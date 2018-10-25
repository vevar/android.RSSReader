package com.alxminyaev.rssreader.core.repository;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.core.HttpHandler;
import com.alxminyaev.rssreader.core.RssParser;
import com.alxminyaev.rssreader.model.news.News;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsRepository extends ARepository<News> {

    @Override
    protected News getElementByCursor(Cursor cursor) {

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
    public void add(News element) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public News getById(int id) {
        return null;
    }

    @Override
    public List<News> getAll() {
        return null;
    }

    @Nullable
    public List<News> getNewsFromRemove(String from){

        try {
            final InputStream inputStream = HttpHandler.GetHTTPInputStream(new URL(from));
            return new RssParser().parse(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }
}
