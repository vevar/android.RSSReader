package com.alxminyaev.rssreader.view.news_activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alxminyaev.rssreader.R;
import com.alxminyaev.rssreader.core.service.NewsReaderService;
import com.alxminyaev.rssreader.model.news.News;
import com.alxminyaev.rssreader.model.news.NewsViewModel;

import java.util.List;

final public class NewsActivity extends AppCompatActivity {

    private NewsViewModel newsViewModel;

    private NewsReaderService newsReaderService;

    final private NewsScreen newsScreen;


    public NewsActivity() {
        newsScreen = new NewsScreen(this);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsScreen.createScreen();

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        final Observer<List<News>> newsObserver = new Observer<List<News>>() {

            @Override
            public void onChanged(@Nullable final List<News> listNews) {
                if (listNews != null) {
                    newsScreen.updateListNews(listNews);
                    String rec = "asd";
                    int a = 0;
                }
            }
        };

        newsViewModel.setObserver(this, newsObserver);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intentGetAllNews = NewsReaderService.getIntentGetAllNews(this);

        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service != null && name.getClassName()
                        .equals(NewsReaderService.class.getName())) {
                    newsReaderService = ((NewsReaderService.NewsBinder) service).getService();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                newsReaderService = null;
            }
        };
        bindService(intentGetAllNews, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (newsReaderService != null) {
                    final List<News> newsList = newsReaderService.getAllNews();
                    if (newsList != null) {
                        newsViewModel.setListNews(newsList);
                    }
                }

            }
        };
        Thread thread = new Thread(task);
        thread.start();

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

