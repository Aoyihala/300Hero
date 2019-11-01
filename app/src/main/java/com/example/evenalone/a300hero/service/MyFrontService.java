package com.example.evenalone.a300hero.service;

import android.annotation.SuppressLint;
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

public class MyFrontService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notafi();
            //不执行的话
            createNotificationChannel("data_000000","战绩定时通知",NotificationManager.IMPORTANCE_MAX);
        }
        super.onCreate();
    }


    /**
     * 创建通知类型
     * @param channelId
     * @param channelName
     * @param importance
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    //针对于安卓8才有的正在通知字样
    @SuppressLint("NewApi")
    private void notafi() {
        NotificationChannel channel = null;
        channel = new NotificationChannel("ddddddd","正在运行", NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getBaseContext(),"ddddddd").build();
        startForeground(12, notification);
        Intent intent = new Intent(getBaseContext(),NotfiService.class);
        getBaseContext().startForegroundService(intent);
    }
}
