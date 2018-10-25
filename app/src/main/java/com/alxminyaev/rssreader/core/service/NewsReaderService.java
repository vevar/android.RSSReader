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

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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


    public List<News> getAllNews(){
        return new NewsRepository().getAll();
    }

    public static Intent getIntentGetAllNews(Activity activity) {
        return new Intent(activity, NewsReaderService.class);
    }

    final public class NewsBinder extends Binder {

        public NewsReaderService getService() {
            return NewsReaderService.this;
        }

    }
}
