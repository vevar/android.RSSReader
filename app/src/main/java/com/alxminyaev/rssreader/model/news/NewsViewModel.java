package com.alxminyaev.rssreader.model.news;

import android.arch.lifecycle.ViewModel;

final public class NewsViewModel extends ViewModel {

    private NewsMutableLiveData listNews;

    public NewsMutableLiveData getListNews() {
        if (listNews == null){
            listNews = new NewsMutableLiveData();
        }

        return listNews;
    }

}
