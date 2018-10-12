package com.axlminyaev.rssreader.dao;

import android.database.Cursor;

public abstract class ARepository<T> implements IRepository <T> {

    abstract protected T getElementByCursor(Cursor cursor);

}
