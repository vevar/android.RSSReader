package com.axlminyaev.rssreader.core.repository;

import java.util.List;

interface IRepository <T>{

    void add(T element);

    void remove(int id);

    T getById(int id);

    List<T> getAll();
}
