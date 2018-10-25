package com.alxminyaev.rssreader.model.news;

import android.arch.lifecycle.ViewModel;

import java.util.List;

final public class NewsViewModel extends ViewModel {

    private NewsMutableLiveData listNews;

    public NewsMutableLiveData getListNews() {
        if (listNews == null){
            listNews = new NewsMutableLiveData();
        }

        return listNews;
    }

    public void setListNews(List<News> listNews) {
        this.listNews.setValue(listNews);
    }
}
