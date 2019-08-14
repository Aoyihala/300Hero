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
import android.util.Log;

import androidx.annotation.Nullable;

public class MyNotifiService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("data","定时查询服务已启用");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //5秒就挂
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notafi();
        }
        Log.e("data","定时查询服务已创建");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notafi() {
        NotificationChannel channel = null;

            channel = new NotificationChannel("id","name", NotificationManager.IMPORTANCE_LOW);

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        manager.createNotificationChannel(channel);


        Notification notification = new Notification.Builder(getBaseContext(),"id").build();

        startForeground(10, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
