package com.alxminyaev.rssreader.core.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.ArraySet;
import android.widget.Switch;

import com.alxminyaev.rssreader.core.repository.NewsRepository;
import com.alxminyaev.rssreader.model.news.News;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class NewsReaderService extends Service {

    private static final String COMMAND = "command";
    private static final String STATUS = "status";

    private static final int TYPE_COMMAND_LOAD_NEWS = 766;

    private static final int STATUS_NEWS_LOADED_TO_DB = 583;

    private static final String BROADCAST_ACTION_ITSELF =
            "com.alxminyaev.rssreader.core.service.NewsReaderService";
    private static final String BROADCAST_ACTION_FOR_APP =
            "com.alxminyaev.rssreader";

    private ArrayList<News> newsArrayList;

    private NewsReaderBroadcastReceiver newsReaderBroadcastReceiver;

    private final class NewsReaderBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int typeCommand = intent.getIntExtra(COMMAND, 0);
            switch (typeCommand) {
                case TYPE_COMMAND_LOAD_NEWS:
                    loadNews();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        newsReaderBroadcastReceiver = new NewsReaderBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_ITSELF);
        registerReceiver(newsReaderBroadcastReceiver, intentFilter);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newsReaderBroadcastReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @NotNull
    public static Intent getIntentStartService(@NotNull final Activity activity) {
        return new Intent(activity, NewsReaderService.class);
    }

    @NotNull
    public static Intent getIntentStopService(@NotNull final Context context) {
        return new Intent(context, NewsReaderService.class);
    }

    @NotNull
    public static Intent getIntentStartLoadNews() {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_ITSELF);
        intent.putExtra(COMMAND, TYPE_COMMAND_LOAD_NEWS);
        return intent;
    }

    @NotNull
    private static Intent getIntentNotifyNewsLoaded() {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_FOR_APP);
        intent.putExtra(STATUS, STATUS_NEWS_LOADED_TO_DB);
        return intent;
    }

    public void loadNews() {
        final Thread loaderNewsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                newsArrayList = new NewsRepository(getApplicationContext()).getAll();
                notifyReadyLoadedNews();
            }
        });

        loaderNewsThread.start();
    }


    private void notifyReadyLoadedNews() {
        sendBroadcast(getIntentNotifyNewsLoaded());
    }


}
