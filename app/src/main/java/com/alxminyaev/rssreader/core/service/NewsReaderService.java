package com.alxminyaev.rssreader.core.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.ArraySet;

import com.alxminyaev.rssreader.core.repository.NewsRepository;
import com.alxminyaev.rssreader.model.news.News;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class NewsReaderService extends Service {

    private ArrayList<News> newsArrayList;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static Intent getIntentStartService(final Activity activity) {
        return new Intent(activity, NewsReaderService.class);
    }

    public static Intent getIntentStopService(final Context context) {
        return new Intent(context, NewsReaderService.class);
    }

    public Intent dataNewsReady(){
        Intent intent = new Intent();

        return intent;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return new NewsBinder();
    }

    public void loadNews() {
        Thread loaderNewsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                newsArrayList = new NewsRepository(getApplicationContext()).getAll();
            }
        });
        loaderNewsThread.start();
    }


    final public class NewsBinder extends Binder {

        public NewsReaderService getService() {
            return NewsReaderService.this;
        }
    }


}
