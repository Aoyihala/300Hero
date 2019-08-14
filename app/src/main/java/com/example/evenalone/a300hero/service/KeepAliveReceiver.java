package com.example.evenalone.a300hero.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * 静态是完全不用管的
 * 适配建议在app启动时加入动态注册
 * 监听系统广播，复活进程
 * (1) 网络变化广播
 * (2) 屏幕解锁广播
 * (3) 应用安装卸载广播
 * (4) 开机广播
 */
public class KeepAliveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //LogUtils.info("AliveBroadcastReceiver---->接收到的系统广播："+action);
        Log.e("当前广播",action);
        if (isAPPALive(context)) {
            //    LogUtils.info("AliveBroadcastReceiver---->APP还是活着的");
            Log.e("状态","存活");
            return;
        }
        Log.e("状态","被kill了");
        // LogUtils.info("AliveBroadcastReceiver---->复活进程(APP)");

    }
    /**
     * 判断本应用是否存活
     * 如果需要判断本应用是否在后台还是前台用getRunningTask
     */
    public static boolean isAPPALive (Context mContext){
        boolean isAPPRunning = false;
        // 获取activity管理对象
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有正在运行的app
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        // 遍历，进程名即包名
        for (ActivityManager.RunningAppProcessInfo appInfo : appProcessInfoList) {
            Log.e("当前进程", "alive process:" + appInfo.processName);
            if (appInfo.processName.endsWith(":a300hero")) {
                isAPPRunning = true;
                break;
            }
        }
        return isAPPRunning;
    }
}
