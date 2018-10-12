package com.axlminyaev.rssreader.dao;

public interface IRepository <T>{

    void add(T element);

    T remove(int id);

    T getById(int id);
}
