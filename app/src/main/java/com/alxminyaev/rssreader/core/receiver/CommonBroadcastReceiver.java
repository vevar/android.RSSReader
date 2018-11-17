package com.alxminyaev.rssreader.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alxminyaev.rssreader.core.service.NewsReaderService;

final public class CommonBroadcastReceiver extends BroadcastReceiver {

  CommonBroadcastReceiver() {

  }

  @Override
  public void onReceive(final Context context, final Intent intent) {
    final int status = intent.getIntExtra(NewsReaderService.STATUS, 0);
    switch (status){
      case NewsReaderService.STATUS_NEWS_LOADED_TO_DB:

        break;
      default:
        break;
    }
  }
}
