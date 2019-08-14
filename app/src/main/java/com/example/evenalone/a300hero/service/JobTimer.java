package com.example.evenalone.a300hero.service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.utils.SystemUtils;

//安卓5.0以上使用
@SuppressLint("Registered")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobTimer extends JobService {
    private static  JobTimer timer = null;
    public static boolean isJobServiceAlive() {
        return timer==null?false:true;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("服务启用--","存活");
        timer = this;
        if (!SystemUtils.isServiceWork(MyApplication.getContext(),"com.example.evenalone.a300hero.service.MyNotifiService"))
        {
            Log.e("data","重新唤醒服务");
            //没有存活
            Intent intent = new Intent(MyApplication.getContext(),MyNotifiService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                MyApplication.getContext().startForegroundService(intent);
            }
            else
            {
                MyApplication.getContext().startService(intent);
            }
        }
        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("服务启用--","意外停止或主动kill");
        timer = null;

        return true;
    }
}
