package com.alxminyaev.rssreader.core.repository;

import android.database.Cursor;

import com.alxminyaev.rssreader.model.topic.Topic;

import java.util.List;

final public class TopicRepository extends ARepository<Topic> {

    @Override
    protected Topic getElementByCursor(Cursor cursor) {
        return null;
    }

    @Override
    public void add(Topic element) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Topic getById(int id) {
        return null;
    }

    @Override
    public List<Topic> getAll() {
        return null;
    }


}
