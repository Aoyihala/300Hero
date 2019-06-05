package com.example.evenalone.a300hero.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.evenalone.a300hero.bean.DaoMaster;
import com.example.evenalone.a300hero.bean.DaoSession;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.ui.HomeActivity;
import com.example.evenalone.a300hero.ui.ListActivity;
import com.example.evenalone.a300hero.ui.SettingActivity;
import com.example.evenalone.a300hero.utils.OkhttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.database.Database;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks
{
    private static Context context;
    private static OkhttpUtils okhttpUtils;
    private static DaoSession daoSession;
    //全局代理词
    public static List<NetWorkProx> proxList = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        x.Ext.init(this);
        initdb();
        context = getApplicationContext();
        okhttpUtils = OkhttpUtils.getInstance();

    }

    private void initdb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"user");
        Database db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        daoSession = master.newSession();
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
        //战绩列表界面
        if (activity instanceof ListActivity)
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

    }
}
