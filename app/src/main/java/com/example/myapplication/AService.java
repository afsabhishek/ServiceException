package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AService extends Service {
  private static final String TAG = "ForegroundService";
  private static final String CHANNEL_ID = "11";
  private static final String CHANNEL_NAME = "ForegroundServiceChannel";

  @Override
  public void onCreate() {
    super.onCreate();
    dummyNotification();
  }

  private void dummyNotification() {
    NotificationChannel channel = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH);
    }

    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      manager.createNotificationChannel(channel);
    }

    Notification notification = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      notification = new Notification.Builder(getApplicationContext(),CHANNEL_ID).build();
    }
    startForeground(1, notification);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, "onStartCommand: ");
    dummyNotification();
    return super.onStartCommand(intent, flags, startId);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind: ");
    return null;
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy: ");
    super.onDestroy();
  }
}
