package com.alxminyaev.rssreader.model.source_news;

import android.provider.BaseColumns;

import com.alxminyaev.rssreader.model.topic.Topic;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.Date;
import java.util.HashSet;

final public class SourceNews {

    private int id;
    private String title;
    private URL url;
    private Date lastBuildDate;

    private HashSet<Topic> topic;

    public final static class Contract implements BaseColumns {

        public static final String TABLE_NAME = "source_news";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_LAST_BUILD_DATE = "last_build_date";
    }

    public SourceNews(final int id, @NotNull final String title,
                      @NotNull final URL url, @NotNull final Date lastBuildDate) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.lastBuildDate = lastBuildDate;
    }

    public SourceNews(String title, URL url) {
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

    public Date getLastBuildDate() {
        return lastBuildDate;
    }
}
