package com.alxminyaev.rssreader.core;

import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

final public class HttpHandler {

    @Nullable
    public static String GetHTTPData(final URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String result = null;

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            result = getDataFromInputStream(urlConnection.getInputStream());
        }

        return result;
    }

    @Nullable
    public static InputStream GetHTTPInputStream(final URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return urlConnection.getInputStream();
        }
        return null;
    }

    private static String getDataFromInputStream(InputStream inputStream) throws IOException {
        final BufferedReader bufferedReaderData = new BufferedReader(
                new InputStreamReader(
                        inputStream
                ));

        final StringBuilder stringBuilderData = new StringBuilder();

        String line;
        while ((line = bufferedReaderData.readLine()) != null) {
            stringBuilderData.append(line);
        }

        return stringBuilderData.toString();
    }
}
