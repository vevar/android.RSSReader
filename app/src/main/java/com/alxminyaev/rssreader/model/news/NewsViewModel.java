package com.alxminyaev.rssreader.model.news;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

final public class NewsViewModel extends ViewModel {

    private NewsMutableLiveData listNews;

    public List<News> getListNews() {
        if (listNews == null){
            listNews = new NewsMutableLiveData();
        }

        return listNews.getValue();
    }

    public void setListNews(@NotNull final List<News> listNews) {
        this.listNews.setValue(listNews);
    }

    public void setObserver(@NotNull final LifecycleOwner owner, @NotNull final Observer<List<News>> observer){
        listNews.observe(owner, observer);
    }
}
