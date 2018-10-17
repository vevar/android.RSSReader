package com.axlminyaev.rssreader.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.axlminyaev.rssreader.R;
import com.axlminyaev.rssreader.repository.model.News;
import com.axlminyaev.rssreader.service.NewsReaderService;

import java.util.List;

final public class MainActivity extends AppCompatActivity {

    private ListView listNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<News> allNews = new NewsReaderService().getAllNews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

