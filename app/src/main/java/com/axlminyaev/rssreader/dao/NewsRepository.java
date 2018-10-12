package com.axlminyaev.rssreader.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.axlminyaev.rssreader.model.SourceNews;

public class NewsRepository implements IRepository<SourceNews> {

    private DataBaseHelper dataBase;


    @Override
    public void add(SourceNews element) {

    }

    @Override
    public static SourceNews remove(int id) {
        return null;
    }

    @Override
    static public SourceNews getById(int id) {
        return null;
    }

    private class DataBaseHelper extends SQLiteOpenHelper{

        private static final int DATA_BASE_VERSION = 1;

        public DataBaseHelper(@Nullable Context context, @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
