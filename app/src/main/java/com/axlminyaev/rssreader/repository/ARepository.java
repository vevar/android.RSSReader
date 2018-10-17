package com.axlminyaev.rssreader.repository;

import android.database.Cursor;

public abstract class ARepository<T> implements IRepository <T> {

    abstract protected T getElementByCursor(Cursor cursor);

}
