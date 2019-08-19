package com.example.evenalone.a300hero.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import androidx.annotation.Nullable;

public class NotfiService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notafi();
            stopForeground(true);
            stopSelf();
        }
    }
    //针对于安卓8才有的正在通知字样
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notafi() {
        NotificationChannel channel = null;
        channel = new NotificationChannel("id","正在运行", NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getBaseContext(),"id").build();
        startForeground(10, notification);
    }

}
