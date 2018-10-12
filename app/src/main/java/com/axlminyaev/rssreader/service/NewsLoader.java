package com.axlminyaev.rssreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.axlminyaev.rssreader.model.SourceNews;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

final public class NewsLoader extends Service {

    final private List<SourceNews> listLoadedURL;
    final private Date dateLastCheck;

    public NewsLoader(){
        listLoadedURL = new ArrayList<>();
        dateLastCheck = Calendar.getInstance().getTime();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return ;
    }

    public void addSourceNews(SourceNews sourceNews){
        listLoadedURL.add(sourceNews.getId(), sourceNews);
    }

    public void removeSourceNews(int id){
        listLoadedURL.remove(id);
    }

    private InputStream getInputStream(URL url) throws IOException {
        return url.openConnection().getInputStream();
    }

}
