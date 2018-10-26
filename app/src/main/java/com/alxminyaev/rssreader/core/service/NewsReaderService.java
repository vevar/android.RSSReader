package com.alxminyaev.rssreader.core.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.alxminyaev.rssreader.core.repository.NewsRepository;
import com.alxminyaev.rssreader.model.news.News;

import java.util.List;

public final class NewsReaderService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return new NewsBinder();
    }


    public List<News> getAllNews(){
        return new NewsRepository().getAll();
    }

    public static Intent getIntentGetAllNews(final Activity activity) {
        return new Intent(activity, NewsReaderService.class);
    }

    final public class NewsBinder extends Binder {

        public NewsReaderService getService() {
            return NewsReaderService.this;
        }

    }
}
