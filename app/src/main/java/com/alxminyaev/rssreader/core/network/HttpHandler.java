package com.alxminyaev.rssreader.core.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.alxminyaev.rssreader.exception.core.CoreException;
import com.alxminyaev.rssreader.exception.core.IncorrectProtocolInURL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

final public class HttpHandler {

    private final static String PROTOCOL = "http";

    private final static int AMOUNT_RECONNECT = 4;

    private final static String REQUEST_METHOD_GET = "GET";

    private final static int FIRST_TIMEOUT_SECONDS = 2;

    @Nullable
    public static InputStream GetHTTPInputStream(final URL url) throws IOException, IncorrectProtocolInURL {
        HttpURLConnection urlConnection = null;
        if (PROTOCOL.equals(url.getProtocol())) {
            HttpURLConnection.setFollowRedirects(false);

            for (int i = 1; i < AMOUNT_RECONNECT; i++) {
                try {
                    urlConnection = tryConnect(url, (int) Math.pow(FIRST_TIMEOUT_SECONDS, i) * 1000);
                } catch (SocketTimeoutException e) {
                    if (i != AMOUNT_RECONNECT - 1) {
                        Log.w(CoreException.TAG, e.getMessage(), e);
                    } else {
                        Log.e(CoreException.TAG, e.getMessage(), e);
                    }
                }
            }

            if (urlConnection != null) {
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return urlConnection.getInputStream();
                }
            }

        } else {
            throw new IncorrectProtocolInURL();
        }
        return null;
    }

    @Nullable
    private static HttpURLConnection tryConnect(@NotNull final URL url, final int time) throws SocketTimeoutException {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD_GET);
            urlConnection.setConnectTimeout(time);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            Log.e(CoreException.TAG, e.getMessage(), e);
        }
        return urlConnection;
    }
}
