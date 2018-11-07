package com.alxminyaev.rssreader.model.source_news;

import android.arch.lifecycle.ViewModel;
import android.util.ArraySet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

final public class SourceNewsViewModel extends ViewModel {

    private ArraySet<SourceNews> sourcesNews;

    public ArraySet<SourceNews> getSourcesNews() {
        return sourcesNews;
    }

    public void setSourcesNews(@NotNull final ArraySet<SourceNews> sourcesNews) {
        this.sourcesNews = sourcesNews;
    }
}
