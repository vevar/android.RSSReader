package com.alxminyaev.rssreader.core.repository;

import java.util.List;

interface IRepository <T>{

    void save(T element);

    void remove(int id);

    T getById(int id);

    List<T> getAll();
}
