package com.alxminyaev.rssreader.core;

import android.support.annotation.Nullable;

import com.alxminyaev.rssreader.exception.core.IncorrectProtocolInURL;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final public class HttpHandler {

    private final static String PROTOCOL = "http";

    private final static int TIMEOUT_MILLISECONDS = 5000;

    @Nullable
    public static InputStream GetHTTPInputStream(final URL url) throws IOException, IncorrectProtocolInURL {

        if (PROTOCOL.equals(url.getProtocol())) {
            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(TIMEOUT_MILLISECONDS);
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return urlConnection.getInputStream();
            }
        } else {
            throw new IncorrectProtocolInURL();
        }
        return null;
    }

}
