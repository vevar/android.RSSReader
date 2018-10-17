package com.axlminyaev.rssreader.repository.model;

import android.provider.BaseColumns;

import java.net.URL;

final public class SourceNews {

    final private int id;
    final private String title;
    final private URL url;

    public final static class Contract implements BaseColumns{

        public static final String TABLE_NAME = "source_news";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
    }

    public SourceNews(int id, final String title, final URL url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }
}
