package com.alxminyaev.rssreader.exception.core;

public class CoreException extends Exception {
    public static final String TAG = "Core exception";

    CoreException(String message) {
        super(message);
    }
}
