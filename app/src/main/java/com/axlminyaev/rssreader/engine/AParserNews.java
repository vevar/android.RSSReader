package com.axlminyaev.rssreader.engine;

import com.axlminyaev.rssreader.repository.model.News;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class AParserNews {

    static final String TAG_ITEM = "item";
    static final String TAG_TITLE = "title";
    static final String TAG_LINK = "link";
    static final String TAG_DESCRIPTION = "description";
    static final String TAG_PUB_DATE = "pubDate";

    abstract public List<News> parse(String text);

    abstract public List<News> parse(InputStream inputStream) throws XmlPullParserException, IOException;
}
