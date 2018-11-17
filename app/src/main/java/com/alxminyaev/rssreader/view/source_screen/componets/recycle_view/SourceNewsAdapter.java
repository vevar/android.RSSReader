package com.alxminyaev.rssreader.view.source_screen.componets.recycle_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alxminyaev.rssreader.core.repository.SourceNewsRepository;
import com.alxminyaev.rssreader.model.ViewModel;
import com.alxminyaev.rssreader.model.source_news.SourceNews;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

final public class SourceNewsAdapter extends RecyclerView.Adapter<SourceNewsAdapter.SourceNewsHolder> {

    private ViewModel<ArrayList<SourceNews>> sourceNewsViewModel;

    final private Context context;

    public SourceNewsAdapter(@NotNull final ViewModel<ArrayList<SourceNews>> sourceNewsList,
                             @NotNull final Context context) {
        this.sourceNewsViewModel = sourceNewsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SourceNewsHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final SourceNews sourceNews = sourceNewsViewModel.getState().get(i);

        final SourceNewsCardView sourceNewsCardView = new SourceNewsCardView(context, viewGroup);
        sourceNewsCardView.setButtonDeleteSourceOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new SourceNewsRepository(context).remove(sourceNews.getId());
                sourceNewsViewModel.getState().remove(i);
            }
        });

        sourceNewsCardView.setNameSource(sourceNews.getTitle());

        return new SourceNewsHolder(sourceNewsCardView);
    }

    final class SourceNewsHolder extends RecyclerView.ViewHolder {

        private SourceNewsCardView sourceNewsCardView;

        private SourceNewsHolder(@NonNull final SourceNewsCardView itemView) {
            super(itemView);
            this.sourceNewsCardView = itemView;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final SourceNewsHolder sourceNewsHolder, final int i) {
        final SourceNews sourceNews = sourceNewsViewModel.getState().get(i);

        sourceNewsHolder.sourceNewsCardView.setNameSource(sourceNews.getTitle());
        // TODO setImage of source

    }

    @Override
    public int getItemCount() {
        return sourceNewsViewModel.getState().size();
    }


}