package com.alxminyaev.rssreader.core.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.model.news.News;
import com.alxminyaev.rssreader.model.source_news.SourceNews;

final class RSSReaderDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RSSReader.db";

    private static final String SQL_CREATE_NEWS =
            "CREATE TABLE " + News.Contract.TABLE_NAME + "(" +
                    News.Contract._ID + " INTEGER PRIMARY KEY," +
                    News.Contract.COLUMN_NAME_TITLE + " TEXT," +
                    News.Contract.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    News.Contract.COLUMN_NAME_URL + " TEXT," +
                    News.Contract.COLUMN_NAME_URL_IMAGE + " TEXT," +
                    News.Contract.COLUMN_NAME_PUB_DATE + " TEXT)";

    private static final String SQL_DELETE_NEWS =
            "DROP TABLE IF EXISTS " + News.Contract.TABLE_NAME;

    private static final String SQL_CREATE_SOURCE_NEWS =
            "CREATE TABLE " + SourceNews.Contract.TABLE_NAME + "(" +
                    SourceNews.Contract._ID + " INTEGER PRIMARY KEY," +
                    SourceNews.Contract.COLUMN_NAME_TITLE + " TEXT," +
                    SourceNews.Contract.COLUMN_NAME_URL + " TEXT" +
                    ")";

    private static final String SQL_DELETE_SOURCE_NEWS =
            "DROP TABLE IF EXISTS " + SourceNews.Contract.TABLE_NAME;

    RSSReaderDbHelper(@Nullable final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NEWS);
        db.execSQL(SQL_CREATE_SOURCE_NEWS);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NEWS);
        db.execSQL(SQL_CREATE_NEWS);

        db.execSQL(SQL_DELETE_SOURCE_NEWS);
        db.execSQL(SQL_CREATE_SOURCE_NEWS);
    }

}

