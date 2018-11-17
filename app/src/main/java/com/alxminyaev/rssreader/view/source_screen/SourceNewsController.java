package com.alxminyaev.rssreader.view.source_screen;

import com.alxminyaev.rssreader.core.repository.SourceNewsRepository;
import com.alxminyaev.rssreader.model.ViewModel;
import com.alxminyaev.rssreader.model.source_news.SourceNews;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

final class SourceNewsController {

    private final ViewModel<ArrayList<SourceNews>> sourceNewsViewModel;

    private final SourceNewsActivity activity;

    SourceNewsController(@NotNull final SourceNewsActivity activity) {
        this.activity = activity;
        sourceNewsViewModel = new ViewModel<>();
    }

    void createScreen() {
        final SourceNewsScreen sourceNewsScreen = new SourceNewsScreen(activity);
        sourceNewsViewModel.addObserver(sourceNewsScreen);
    }

    void showScreenInFront() {
        final ArrayList<SourceNews> sourceNewList = new SourceNewsRepository(activity).getAll();

        if (sourceNewList != null) {
            sourceNewsViewModel.setState(sourceNewList);
        }
    }
}
