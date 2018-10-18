package com.axlminyaev.rssreader.repository.model;

import android.provider.BaseColumns;

import java.net.URL;
import java.util.Date;

final public class News {

    public static final class Contract implements BaseColumns {

        private Contract(){}

        public static final String TABLE_NAME = "news";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_IMAGE = "image";
        public static final String COLUMN_NAME_PUB_DATE = "pub_date";
    }

    private int id;
    private String title;
    private String description;
    private URL url;
    private URL urlImage;
    private Date pubDate;

    public News(){

    }

    public News(String title, String description, URL url, URL urlImage, Date pubDate) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlImage = urlImage;
        this.pubDate = pubDate;
    }

    public News(int id, String title, String description, URL url, URL urlImage, Date pubDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlImage = urlImage;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URL getUrl() {
        return url;
    }

    public URL getUrlImage() {
        return urlImage;
    }

    public Date getPubDate() {
        return pubDate;
    }
}
