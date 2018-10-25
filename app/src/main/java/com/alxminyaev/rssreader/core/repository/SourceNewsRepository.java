package com.alxminyaev.rssreader.core.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.model.SourceNews;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

final public class SourceNewsRepository extends ARepository<SourceNews> {

    @Override
    public void add(final SourceNews sourceNews) {
        final ContentValues contentSourceNews = new ContentValues();

        contentSourceNews.put(SourceNews.Contract._ID, sourceNews.getId());
        contentSourceNews.put(SourceNews.Contract.COLUMN_NAME_TITLE, sourceNews.getTitle());
        contentSourceNews.put(SourceNews.Contract.COLUMN_NAME_URL, sourceNews.getUrl().toString());

        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(null);

        rssReaderDbHelper.getWritableDatabase()
                .insert(
                        SourceNews.Contract.TABLE_NAME,
                        null,
                        contentSourceNews
                );

        rssReaderDbHelper.close();
    }

    @Override
    public void remove(final int id) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(null);

        rssReaderDbHelper.getWritableDatabase()
                .delete(
                        SourceNews.Contract.TABLE_NAME,
                        SourceNews.Contract._ID + " = ?",
                        new String[]{Integer.toString(id)}
                );

        rssReaderDbHelper.close();
    }

    @Nullable
    @Override
    public SourceNews getById(final int id) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(null);

        final Cursor cursor = rssReaderDbHelper.getWritableDatabase().query(
                SourceNews.Contract.TABLE_NAME, null,
                SourceNews.Contract._ID + "=?", new String[]{Integer.toString(id)},
                null, null, null);

        rssReaderDbHelper.close();

        if (cursor.moveToFirst()) {
            return getElementByCursor(cursor);
        } else {
            return null;
        }
    }

    @Override
    public List<SourceNews> getAll() {
        final List<SourceNews> sourceNewsList = new ArrayList<>();

        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(null);

        final Cursor cursor = rssReaderDbHelper.getWritableDatabase().query(
                SourceNews.Contract.TABLE_NAME, null, null,
                null, null, null, null);

        rssReaderDbHelper.close();

        if (cursor.moveToFirst()) {
            SourceNews sourceNews;

            do {
                sourceNews = getElementByCursor(cursor);
                sourceNewsList.add(sourceNews);
            } while (cursor.moveToNext());

        }

        return sourceNewsList;
    }


    @Nullable
    @Override
    protected SourceNews getElementByCursor(final Cursor cursor) {
        final int idIndex = cursor.getColumnIndex(SourceNews.Contract._ID);
        final int titleIndex = cursor.getColumnIndex(SourceNews.Contract.COLUMN_NAME_TITLE);
        final int urlIndex = cursor.getColumnIndex(SourceNews.Contract.COLUMN_NAME_URL);

        try {
            return new SourceNews
                    (
                            cursor.getInt(idIndex),
                            cursor.getString(titleIndex),
                            new URL(cursor.getColumnName(urlIndex))
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
