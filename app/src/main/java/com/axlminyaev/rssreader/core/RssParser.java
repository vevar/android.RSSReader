package com.axlminyaev.rssreader.core;

import android.support.annotation.Nullable;
import android.util.Xml;

import com.axlminyaev.rssreader.model.News;

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
import java.util.List;
import java.util.Locale;

public class RssParser extends AParserNews {

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
    private News parseNews(XmlPullParser parser) {
        String title = null, description = null;
        URL url = null, urlImage = null;
        Date pubDate = null;

        try {
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
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (title != null && description != null && url != null) {
            return new News(title, description, url, urlImage, pubDate);
        } else {
            return null;
        }
    }

    private URL getUrlContentImage(XmlPullParser parser) throws MalformedURLException {
        if (parser.getAttributeValue(null, ATTR_MEDIUM).equals(VALUE_IMAGE)) {
            return new URL(parser.getAttributeValue(null, ATTR_URL));
        }

        return null;
    }

    private URL getUrlImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType;
        String tagType;

        do {
            eventType = parser.next();
            tagType = parser.getName();
        } while (eventType != XmlPullParser.START_TAG || !tagType.equals(TAG_LINK));

        return new URL(parser.nextText());
    }

    private Date getDate(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        final SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault());

        return simpleDateFormat.parse(parser.nextText());
    }

    private String getAttrLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result;

        if (parser.getAttributeCount() > 0) {
            result = parser.getAttributeValue(null, ATTR_HREF);
        } else {
            result = parser.nextText();
        }

        return result;
    }
}
