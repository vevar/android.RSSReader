package com.alxminyaev.rssreader.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewModel<ModelData> {

    private ArrayList<Observer<ModelData>> observerList;

    private ModelData modelDataList;

    public ViewModel() {
        observerList = new ArrayList<>();
    }

    public ViewModel(@NotNull final ModelData modelDataList) {
        super();
        this.modelDataList = modelDataList;
    }

    final public void addObserver(@NotNull final Observer<ModelData> observer) {
        observerList.add(observer);
    }

    final public void setState(@NotNull final ModelData modelDataList) {
        this.modelDataList = modelDataList;
        notifyAllObservers();
    }

    final public ModelData getState() {
        return modelDataList;
    }

    private void notifyAllObservers() {
        for (Observer<ModelData> observer : observerList) {
            observer.update(modelDataList);
        }
    }
}
