package com.alxminyaev.rssreader.exception.view;

public class ViewException extends Exception {
    public final static String TAG = "View exception";

    ViewException(String message) {
        super(message);
    }
}
