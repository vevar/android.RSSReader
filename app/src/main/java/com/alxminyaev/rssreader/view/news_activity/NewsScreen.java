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

    final private NewsRecycleViewAdapter newsRecycleViewAdapter;

    NewsScreen(@NotNull final Activity activity) {

        RecyclerView newsRecycleView = activity.findViewById(R.id.listNews);
        newsRecycleView.setHasFixedSize(true);
        newsRecycleView.setLayoutManager(new LinearLayoutManager(activity));
        newsRecycleViewAdapter = new NewsRecycleViewAdapter(new ArrayList<News>());
        newsRecycleView.setAdapter(newsRecycleViewAdapter);

    }

    void updateListNews(@NotNull final List<News> listNews) {
        newsRecycleViewAdapter.setNewsList(listNews);
        newsRecycleViewAdapter.notify();
    }
}
