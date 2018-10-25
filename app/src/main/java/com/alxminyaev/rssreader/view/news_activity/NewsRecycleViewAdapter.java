package com.alxminyaev.rssreader.view.news_activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alxminyaev.rssreader.R;
import com.alxminyaev.rssreader.exception.view.IncorrectItemsOfViewException;
import com.alxminyaev.rssreader.exception.view.ViewException;
import com.alxminyaev.rssreader.model.news.News;

import java.util.List;

final class NewsRecycleViewAdapter extends RecyclerView.Adapter<NewsRecycleViewAdapter.NewsViewHolder> {

    private List<News> newsList;

    NewsRecycleViewAdapter(final List<News> newsList) {
        this.newsList = newsList;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);

        return new NewsViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder newsViewHolder, int i) {

        final News news = newsList.get(i);
        newsViewHolder.titleView.setText(news.getTitle());
        newsViewHolder.description.setText(news.getDescription());
    }



    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView description;

        NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleCard);
            description = itemView.findViewById(R.id.descriptionCard);
            //TODO Is this need???
            if (titleView == null || description == null) {
                try {
                    throw new IncorrectItemsOfViewException(
                            IncorrectItemsOfViewException.MESSAGE_INCORRECT_ITEMS_OF_VIEW);
                } catch (IncorrectItemsOfViewException e) {
                    Log.e(ViewException.TAG, e.getMessage());
                }
            }
        }

    }
}
