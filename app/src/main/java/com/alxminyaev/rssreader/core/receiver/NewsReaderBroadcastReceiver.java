package com.alxminyaev.rssreader.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class NewsReaderBroadcastReceiver extends BroadcastReceiver {

    public static final String COMMAND = "command";
    public static final String STATUS = "status";

    public static final int STATUS_NEWS_LOADED_TO_DB = 583;

    private static final int TYPE_COMMAND_LOAD_NEWS = 766;

    @Override
    public void onReceive(Context context, Intent intent) {
        int typeCommand = intent.getIntExtra(COMMAND, 0);
        switch (typeCommand) {
            case TYPE_COMMAND_LOAD_NEWS:

                break;
            default:
                break;
        }
    }
}
