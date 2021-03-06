package com.example.evenalone.a300hero.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.example.evenalone.a300hero.bean.DaoMaster;
import com.example.evenalone.a300hero.bean.DaoSession;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.bean.UpdateBean;
import com.example.evenalone.a300hero.service.JobSchedulerManager;
import com.example.evenalone.a300hero.service.MyFrontService;
import com.example.evenalone.a300hero.service.MyPushService;
import com.example.evenalone.a300hero.service.PushCallBackService;
import com.example.evenalone.a300hero.service.UpdateService;
import com.example.evenalone.a300hero.ui.HomeActivity;
import com.example.evenalone.a300hero.ui.ListActivity;
import com.example.evenalone.a300hero.ui.SettingActivity;
import com.example.evenalone.a300hero.ui.TipoffActivity;
import com.example.evenalone.a300hero.utils.ImageCenter;
import com.example.evenalone.a300hero.utils.MyCatchException;
import com.example.evenalone.a300hero.utils.OkhttpUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.SystemUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.database.Database;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



@SuppressLint("NewApi")
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks
{
    private static Context context;
    private static OkhttpUtils okhttpUtils;
    private static DaoSession daoSession;
    private static ImageCenter imageCenter;
    private static Timer timer_update;
    //15分钟请求一次
    private static long time = 1000*15*60;
    //全局代理词
    public static List<NetWorkProx> proxList = new ArrayList<>();
    //全局的list页面
    public static ListActivity listactivity;
    public static Map<String,ListActivity> listActivityMap = new LinkedHashMap<>();
    private static List<Activity> activitiesall = new LinkedList<>();

    public static void startUpdateTask() {
        Log.e("主动更新服务","启动");
        Intent intent = new Intent(context,UpdateService.class);
        context.startService(intent);
    }

    public static void stopUpdateTask() {

        Log.e("更新服务","停止");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        context = getApplicationContext();
        x.Ext.init(this);
        //全局异常监听
        MyCatchException mException=MyCatchException.getInstance();
        mException.init(getApplicationContext());
        initdb();
        initdownloader();
        PushManager.getInstance().initialize(context,MyPushService.class);
        PushManager.getInstance().registerPushIntentService(context,PushCallBackService.class);
        okhttpUtils = OkhttpUtils.getInstance();
        imageCenter = ImageCenter.getInstance(context);
      /*  Intent intent = new Intent(context,MyFrontService.class);
        startService(intent);*/
        checkservice();
        startUpdateTask();

       // releasedata();
        //启用jobshulder
      /*  JobSchedulerManager.getJobSchedulerInstance(context).startJobScheduler();*/
        /*Intent intent = new Intent(context,MyNotifiService.class);
        context.startForegroundService(intent);*/

    }

    public static ListActivity getListactivity() {
        return listactivity;
    }

    public static ImageCenter getImageCenter() {
        return imageCenter;
    }

    private void initdownloader() {
        PRDownloader.initialize(getApplicationContext());

// Enabling database for resume support even after the application is killed:
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);

// Setting timeout globally for the download network requests:
        PRDownloaderConfig config1 = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        PRDownloader.initialize(getApplicationContext(), config1);
    }

    private void checkservice() {
        if (SpUtils.isClock())
        {
            if (!SystemUtils.isServiceWork(MyApplication.getContext(),"com.example.evenalone.a300hero.service.MyNotifiService"))
            {
                //服务没有运行
                JobSchedulerManager.getJobSchedulerInstance(context).startJobScheduler();
            }
        }


    }

    private void inittalk() {

    }

    private void releasedata() {
        //释放资源
        //释放
        SpUtils.setMianUser(null);

    }

    private void initdb() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"user");
        Database db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        daoSession = master.newSession();
    }
    //回转页面只剩一个
    public static void removeStack()
    {
        if (activitiesall.size()>0)
        {
            for (int i=0;i<activitiesall.size();i++)
            {
                if (i>0)
                {
                    activitiesall.get(i).finish();
                }
            }
            Toast.makeText(context, "已回到首页", Toast.LENGTH_SHORT).show();
        }
    }


    public static void removeStack2(HomeActivity homeActivity)
    {
        for (Activity activity:activitiesall)
        {
            if (null!=activity&&!(activity instanceof HomeActivity))
            {
                activity.finish();
            }
        }
    }




    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getContext() {
        return context;
    }

    public static OkhttpUtils getOkhttpUtils() {
        return okhttpUtils;
    }

    public void register(Activity activity)
    {
        if (!EventBus.getDefault().isRegistered(activity))
        {
            EventBus.getDefault().register(activity);
        }
    }

    public void unregister(Activity activity)
    {
        EventBus.getDefault().unregister(activity);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activitiesall.add(activity);
        if (activity instanceof AppCompatActivity)
        {
            //随机页面启动
            SpUtils.setMoreMode(true);
            if (((AppCompatActivity) activity).getSupportActionBar()!=null)
            {
                //统一操作toolbar
            }
        }


    }

    @Override
    public void onActivityStarted(Activity activity) {
        //绑定界面
        if (activity instanceof HomeActivity)
        {
            register(activity);
        }
        //战绩列表界面
        if (activity instanceof ListActivity)
        {
            register(activity);
        }
        //设置界面
        if (activity instanceof SettingActivity)
        {
            register(activity);
        }
        //举报界面
        if (activity instanceof TipoffActivity)
        {
            register(activity);
        }

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
        //绑定界面
        if (activity instanceof HomeActivity)
        {
            unregister(activity);
        }

        //设置界面
        if (activity instanceof SettingActivity)
        {
            unregister(activity);
        }
        //战绩列表界面
        if (activity instanceof ListActivity)
        {
            unregister(activity);
           /* //移除
            listActivityMap.remove(((ListActivity) activity).getNickname());*/
        }
        //举报界面
        if (activity instanceof TipoffActivity)
        {
            unregister(activity);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activitiesall.remove(activity);
    }
}
