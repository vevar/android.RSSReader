package com.alxminyaev.rssreader.core;

import android.util.Log;

import com.alxminyaev.rssreader.core.parser.RSSParser;
import com.alxminyaev.rssreader.exception.core.CoreException;
import com.alxminyaev.rssreader.model.news.News;

import org.junit.Before;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RssParserTest {

    private String PATH_TO_FILE = "src\\test\\res\\TestNews.xml";

    private String NEWS_ONE_TITLE = "Rescuing Sea Turtles From Fishermen’s Nets";
    private String NEWS_ONE_LINK = "https://www.nytimes.com/2018/10/15/science/sea-turtles-endangered-fishing.html?partner=rss&emc=rss";
    private String NEWS_ONE_DESCRIPTION = "A green sea turtle trapped in a gill net. Scientists estimate the global green\n" +
            "                turtle population has declined 50 to 70 percent since 1900.";
    private String NEWS_ONE_PUB_DATE = "Mon, 15 Oct 2018 18:11:48 GMT";


    private String NEWS_TWO_TITLE = "Trilobites: City Rats Eat Meat. Country Rats Eat What They Can.";
    private String NEWS_TWO_LINK = "https://www.nytimes.com/2018/10/16/science/rats-cities-meat.html?partner=rss&emc=rss";
    private String NEWS_TWO_DESCRIPTION = "An analysis of the remains of urban brown rats from the 19th century revealed that\n" +
            "                their diets were much richer than rural rats.";
    private String NEWS_TWO_PUB_DATE = "Tue, 16 Oct 2018 23:00:03 GMT";

    private RSSParser parser;

    @Before
    public void createRSSParser() {
        parser = new RSSParser();
    }

    @Test
    public void testParse() throws MalformedURLException {
        File file = new File(PATH_TO_FILE);

        final List<News> expectedNews = new ArrayList<>();

        News newsOne = new News(NEWS_ONE_TITLE, NEWS_ONE_DESCRIPTION, new URL(NEWS_ONE_LINK), null, null);
        News newsTwo = new News(NEWS_TWO_TITLE, NEWS_TWO_DESCRIPTION, new URL(NEWS_TWO_LINK), null, null);

        expectedNews.add(newsOne);
        expectedNews.add(newsTwo);


        try {
            List<News> actualNews = parser.parse(new FileInputStream(file));
            assertEquals(expectedNews, actualNews);
        } catch (XmlPullParserException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        } catch (ParseException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        }
    }
}
