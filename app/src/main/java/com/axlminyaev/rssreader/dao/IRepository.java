package com.axlminyaev.rssreader.dao;

interface IRepository <T>{

    void add(T element);

    void remove(int id);

    T getById(int id);
}
