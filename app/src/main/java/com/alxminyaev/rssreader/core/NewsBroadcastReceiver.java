package com.alxminyaev.rssreader.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alxminyaev.rssreader.model.news.NewsViewModel;

final public class NewsBroadcastReceiver extends BroadcastReceiver {

    private NewsViewModel newsViewModel;

    NewsBroadcastReceiver(NewsViewModel newsViewModel){
        this.newsViewModel = newsViewModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
