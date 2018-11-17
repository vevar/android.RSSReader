package com.alxminyaev.rssreader.core.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.model.additional_contracts.ContractSourceNewsManyToManyTopic;
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

  @NonNull
  @Override
  protected Topic getElementByCursor(@NotNull Cursor cursor) {
    final int indexId = cursor.getColumnIndex(Topic.Contract._ID);
    final int indexName = cursor.getColumnIndex(Topic.Contract.COLUMN_NAME_NAME);

    final int topicID = cursor.getInt(indexId);
    final HashSet<SourceNews> sourceNewsSet = getSetSourceNewsByTopicId(topicID);

    return new Topic(
            topicID,
            cursor.getString(indexName),
            sourceNewsSet
    );
  }

  @NonNull
  private HashSet<SourceNews> getSetSourceNewsByTopicId(int topicID) {
    final HashSet<SourceNews> setSourceNews = new HashSet<>();
    final SQLiteDatabase readableDatabase = new RSSReaderDbHelper(context).getReadableDatabase();
    Cursor cursor = null;
    try {
      cursor = readableDatabase
              .query(
                      ContractSourceNewsManyToManyTopic.TABLE_NAME,
                      new String[]{ContractSourceNewsManyToManyTopic.COLUMN_NAME_SOURCE_NEWS_ID},
                      ContractSourceNewsManyToManyTopic.COLUMN_NAME_TOPIC_ID + " = ?",
                      new String[]{String.valueOf(topicID)},
                      null, null, null
              );
      if (cursor.moveToFirst()) {

        int sourceNewsIdIndex = cursor.getColumnIndex(
                ContractSourceNewsManyToManyTopic.COLUMN_NAME_SOURCE_NEWS_ID);
        final SourceNewsRepository sourceNewsRepository = new SourceNewsRepository(context);
        do {
          final int idSourceNews = cursor.getInt(sourceNewsIdIndex);
          final SourceNews sourceNews = sourceNewsRepository.getById(idSourceNews);
          setSourceNews.add(sourceNews);
        } while (cursor.moveToNext());
      }

      return setSourceNews;
    } finally {
      if (cursor != null) {
        cursor.close();
      }
      readableDatabase.close();
    }
  }


  @Override
  public void save(@NotNull Topic topic) {
    final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);
    final SQLiteDatabase writableDatabase = rssReaderDbHelper.getWritableDatabase();

    try {
      writableDatabase.beginTransaction();

      saveToTopicTable(writableDatabase, topic);
      saveToSourceNewsManyToManyTopic(writableDatabase, topic);

      writableDatabase.setTransactionSuccessful();
    } finally {
      writableDatabase.endTransaction();
      rssReaderDbHelper.close();
    }
  }

  private void saveToSourceNewsManyToManyTopic(@NotNull final SQLiteDatabase writableDatabase,
                                               @NotNull final Topic topic) {
    final ContentValues contentTopicIdSourceId = new ContentValues();

    contentTopicIdSourceId
            .put(
                    ContractSourceNewsManyToManyTopic.COLUMN_NAME_TOPIC_ID,
                    topic.getId()
            );

    for (SourceNews sourceNews : topic.getSourceNews()) {
      contentTopicIdSourceId.
              put(
                      ContractSourceNewsManyToManyTopic.COLUMN_NAME_TOPIC_ID,
                      sourceNews.getId()
              );

      writableDatabase
              .insert(
                      ContractSourceNewsManyToManyTopic.TABLE_NAME,
                      null,
                      contentTopicIdSourceId
              );
    }
  }

  private void saveToTopicTable(@NotNull final SQLiteDatabase writableDatabase, @NotNull final Topic topic) {
    final ContentValues contentTopic = new ContentValues();
    contentTopic.put(Topic.Contract.COLUMN_NAME_NAME, topic.getName());
    writableDatabase
            .insert(Topic.Contract.TABLE_NAME, null, contentTopic);

  }

  @Override
  public void remove(int id) {
    final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

    try {
      rssReaderDbHelper.getWritableDatabase()
              .delete(
                      Topic.Contract.TABLE_NAME,
                      Topic.Contract._ID + " = ?", new String[]{String.valueOf(id)}
              );
    } finally {
      rssReaderDbHelper.close();
    }
  }

  @NonNull
  @Override
  public Topic getById(int id) {
    final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);

    try {
      final Cursor cursor = rssReaderDbHelper.getReadableDatabase()
              .query(
                      Topic.Contract.TABLE_NAME, null,
                      Topic.Contract._ID + " = ?", new String[]{String.valueOf(id)},
                      null, null, null);
      return getElementByCursor(cursor);
    } finally {
      rssReaderDbHelper.close();
    }
  }

  @Nullable
  @Override
  public ArrayList<Topic> getAll() {

    final RSSReaderDbHelper rssReaderDbHelper = new RSSReaderDbHelper(context);
    try {
      final Cursor cursor = rssReaderDbHelper.getReadableDatabase().query(
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
    } finally {
      rssReaderDbHelper.close();
    }
  }


}
