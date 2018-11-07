package com.alxminyaev.rssreader.core.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.model.news.News;
import com.alxminyaev.rssreader.model.source_news.SourceNews;
import com.alxminyaev.rssreader.model.topic.Topic;

final class RSSReaderDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "RSSReader.db";

    public static final class ContractSourceNewsManyToManyTopic {
        private ContractSourceNewsManyToManyTopic() {
        }

        public static final String TABLE_NAME = "source_news_many_to_many_topic";

        public static final String COLUMN_NAME_SOURCE_NEWS_ID = "id_source_news";
        public static final String COLUMN_NAME_TOPIC_ID = "id_topic";
    }

    private static final String SQL_CREATE_NEWS =
            "CREATE TABLE " + News.Contract.TABLE_NAME + "(" +
                    News.Contract._ID + " INTEGER PRIMARY KEY," +
                    News.Contract.COLUMN_NAME_TITLE + " TEXT," +
                    News.Contract.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    News.Contract.COLUMN_NAME_URL + " TEXT," +
                    News.Contract.COLUMN_NAME_URL_IMAGE + " TEXT," +
                    News.Contract.COLUMN_NAME_PUB_DATE + " TEXT," +
                    News.Contract.COLUMN_NAME_IS_READIED + " INTEGER, " +
                    News.Contract.COLUMN_NAME_IS_SAVED + " INTEGER," +
                    News.Contract.COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM + " TEXT" +
                    ")";

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

    private static final String SQL_CREATE_TOPIC =
            "CREATE TABLE " + Topic.Contract.TABLE_NAME + "(" +
                    Topic.Contract._ID + " INTEGER PRIMARY KEY, " +
                    Topic.Contract.COLUMN_NAME_NAME + " TEXT)";

    private static final String SQL_DELETE_TOPIC =
            "DROP TABLE IF EXISTS " + Topic.Contract.TABLE_NAME;

    private static final String SQL_CREATE_SOURCE_NEWS_MANY_TO_MANY_TOPIC =
            "CREATE TABLE " + ContractSourceNewsManyToManyTopic.TABLE_NAME + "(" +
                    ContractSourceNewsManyToManyTopic.COLUMN_NAME_SOURCE_NEWS_ID + " INTEGER," +
                    ContractSourceNewsManyToManyTopic.COLUMN_NAME_TOPIC_ID + " INTEGER" +
                    ")";

    private static final String SQL_DELETE_SOURCE_NEWS_MANY_TO_MANY_TOPIC =
            "DROP TABLE IF EXISTS " + ContractSourceNewsManyToManyTopic.TABLE_NAME;


    RSSReaderDbHelper(@Nullable final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NEWS);
        db.execSQL(SQL_CREATE_SOURCE_NEWS);
        db.execSQL(SQL_CREATE_TOPIC);
        db.execSQL(SQL_CREATE_SOURCE_NEWS_MANY_TO_MANY_TOPIC);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NEWS);
        db.execSQL(SQL_CREATE_NEWS);

        db.execSQL(SQL_DELETE_SOURCE_NEWS);
        db.execSQL(SQL_CREATE_SOURCE_NEWS);

        db.execSQL(SQL_DELETE_TOPIC);
        db.execSQL(SQL_CREATE_TOPIC);

        db.execSQL(SQL_DELETE_SOURCE_NEWS_MANY_TO_MANY_TOPIC);
        db.execSQL(SQL_CREATE_SOURCE_NEWS_MANY_TO_MANY_TOPIC);
    }

}

