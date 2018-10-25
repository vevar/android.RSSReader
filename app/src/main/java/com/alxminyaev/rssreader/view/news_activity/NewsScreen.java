package com.alxminyaev.rssreader.view.news_activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alxminyaev.rssreader.R;
import com.alxminyaev.rssreader.model.news.News;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class NewsScreen {

    private Activity activity;

    private RecyclerView newsRecycleView;


    NewsScreen(@NotNull final Activity activity) {
        this.activity = activity;
    }

    void createScreen(){
        setupNewsRecycleView();
    }

    private void setupNewsRecycleView(){
        newsRecycleView = activity.findViewById(R.id.listNews);
        newsRecycleView.setHasFixedSize(true);
        newsRecycleView.setLayoutManager(new LinearLayoutManager(activity));
        //TODO need get list of news
        final List<News> newsList = new ArrayList<>();
        NewsRecycleViewAdapter newsRecycleViewAdapter = new NewsRecycleViewAdapter(newsList);
        newsRecycleView.setAdapter(newsRecycleViewAdapter);
    }



}
