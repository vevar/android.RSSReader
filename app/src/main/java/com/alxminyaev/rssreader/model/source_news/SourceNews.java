package com.alxminyaev.rssreader.model.source_news;

import android.provider.BaseColumns;

import com.alxminyaev.rssreader.model.topic.Topic;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

final public class SourceNews {

    private int id;
    private String title;
    private URL url;

    private HashSet<Topic> topic;

    public final static class Contract implements BaseColumns {

        public static final String TABLE_NAME = "source_news";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
    }

    public SourceNews(int id, final String title, final URL url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public SourceNews(String title, URL url, HashSet<Topic> topic) {
        this.title = title;
        this.url = url;
        this.topic = topic;
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
