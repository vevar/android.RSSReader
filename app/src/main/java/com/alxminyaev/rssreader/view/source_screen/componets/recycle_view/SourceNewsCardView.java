package com.alxminyaev.rssreader.view.source_screen.componets.recycle_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alxminyaev.rssreader.R;

import org.jetbrains.annotations.NotNull;

final public class SourceNewsCardView extends CardView {

    private ImageView imageSource;
    private TextView textViewNameSource;
    private Button buttonDeleteSource;

    public SourceNewsCardView(@NonNull final Context context, @NotNull final ViewGroup viewGroup) {
        super(context);

        inflate(context, R.layout.card_source_news, this);
        this.imageSource = findViewById(R.id.image_source);

        this.textViewNameSource = findViewById(R.id.text_view_name_source);

        this.buttonDeleteSource = findViewById(R.id.button_delete_source);

    }

    public void setNameSource(@NotNull final String nameSource) {
        this.textViewNameSource.setText(nameSource);
    }

    public void setButtonDeleteSourceOnClickListener(@NotNull final OnClickListener onClickListener) {
        buttonDeleteSource.setOnClickListener(onClickListener);
    }
}
