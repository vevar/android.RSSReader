package com.alxminyaev.rssreader.core.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.model.source_news.SourceNews;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

final public class SourceNewsRepository extends ARepository<SourceNews> {

    final private Context context;

    public SourceNewsRepository(final Context context) {
        this.context = context;
    }

    @Override
    public void save(final SourceNews sourceNews) {
        final ContentValues contentSourceNews = new ContentValues();

        contentSourceNews.put(SourceNews.Contract.COLUMN_NAME_TITLE, sourceNews.getTitle());
        contentSourceNews.put(SourceNews.Contract.COLUMN_NAME_URL, sourceNews.getUrl().toString());

        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

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
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

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
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        final Cursor cursor = rssReaderDbHelper.getReadableDatabase().query(
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

    @Nullable
    @Override
    public ArrayList<SourceNews> getAll() {

        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        final Cursor cursor = rssReaderDbHelper.getReadableDatabase().query(
                SourceNews.Contract.TABLE_NAME, null, null,
                null, null, null, null);


        if (cursor.moveToFirst()) {
            final ArrayList<SourceNews> sourceNewsList = new ArrayList<>();
            SourceNews sourceNews;

            do {
                sourceNews = getElementByCursor(cursor);
                sourceNewsList.add(sourceNews);
            } while (cursor.moveToNext());
            //TODO finally
            rssReaderDbHelper.close();

            return sourceNewsList;
        }

        return null;
    }


    @Nullable
    @Override
    protected SourceNews getElementByCursor(@NotNull final Cursor cursor) {
        final int idIndex = cursor.getColumnIndex(SourceNews.Contract._ID);
        final int titleIndex = cursor.getColumnIndex(SourceNews.Contract.COLUMN_NAME_TITLE);
        final int urlIndex = cursor.getColumnIndex(SourceNews.Contract.COLUMN_NAME_URL);

        try {
            return new SourceNews
                    (
                            cursor.getInt(idIndex),
                            cursor.getString(titleIndex),
                            new URL(cursor.getString(urlIndex))
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
