package com.alxminyaev.rssreader.core.repository.exception;

public class DataBaseException extends Exception {
    public static String TAG = "DataBase";

    public DataBaseException(String message) {
        super(message);
    }
}
