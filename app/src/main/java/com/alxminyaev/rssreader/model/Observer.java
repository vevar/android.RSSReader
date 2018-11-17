package com.alxminyaev.rssreader.model;

public interface Observer<ModelData> {
    void update(ModelData modelDataList);
}
