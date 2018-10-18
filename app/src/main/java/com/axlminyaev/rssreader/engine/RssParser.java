package com.axlminyaev.rssreader.engine;

import android.util.Xml;

import com.axlminyaev.rssreader.repository.model.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssParser extends AParserNews {


    @Override
    public List<News> parse(String text) {

        return null;
    }

    @Override
    public List<News> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new InputStreamReader(inputStream));

        List<News> listNews = new ArrayList<>();

        int eventType = parser.getEventType();
        String tagType;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            tagType = parser.getName();
            if (eventType == XmlPullParser.START_TAG && tagType.equals(TAG_ITEM)) {
                parseItem(parser);
            }

            eventType = parser.next();
        }


        return null;
    }


    private News parseItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String tagType = parser.getName();

        String title = null;
        String description = null;
        String pubDate = null;
        URL url = null;

        while (eventType != XmlPullParser.END_TAG || !tagType.equals(TAG_ITEM)) {

            if (eventType == XmlPullParser.START_TAG) {
                tagType = parser.getName();

                switch (tagType) {
                    case TAG_TITLE:
                        title = getTitle(parser);
                        break;
                    case TAG_LINK:
                        url = new URL(getLink(parser));
                        break;
                    case TAG_DESCRIPTION:
                        description = getDescription(parser);
                        break;
                    case TAG_PUB_DATE:
                        pubDate = getPubDate(parser);
                        break;
                    default:
                        break;
                }
            }
            eventType = parser.next();
            tagType = parser.getName();
        }

        return new News(title, description, url, null, null);
    }

    private String getPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        return parser.nextText();
    }

    private String getDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        return parser.nextText();
    }

    private String getLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        return parser.nextText();
    }

    private String getTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        return parser.nextText();
    }
}
