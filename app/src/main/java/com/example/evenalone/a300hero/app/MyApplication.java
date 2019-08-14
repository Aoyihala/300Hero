package com.example.evenalone.a300hero.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.evenalone.a300hero.bean.DaoMaster;
import com.example.evenalone.a300hero.bean.DaoSession;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.service.JobSchedulerManager;
import com.example.evenalone.a300hero.service.MyNotifiService;
import com.example.evenalone.a300hero.service.MyPushService;
import com.example.evenalone.a300hero.service.PushCallBackService;
import com.example.evenalone.a300hero.ui.HomeActivity;
import com.example.evenalone.a300hero.ui.ListActivity;
import com.example.evenalone.a300hero.ui.SettingActivity;
import com.example.evenalone.a300hero.utils.OkhttpUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.igexin.sdk.PushManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.database.Database;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks
{
    private static Context context;
    private static OkhttpUtils okhttpUtils;
    private static DaoSession daoSession;
    //全局代理词
    public static List<NetWorkProx> proxList = new ArrayList<>();
    private static Map<String,ListActivity> listActivities = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        context = getApplicationContext();
        x.Ext.init(this);
        initdb();
        PushManager.getInstance().initialize(context,MyPushService.class);
        PushManager.getInstance().registerPushIntentService(context,PushCallBackService.class);
        okhttpUtils = OkhttpUtils.getInstance();
        releasedata();
        //启用jobshulder
        JobSchedulerManager.getJobSchedulerInstance(context).startJobScheduler();
        /*Intent intent = new Intent(context,MyNotifiService.class);
        context.startForegroundService(intent);*/

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
    //列表activity的栈
    public static void addlistActivityStack(ListActivity activity,String name)
    {
        listActivities.put(name,activity);
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
        if (activity instanceof AppCompatActivity)
        {
            //随机页面启动
            SpUtils.setMoreMode(true);
            if (((AppCompatActivity) activity).getSupportActionBar()!=null)
            {
                //统一操作toolbar
            }
        }
        //属于战绩列表页面
        if (activity instanceof ListActivity)
        {
            ((ListActivity) activity).setTag(SpUtils.getNowUser());

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
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //判断是否是第一个界面
        if (activity instanceof ListActivity)
        {
            String mianuser = SpUtils.getMainUser();
           /* if (!TextUtils.isEmpty(mianuser)&&!TextUtils.isEmpty(((ListActivity) activity).getVisitor_name()))
            {
                String vist = ((ListActivity) activity).getVisitor_name();
                if (vist.equals(mianuser))
                {
                    //相同的什么也不干
                    return;
                }
                SpUtils.selectUser(mianuser);
                ((ListActivity) activity).initdatabyApplication();
                ((ListActivity) activity).initviewbyApplication();

            }*/
            if (!TextUtils.isEmpty(mianuser)&&!TextUtils.isEmpty(((ListActivity) activity).getVisitor_name()))
            {
                String vist = ((ListActivity) activity).getVisitor_name();
                if (vist.equals(mianuser))
                {
                    //获取相同的实列
                  SpUtils.selectUser(mianuser);
                  ((ListActivity) activity).initdatabyApplication(mianuser);
                }
            }
        }
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
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //战绩列表界面
        if (activity instanceof ListActivity)
        {
            unregister(activity);
        }
    }
}
