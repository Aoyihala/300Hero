package com.example.evenalone.a300hero.service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.SystemUtils;

//安卓5.0以上使用
@SuppressLint("Registered")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobTimer extends JobService {
    private String nickname;
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
            //保存一个cachename
            nickname = SpUtils.getNowUser();
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
        else
       {
           if (!TextUtils.isEmpty(nickname))
           {
               if (nickname.equals(SpUtils.getNowUser()))
               {
                   //到这里就该结束了
                   jobFinished(params,true);
                   return true;
               }
           }
           //这得一定是换人的情况下才能用
           /*MyApplication.getContext().bindService(new Intent(this, MyNotifiService.class), new ServiceConnection() {
               @Override
               public void onServiceConnected(ComponentName name, IBinder service) {
                   MyNotifiService.MyBind myBind = (MyNotifiService.MyBind) service;
                   myBind.startDownload();
               }

               @Override
               public void onServiceDisconnected(ComponentName name) {

               }
           },BIND_AUTO_CREATE);*/
       }
        jobFinished(params,true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("服务停止--","意外停止或主动kill");
        timer = null;

        return true;
    }
}
