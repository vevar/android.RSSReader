package com.axlminyaev.rssreader.model;

import java.net.URL;

final public class SourceNews {

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String URL = "url";

    final private int id;
    final private String title;
    final private URL url;

    public SourceNews(int id, String title, URL url) {
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
