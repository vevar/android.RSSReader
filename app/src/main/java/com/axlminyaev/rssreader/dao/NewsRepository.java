package com.axlminyaev.rssreader.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.axlminyaev.rssreader.model.SourceNews;

import java.net.MalformedURLException;
import java.net.URL;

final class NewsRepository extends ARepository<SourceNews> {

    final private DataBaseHelper dataBase;

    NewsRepository() {
        dataBase = new DataBaseHelper(null, RepositoryManager.DATA_BASE_NAME);
    }

    @Override
    public void add(SourceNews sourceNews) {
        final ContentValues contentSourceNews = new ContentValues();
        contentSourceNews.put(SourceNews.ID, sourceNews.getId());
        contentSourceNews.put(SourceNews.TITLE, sourceNews.getTitle());
        contentSourceNews.put(SourceNews.URL, sourceNews.getUrl().toString());

        dataBase.getWritableDatabase()
                .insert(
                        DataBaseHelper.TABLE_SOURCE_NEWS,
                        null,
                        contentSourceNews
                );
        dataBase.close();
    }

    @Override
    public void remove(int id) {
        dataBase.getWritableDatabase()
                .delete(
                        DataBaseHelper.TABLE_SOURCE_NEWS,
                        SourceNews.ID + " = ?",
                        new String[]{Integer.toString(id)}
                );
    }

    @Nullable
    @Override
    public SourceNews getById(int id) {
        final Cursor cursor = dataBase.getWritableDatabase()
                .query(
                        DataBaseHelper.TABLE_SOURCE_NEWS,
                        null,
                        SourceNews.ID + "= ?",
                        new String[]{Integer.toString(id)},
                        null,
                        null,
                        null
                );
        if (cursor.moveToFirst()) {
            return getElementByCursor(cursor);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    protected SourceNews getElementByCursor(Cursor cursor) {
        final int idIndex = cursor.getColumnIndex(SourceNews.ID);
        final int titleIndex = cursor.getColumnIndex(SourceNews.TITLE);
        final int urlIndex = cursor.getColumnIndex(SourceNews.URL);

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


    private class DataBaseHelper extends SQLiteOpenHelper {

        private static final int DATA_BASE_VERSION = 1;

        private static final String TABLE_SOURCE_NEWS = "source_news";

        private DataBaseHelper(@Nullable Context context, @Nullable String name) {
            super(context, name, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_SOURCE_NEWS +
                    "(" +
                    SourceNews.ID + " INTEGER PRIMARY KEY, " +
                    SourceNews.TITLE + "TEXT" +
                    SourceNews.URL + "TEXT" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
