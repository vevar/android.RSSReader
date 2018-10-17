package com.axlminyaev.rssreader.service;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.axlminyaev.rssreader.engine.HttpHandler;
import com.axlminyaev.rssreader.repository.model.News;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class NewsReaderService {

    public List<News> getAllNews(){

        final String url = "https://habr.com/rss/feed/posts/b74d0ec4a943d95aefccea8e46593cc0/";

        NewsTask newsTask = new NewsTask();
        newsTask.execute(url);

        return null;
    }

    private static class NewsTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings !=null && strings.length > 0){
                return getData(strings[0]);
            }

            return null;
        }

        @Nullable
        private String getData(String from) {

            try {
                return HttpHandler.GetHTTPData(new URL(from));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
