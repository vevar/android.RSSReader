package com.alxminyaev.rssreader.model.source_news;

import android.arch.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

final public class SourceNewsViewModel extends ViewModel {

    private SourceNewsMutableLiveData sourcesNews;

    public SourceNewsMutableLiveData getSourcesNews() {
        if (sourcesNews == null) {
            sourcesNews = new SourceNewsMutableLiveData();
        }

        return sourcesNews;
    }

    public void setSourcesNews(@NotNull final List<SourceNews> sourcesNews) {
        this.sourcesNews.setValue(sourcesNews);
    }
}
