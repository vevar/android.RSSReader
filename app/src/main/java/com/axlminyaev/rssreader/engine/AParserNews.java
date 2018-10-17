package com.axlminyaev.rssreader.engine;

import com.axlminyaev.rssreader.repository.model.News;

import java.util.List;

public abstract class AParserNews {

    abstract public List<News> parse(String text);
}
