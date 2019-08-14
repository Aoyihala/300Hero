package com.example.evenalone.a300hero.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.utils.SystemUtils;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.util.List;

public class PushCallBackService extends GTIntentService
{


    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.e("当前客户端id",s);
        //判断app是否在后台
        if (isBackground(MyApplication.getContext()))
        {


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

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        //透传信息

    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        Log.e("当前在线状态",b+"");
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
}

