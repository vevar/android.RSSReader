package com.axlminyaev.rssreader.view.news_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.axlminyaev.rssreader.R;
import com.axlminyaev.rssreader.core.repository.NewsRepository;
import com.axlminyaev.rssreader.model.News;
import com.axlminyaev.rssreader.core.service.NewsReaderService;

import java.util.List;
import java.util.concurrent.ExecutionException;

final public class NewsActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listNews);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<News> newsFromRemove = new NewsRepository().getNewsFromRemove("http://rss.nytimes.com/services/xml/rss/nyt/Science.xml");

        try {
            new NewsReaderService().loadRssNews();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

