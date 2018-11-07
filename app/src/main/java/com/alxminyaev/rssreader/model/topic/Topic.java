package com.alxminyaev.rssreader.model.topic;

import android.provider.BaseColumns;

import com.alxminyaev.rssreader.model.source_news.SourceNews;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

final public class Topic {

    private int id;
    private String name;
    private HashSet<SourceNews> sourceNews;

    public static final class Contract implements BaseColumns {
        public static final String TABLE_NAME = "topic";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public Topic(final String name) {
        this.name = name;
    }

    public Topic(final int id, @NotNull final String name,
                 final @NotNull HashSet<SourceNews> sourceNews) {
        this.id = id;
        this.name = name;
        this.sourceNews = sourceNews;
    }

    public String getName() {
        return name;
    }

    public HashSet<SourceNews> getSourceNews() {
        return sourceNews;
    }

    public int getId() {
        return id;
    }
}
