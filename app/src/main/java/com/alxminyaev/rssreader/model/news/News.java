package com.alxminyaev.rssreader.model.news;

import android.provider.BaseColumns;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.util.Date;

final public class News {

    public static class Contract implements BaseColumns {

        private Contract() {
        }

        public static final String TABLE_NAME = "news";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_IMAGE = "image";
        public static final String COLUMN_NAME_PUB_DATE = "pub_date";
        public static final String COLUMN_NAME_IS_READIED = "is_readied";
        public static final String COLUMN_NAME_IS_FAVORITE = "is_favorite";
        public static final String COLUMN_NAME_PATH_IMAGE_IN_FILE_SYSTEM = "path_image";
        public static final String COLUMN_NAME_IS_SAVED = "is_saved";
    }

    private int id;
    private String title;
    private String description;
    private URL url;
    private URL urlImage;
    private Date pubDate;

    private boolean isReadied;
    private boolean isSaved;

    private File pathImage;


    public News() {

    }

    public News(String title, String description, URL url) {
        this.title = title;
        this.description = description;
        this.url = url;
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

    public News(int id, String title, String description, URL url, URL urlImage, Date pubDate,
                boolean isReadied, boolean isSaved, File pathImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlImage = urlImage;
        this.pubDate = pubDate;
        this.isReadied = isReadied;
        this.isSaved = isSaved;
        this.pathImage = pathImage;
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

    public boolean isReadied() {
        return isReadied;
    }

    public void setReadied(boolean readied) {
        isReadied = readied;
    }


    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public File getPathImage() {
        return pathImage;
    }

    public void setPathImage(File pathImage) {
        this.pathImage = pathImage;
    }

    public boolean equals(@NotNull News news) {
        return this.title.equals(news.title) &&
                this.description.equals(news.description) &&
                this.url.equals(news.url);
    }
}
