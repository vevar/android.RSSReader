package com.alxminyaev.rssreader.model.topic;

import org.jetbrains.annotations.NotNull;

import java.util.List;

final public class TopicViewModel {

    private TopicMutableLiveData topics;

    public TopicMutableLiveData getTopics(){
        if (topics == null){
            topics = new TopicMutableLiveData();
        }
        return topics;
    }

    public void setTopics(@NotNull final List<Topic> topics) {
        this.topics.setValue(topics);
    }
}
