package com.alxminyaev.rssreader.core.parser;

import android.support.annotation.Nullable;
import android.util.Xml;

import com.alxminyaev.rssreader.model.news.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

final public class RSSParser extends AParserNews {

    @Override
    public ArrayList<News> parse(final InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
        final XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new InputStreamReader(inputStream));

        final ArrayList<News> listNews = new ArrayList<>();

        int eventType = parser.getEventType();
        String tagType;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            tagType = parser.getName();

            if (eventType == XmlPullParser.START_TAG && tagType.equals(TAG_ITEM)) {
                final News news = parseNews(parser);

                if (news != null) {
                    listNews.add(news);
                }
            }
            eventType = parser.next();
        }
        return listNews;
    }

    @Nullable
    private News parseNews(final XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        String title = null;
        String description = null;
        URL url = null;
        URL urlImage = null;
        Date pubDate = null;

        int eventType = parser.getEventType();
        String tagType = parser.getName();

        while (eventType != XmlPullParser.END_TAG || !tagType.equals(TAG_ITEM)) {

            if (eventType == XmlPullParser.START_TAG) {
                tagType = parser.getName();

                switch (tagType) {
                    case TAG_TITLE:
                        title = parser.nextText();
                        break;
                    case TAG_LINK:
                        if (parser.getAttributeCount() > 0) {
                            url = new URL(getAttrLink(parser));
                        } else {
                            url = new URL(parser.nextText());
                        }
                        break;
                    case TAG_DESCRIPTION:
                        description = parser.nextText();
                        break;
                    case TAG_PUB_DATE:
                        pubDate = getDate(parser);
                        break;
                    case TAG_IMAGE:
                        if (urlImage == null) {
                            urlImage = getUrlImage(parser);
                        }
                        break;
                    case TAG_CONTENT:
                        if (urlImage == null) {
                            urlImage = getUrlContentImage(parser);
                        }
                        break;
                    default:
                        break;
                }
            }
            eventType = parser.next();
            tagType = parser.getName();
        }

        if (title != null && description != null && url != null) {
            final News news = new News(title, description, url);
            if (urlImage != null) {
                news.setUrlImage(urlImage);
            }
            if (pubDate != null) {
                news.setPubDate(pubDate);
            }
            return news;
        } else {
            return null;
        }
    }

    private URL getUrlContentImage(final XmlPullParser parser) throws MalformedURLException {
        if (parser.getAttributeValue(null, ATTR_MEDIUM).equals(VALUE_IMAGE)) {
            return new URL(parser.getAttributeValue(null, ATTR_URL));
        }

        return null;
    }

    private URL getUrlImage(final XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType;
        String tagType;

        do {
            eventType = parser.next();
            tagType = parser.getName();
        } while (eventType != XmlPullParser.START_TAG || !tagType.equals(TAG_LINK));

        return new URL(parser.nextText());
    }

    private Date getDate(final XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        final SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(PATTERN_PARSE_DATE, Locale.getDefault());

        return simpleDateFormat.parse(parser.nextText());
    }

    private String getAttrLink(final XmlPullParser parser) throws IOException, XmlPullParserException {
        String result;

        if (parser.getAttributeCount() > 0) {
            result = parser.getAttributeValue(null, ATTR_HREF);
        } else {
            result = parser.nextText();
        }

        return result;
    }
}
