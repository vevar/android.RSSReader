package com.alxminyaev.rssreader.view.news_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alxminyaev.rssreader.R;
import com.alxminyaev.rssreader.core.service.NewsReaderService;
import com.alxminyaev.rssreader.model.news.NewsViewModel;

final public class NewsActivity extends AppCompatActivity {

    private NewsViewModel newsViewModel;

    private NewsReaderService newsReaderService;

    private NewsScreen newsScreen;

    public NewsActivity() {
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        newsScreen = new NewsScreen(this);

        final Intent intentStartService = NewsReaderService.getIntentStartService(this);
        startService(intentStartService);


    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intentStartLoadNews = NewsReaderService.getIntentStartLoadNews();
        sendBroadcast(intentStartLoadNews);
    }

    @Override
    protected void onResume() {
        super.onResume();

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

