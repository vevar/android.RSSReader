package com.alxminyaev.rssreader.core.service;

import com.alxminyaev.rssreader.model.SourceNews;

final class NewsTaskBuilder{

    static Runnable tastGetAllNews(){
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    static Runnable taskGetAllNews(SourceNews sourceNews){
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    static Runnable taskCheckUpdate(){
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    static Runnable taskCheckUpdate(SourceNews sourceNews){
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }


}
