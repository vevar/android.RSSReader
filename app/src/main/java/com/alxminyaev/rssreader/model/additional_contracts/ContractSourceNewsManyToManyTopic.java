package com.alxminyaev.rssreader.model.additional_contracts;

public final class ContractSourceNewsManyToManyTopic {
    private ContractSourceNewsManyToManyTopic() {
    }

    public static final String TABLE_NAME = "source_news_many_to_many_topic";

    public static final String COLUMN_NAME_SOURCE_NEWS_ID = "id_source_news";
    public static final String COLUMN_NAME_TOPIC_ID = "id_topic";
}