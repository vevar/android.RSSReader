package com.axlminyaev.rssreader.core.service;

import android.os.AsyncTask;

import com.axlminyaev.rssreader.core.repository.NewsRepository;
import com.axlminyaev.rssreader.model.News;

import java.util.List;
import java.util.concurrent.ExecutionException;

public final class NewsReaderService {

    public void loadRssNews() throws ExecutionException, InterruptedException {
        NewsTask newsTask = new NewsTask();
        AsyncTask<String, String, List<News>> execute = newsTask.execute("http://rss.nytimes.com/services/xml/rss/nyt/Science.xml");
        List<News> news = execute.get();

    }

    private static class NewsTask extends AsyncTask<String, String, List<News>> {

        @Override
        protected List<News> doInBackground(String... strings) {
            NewsRepository newsRepository = new NewsRepository();
            if (strings != null && strings.length > 0) {
                return newsRepository.getNewsFromRemove(strings[0]);

            }
            return null;
        }


    }
}
