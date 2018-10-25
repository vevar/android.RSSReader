package com.alxminyaev.rssreader.model.topic;

import com.alxminyaev.rssreader.model.source_news.SourceNews;

import java.util.Set;

final public class Topic {

    private String name;
    private Set<SourceNews> sourceNews;

    public Topic(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<SourceNews> getSourceNews(){
        return sourceNews;
    }
}
