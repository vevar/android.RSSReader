package com.alxminyaev.rssreader.core.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.core.repository.NewsRepository;
import com.alxminyaev.rssreader.model.news.News;

import java.util.List;

public final class NewsReaderService extends Service {

    private Thread newsHandler;

    private List<News> newsList;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        newsHandler = new Thread();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Intent getIntentGetAllNews(Activity activity){
        return new Intent(activity, NewsReaderService.class);
    }

    final private class NewsBinder extends Binder {

        public NewsReaderService getService(){
            return NewsReaderService.this;
        }

    }

    final class NewsServiceThread extends Thread{
        private List<News> listNews;

         public List<News> getAllNews(){
             return listNews;
         }

        @Override
        public void run() {
             listNews = new NewsRepository().getAll();
        }
    }
}
