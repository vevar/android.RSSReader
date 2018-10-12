package com.axlminyaev.rssreader.dao;

public class RepositoryManager {

    static final String DATA_BASE_NAME = "dbRssReader";

    private static NewsRepository newsRepository;

    public static NewsRepository getNewRepository() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }

        return newsRepository;
    }


}
