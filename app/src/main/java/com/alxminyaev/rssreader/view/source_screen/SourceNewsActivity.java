package com.alxminyaev.rssreader.view.source_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


final public class SourceNewsActivity extends AppCompatActivity {


    private final SourceNewsController sourceNewsController;

    public SourceNewsActivity() {
        sourceNewsController = new SourceNewsController(this);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sourceNewsController.createScreen();
    }

    @Override
    protected void onStart() {
        super.onStart();

        sourceNewsController.showScreenInFront();
    }
}
