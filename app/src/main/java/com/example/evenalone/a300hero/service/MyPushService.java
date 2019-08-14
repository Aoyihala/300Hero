package com.example.evenalone.a300hero.service;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.utils.SystemUtils;
import com.igexin.sdk.PushService;

public class MyPushService extends PushService
{

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("推送服务已运行","运行中");
        //判断app是否在后台
        if (SystemUtils.isBackground(MyApplication.getContext()))
        {
            //在后台


                //判断关键服务是否运行
                if (!SystemUtils.isServiceWork(MyApplication.getContext(),"com.example.evenalone.a300hero.service.MyNotifiService"))
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        JobSchedulerManager.getJobSchedulerInstance(MyApplication.getContext()).startJobScheduler();
                    }
                    //
                    Log.e("data","唤醒服务");
                    //唤醒
                    Intent intent = new Intent(MyApplication.getContext(),MyNotifiService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        MyApplication.getContext().startForegroundService(intent);
                    }
                    else
                    {
                        MyApplication.getContext().startService(intent);
                    }


            }
        }
    }
}
