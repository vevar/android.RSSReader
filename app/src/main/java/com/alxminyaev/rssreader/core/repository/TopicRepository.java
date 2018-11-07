package com.alxminyaev.rssreader.core.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.alxminyaev.rssreader.model.source_news.SourceNews;
import com.alxminyaev.rssreader.model.topic.Topic;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

final public class TopicRepository extends ARepository<Topic> {

    private final Context context;

    TopicRepository(@NotNull final Context context) {
        this.context = context;
    }

    @Override
    protected Topic getElementByCursor(Cursor cursor) {
        final int indexId = cursor.getColumnIndex(Topic.Contract._ID);
        final int indexName = cursor.getColumnIndex(Topic.Contract.COLUMN_NAME_NAME);

        final int topicID = cursor.getInt(indexId);

        //TODO many to many
        final HashSet<SourceNews> sourceNewsSet = getSetSourceNewsByTopicId(topicID);

        return new Topic(
                topicID,
                cursor.getString(indexName),
                sourceNewsSet
        );
    }

    private HashSet<SourceNews> getSetSourceNewsByTopicId(int topicID) {
        HashSet<SourceNews> sourceNews = new HashSet<>();

        Cursor cursor = new RSSReaderDbHelper(context).getReadableDatabase()
                .query(
                        RSSReaderDbHelper.ContractSourceNewsManyToManyTopic.TABLE_NAME,
                        new String[]{RSSReaderDbHelper.ContractSourceNewsManyToManyTopic.COLUMN_NAME_SOURCE_NEWS_ID},
                        RSSReaderDbHelper.ContractSourceNewsManyToManyTopic.COLUMN_NAME_TOPIC_ID + " = ?",
                        new String[]{String.valueOf(topicID)},
                        null, null, null
                );

        return null;
    }


    @Override
    public void save(Topic element) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        final ContentValues contentValues = new ContentValues();
        contentValues.put(Topic.Contract.COLUMN_NAME_NAME, element.getName());

        //TODO save to table SourceNew many to many Topic

        rssReaderDbHelper.getWritableDatabase()
                .insert(Topic.Contract.TABLE_NAME, null, contentValues);

        rssReaderDbHelper.close();
    }

    @Override
    public void remove(int id) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        rssReaderDbHelper.getWritableDatabase()
                .delete(
                        Topic.Contract.TABLE_NAME,
                        Topic.Contract._ID + " = ?", new String[]{String.valueOf(id)}
                );
    }

    @NonNull
    @Override
    public Topic getById(int id) {
        final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

        final Cursor cursor = rssReaderDbHelper.getReadableDatabase()
                .query(
                        Topic.Contract.TABLE_NAME, null,
                        Topic.Contract._ID + " = ?", new String[]{String.valueOf(id)},
                        null, null, null);
        return getElementByCursor(cursor);
    }

    @Override
    public ArrayList<Topic> getAll() {

        RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);
        Cursor cursor = rssReaderDbHelper.getReadableDatabase().query(
                Topic.Contract.TABLE_NAME, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            final ArrayList<Topic> listTopics = new ArrayList<>();
            Topic topic;
            do {
                topic = getElementByCursor(cursor);
                listTopics.add(topic);
            } while (cursor.moveToNext());

            return listTopics;
        }

        return null;
    }


}
