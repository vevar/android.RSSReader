package com.alxminyaev.rssreader.core.repository;

import java.io.IOException;
import java.util.List;

interface IRepository <T>{

    void add(T element);

    void remove(int id);

    T getById(int id);

    List<T> getAll() throws IOException;
}
