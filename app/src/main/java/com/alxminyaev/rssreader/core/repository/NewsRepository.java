package com.alxminyaev.rssreader.core.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alxminyaev.rssreader.core.HttpHandler;
import com.alxminyaev.rssreader.core.RSSParser;
import com.alxminyaev.rssreader.exception.core.CoreException;
import com.alxminyaev.rssreader.exception.core.IncorrectProtocolInURL;
import com.alxminyaev.rssreader.model.news.News;
import com.alxminyaev.rssreader.model.source_news.SourceNews;
import com.alxminyaev.rssreader.model.topic.Topic;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

final public class NewsRepository extends ARepository<News> {

    private final Context context;

    public NewsRepository(Context context) {
        this.context = context;
    }

    private HashMap<Topic, HashSet<SourceNews>> topicSourceNews = null;

    private RSSParser rssParser;

    @NotNull
    private RSSParser getRSSParser() {
        if (rssParser == null) {
            rssParser = new RSSParser();
        }

        return rssParser;
    }

    @NotNull
    private HashMap<Topic, HashSet<SourceNews>> getMapTopicSourceNews() {
        if (topicSourceNews == null) {
            topicSourceNews = new HashMap<>();
            final ArrayList<Topic> listTopics = new TopicRepository(context).getAll();
            if (listTopics != null) {
                for (Topic topic : listTopics) {
                    topicSourceNews.put(topic, topic.getSourceNews());
                }
            }
        }

        return topicSourceNews;
    }


    @Nullable
    @Override
    protected News getElementByCursor(@NotNull final Cursor cursor) {

        final int idIndex = cursor.getColumnIndex(News.Contract._ID);
        final int titleIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_TITLE);
        final int descriptionIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_DESCRIPTION);
        final int urlImageIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_URL_IMAGE);
        final int urlIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_URL);
        final int pubDateIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_PUB_DATE);
        final int isReadiedIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_IS_READIED);
        final int isSavedIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_IS_SAVED);

        try {
            Date date = new SimpleDateFormat("dd.mm.yyyy", Locale.US).parse(cursor.getString(pubDateIndex));

            News news = new News(
                    cursor.getInt(idIndex),
                    cursor.getString(titleIndex),
                    cursor.getString(descriptionIndex),
                    new URL(cursor.getString(urlIndex)),
                    new URL(cursor.getString(urlImageIndex)),
                    date
            );
            final int isReadied = cursor.getInt(isReadiedIndex);
            news.setReadied(isReadied == 1);

            final int isSaved = cursor.getInt(isSavedIndex);
            news.setSaved(isSaved == 1);

            if (isSaved == 1) {
                final int pathImageIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM);
                news.setPathImage(new File(cursor.getString(pathImageIndex)));
            }

            return news;

        } catch (ParseException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
            return null;
        } catch (MalformedURLException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
            return null;
        } finally {
            cursor.close();
        }

    }

    @Override
    public void save(@NotNull final News element) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        final ContentValues contentNews = new ContentValues();

        contentNews.put(News.Contract.COLUMN_NAME_TITLE, element.getTitle());
        contentNews.put(News.Contract.COLUMN_NAME_DESCRIPTION, element.getDescription());
        contentNews.put(News.Contract.COLUMN_NAME_URL, element.getUrl().toString());
        contentNews.put(News.Contract.COLUMN_NAME_PUB_DATE, element.getPubDate().toString());
        contentNews.put(News.Contract.COLUMN_NAME_URL_IMAGE, element.getUrlImage().toString());
        contentNews.put(News.Contract.COLUMN_NAME_IS_FAVORITE, element.isReadied());
        contentNews.put(News.Contract.COLUMN_NAME_IS_SAVED, element.isSaved());
        if (element.isSaved()) {
            contentNews.put(News.Contract.COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM, element.getPathImage().toString());
        } else {
            contentNews.put(News.Contract.COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM, "");
        }

        rssReaderDbHelper.getWritableDatabase()
                .insert(
                        News.Contract.TABLE_NAME,
                        null,
                        contentNews
                );
        rssReaderDbHelper.close();
    }

    public void saveMany(@NotNull final ArrayList<News> listNews) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        SQLiteDatabase writableDatabase = rssReaderDbHelper.getWritableDatabase();
        try {
            writableDatabase.beginTransaction();

            for (News news : listNews) {
                final ContentValues contentNews = new ContentValues();

                contentNews.put(News.Contract.COLUMN_NAME_TITLE, news.getTitle());
                contentNews.put(News.Contract.COLUMN_NAME_DESCRIPTION, news.getDescription());
                contentNews.put(News.Contract.COLUMN_NAME_URL, news.getUrl().toString());
                contentNews.put(News.Contract.COLUMN_NAME_PUB_DATE, news.getPubDate().toString());
                contentNews.put(News.Contract.COLUMN_NAME_URL_IMAGE, news.getUrlImage().toString());
                contentNews.put(News.Contract.COLUMN_NAME_IS_FAVORITE, news.isReadied());
                contentNews.put(News.Contract.COLUMN_NAME_IS_SAVED, news.isSaved());
                if (news.isSaved()) {
                    contentNews.put(News.Contract.COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM, news.getPathImage().toString());
                } else {
                    contentNews.put(News.Contract.COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM, "");
                }
                writableDatabase.insert(
                        News.Contract.TABLE_NAME,
                        null,
                        contentNews
                );
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
            writableDatabase.close();
        }

    }

    @Override
    public void remove(final int id) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);
        try {
            rssReaderDbHelper.getReadableDatabase()
                    .delete(
                            News.Contract.TABLE_NAME,
                            News.Contract._ID + " = ?",
                            new String[]{String.valueOf(id)}
                    );
        } finally {
            rssReaderDbHelper.close();
        }
    }


    @Nullable
    @Override
    public News getById(final int id) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);
        try {
            Cursor cursor = rssReaderDbHelper.getReadableDatabase()
                    .query(News.Contract.TABLE_NAME, null,
                            News.Contract._ID, new String[]{String.valueOf(id)},
                            null, null, null);

            return getElementByCursor(cursor);
        } finally {
            rssReaderDbHelper.close();
        }
    }

    @Nullable
    @Override
    public ArrayList<News> getAll() {
        final ArrayList<News> resultListNews = new ArrayList<>();

        for (Map.Entry<Topic, HashSet<SourceNews>> entrySourceNews : getMapTopicSourceNews().entrySet()) {
            final Topic topic = entrySourceNews.getKey();
            final ArrayList<News> listByTopic = getByTopic(topic);

            if (listByTopic != null) {
                resultListNews.addAll(listByTopic);
            }
        }

        return resultListNews.size() > 0 ? resultListNews : null;
    }

    @Nullable
    private ArrayList<News> getBySourceNews(@NotNull final SourceNews sourceNews) {
        InputStream inputStream = null;
        ArrayList<News> parsedNewsList = null;

        try {
            inputStream = HttpHandler.GetHTTPInputStream(sourceNews.getUrl());
            if (inputStream != null) {
                parsedNewsList = getRSSParser().parse(inputStream);
            }
        } catch (IOException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        } catch (XmlPullParserException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        } catch (IncorrectProtocolInURL e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e(CoreException.TAG, e.getMessage(), e);
            }
        }

        return parsedNewsList;
    }

    @Nullable
    private ArrayList<News> getByTopic(@NotNull final Topic topic) {
        final ArrayList<News> resultListNews = new ArrayList<>();
        final HashSet<SourceNews> setSourceNews = topicSourceNews.get(topic);

        if (setSourceNews != null) {
            for (SourceNews source : setSourceNews) {
                ArrayList<News> listBySource = getBySourceNews(source);
                if (listBySource != null) {
                    resultListNews.addAll(listBySource);
                }
            }
        }

        return resultListNews.size() > 0 ? resultListNews : null;
    }
}
