package com.alxminyaev.rssreader.core.parser;

import com.alxminyaev.rssreader.model.news.News;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

public abstract class AParserNews {

    static final String TAG_ITEM = "item";
    static final String TAG_TITLE = "title";
    static final String TAG_LINK = "link";
    static final String TAG_DESCRIPTION = "description";
    static final String TAG_PUB_DATE = "pubDate";
    static final String TAG_IMAGE = "image";
    static final String TAG_CONTENT = "content";

    static final String ATTR_MEDIUM = "medium";
    static final String ATTR_URL = "url";
    static final String ATTR_HREF = "href";

    static final String VALUE_IMAGE = "image";

    final String PATTERN_PARSE_DATE = "EEE, d MMM yyyy HH:mm:ss z";


    abstract public List<News> parse(final InputStream inputStream) throws XmlPullParserException, IOException, ParseException;
}
