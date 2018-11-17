package com.alxminyaev.rssreader.view.source_screen;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alxminyaev.rssreader.R;
import com.alxminyaev.rssreader.model.Observer;
import com.alxminyaev.rssreader.model.source_news.SourceNews;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

final class SourceNewsScreen implements Observer<ArrayList<SourceNews>> {

    private final Activity activity;

    private final RecyclerView mRecycleView;

    SourceNewsScreen(@NotNull final Activity activity) {
        this.activity = activity;
        this.activity.setContentView(R.layout.activity_source_new);
        mRecycleView = activity.findViewById(R.id.recycle_view_source_news);
//        mRecycleView.set1HasFixedSize(true);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mRecycleView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void update(@NotNull final ArrayList<SourceNews> modelDataList) {
//        mRecycleView.swapAdapter(new SourceNewsAdapter(modelDataList, activity),
//                true);
    }
}
