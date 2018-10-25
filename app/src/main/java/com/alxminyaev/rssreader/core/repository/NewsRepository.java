package com.alxminyaev.rssreader.core.repository;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.core.HttpHandler;
import com.alxminyaev.rssreader.core.RSSParser;
import com.alxminyaev.rssreader.model.source_news.SourceNews;
import com.alxminyaev.rssreader.model.topic.Topic;
import com.alxminyaev.rssreader.model.news.News;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final public class NewsRepository extends ARepository<News> {

    private HashMap<Topic, Set<SourceNews>> topicSourceNews = null;

    private RSSParser rssParser;

    @NotNull
    private RSSParser getRSSParser() {
        if (rssParser == null) {
            rssParser = new RSSParser();
        }

        return rssParser;
    }

    @NotNull
    private HashMap<Topic, Set<SourceNews>> getMapTopicSourceNews() {
        if (topicSourceNews == null) {
            topicSourceNews = new HashMap<>();
            final List<Topic> listTopics = new TopicRepository().getAll();
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
    protected News getElementByCursor(final Cursor cursor) {

        final int idIndex = cursor.getColumnIndex(News.Contract._ID);
        final int titleIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_TITLE);
        final int descriptionIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_DESCRIPTION);
        final int urlImageIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_URL_IMAGE);
        final int urlIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_URL);
        final int pubDateIndex = cursor.getColumnIndex(News.Contract.COLUMN_NAME_PUB_DATE);

        try {
            Date date = new SimpleDateFormat("dd.mm.yyyy", Locale.US).parse(cursor.getString(pubDateIndex));

            return new News
                    (
                            cursor.getInt(idIndex),
                            cursor.getString(titleIndex),
                            cursor.getString(descriptionIndex),
                            new URL(cursor.getString(urlIndex)),
                            new URL(cursor.getString(urlImageIndex)),
                            date

                    );
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void add(final News element) {

    }

    @Override
    public void remove(final int id) {

    }

    @Override
    public News getById(final int id) {
        return null;
    }

    @Nullable
    @Override
    public List<News> getAll() {
        final List<News> resultListNews = new ArrayList<>();

        for (Map.Entry<Topic, Set<SourceNews>> entrySourceNews : getMapTopicSourceNews().entrySet()) {
            Topic topic = entrySourceNews.getKey();
            List<News> listByTopic = getByTopic(topic);

            if (listByTopic != null) {
                resultListNews.addAll(listByTopic);
            }

        }

        return resultListNews.size() > 0 ? resultListNews : null;
    }

    @Nullable
    public List<News> getBySourceNews(@NotNull final SourceNews sourceNews) {
        final InputStream inputStream;
        List<News> parsedNewsList = null;

        try {
            inputStream = HttpHandler.GetHTTPInputStream(sourceNews.getUrl());
            if (inputStream != null) {
                parsedNewsList = getRSSParser().parse(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return parsedNewsList;
    }

    public List<News> getByTopic(@NotNull final Topic topic) {
        List<News> resultListNews = new ArrayList<>();
        Set<SourceNews> setSourceNews = topicSourceNews.get(topic);

        if (setSourceNews != null) {
            for (SourceNews source : setSourceNews) {
                List<News> listBySource = getBySourceNews(source);
                if (listBySource != null) {
                    resultListNews.addAll(listBySource);
                }
            }
        }

        return resultListNews.size() > 0 ? resultListNews : null;
    }
}
