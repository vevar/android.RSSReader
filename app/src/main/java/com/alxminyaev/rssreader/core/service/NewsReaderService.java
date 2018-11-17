package com.alxminyaev.rssreader.core.service;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.alxminyaev.rssreader.core.repository.NewsRepository;
import com.alxminyaev.rssreader.model.news.News;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class NewsReaderService extends Service {

    private static final String COMMAND = "command";
    public static final String STATUS = "status";

    private static final int TYPE_COMMAND_LOAD_NEWS = 766;

    public static final int STATUS_NEWS_LOADED_TO_DB = 583;

    private static final String BROADCAST_ACTION_ITSELF =
            "com.alxminyaev.rssreader.core.service.NewsReaderService";
    private static final String BROADCAST_ACTION_FOR_APP =
            "com.alxminyaev.rssreader";

    private ArrayList<News> newsArrayList;

    private Thread loaderNewsThread;

    private NewsReaderBroadcastReceiver newsReaderBroadcastReceiver;

    private final class NewsReaderBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        newsReaderBroadcastReceiver = new NewsReaderBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_ITSELF);

        registerReceiver(newsReaderBroadcastReceiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loaderNewsThread.interrupt();
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
        final Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_ITSELF);
        intent.putExtra(COMMAND, TYPE_COMMAND_LOAD_NEWS);
        return intent;
    }

    @NotNull
    private static Intent getIntentNotifyNewsLoaded() {
        final Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_FOR_APP);
        intent.putExtra(STATUS, STATUS_NEWS_LOADED_TO_DB);
        return intent;
    }

    public void loadNews() {
        loaderNewsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                new NewsRepository(getApplicationContext()).loadAllNewsToDataBase();
                notifyReadyLoadedNews();
            }
        });

        loaderNewsThread.start();
    }


    private void notifyReadyLoadedNews() {
        sendBroadcast(getIntentNotifyNewsLoaded());
    }


}
