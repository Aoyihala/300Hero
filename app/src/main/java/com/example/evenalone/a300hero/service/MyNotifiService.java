package com.example.evenalone.a300hero.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.event.ListInfoEvent;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.OkhttpUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.SystemUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import okhttp3.Request;

public class MyNotifiService extends Service {
    private Timer timer;
    private int not_id=1;
    //15分钟获取一次
    private long time = 1000*60*15;
    private ArrayList<HeroGuide.ListBean> listBeans;


    public class MyBind extends Binder {
        public void startDownload() {
            if (timer==null)
            {
                timer = new Timer();
            }
            Log.d("换人", "开始前期");
            // 执行具体的下载任务
            startRequest();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startRequest();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startRequest()
    {
        Log.e("data","定时查询服务已启用");
        if (timer!=null)
        {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //一分钟执行一次
                    String name = SpUtils.getMainUser()==null?SpUtils.getNowUser():SpUtils.getMainUser();
                    Log.e("data","执行了请求,用户是:"+name);
                    //执行网络请求
                    //前十个最新,不需要传index
                    Request request = new Request.Builder()
                            .url(Contacts.LIST_URL+"?name="+name)
                            .build();
                    OkhttpUtils.getInstance().sendRequest(request,HeroGuide.class);
                    //会延时10秒启动第一次
                }
            },10000,time);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        //5秒就挂

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notafi();
            //不执行的话
            createNotificationChannel("data","战绩定时通知",NotificationManager.IMPORTANCE_MAX);
        }
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        timer = new Timer();
        if (SystemUtils.isBackground(getBaseContext()))
        {
            //后台创建的服务不会执行
            startRequest();
        }


        Log.e("data","定时查询服务已创建");
    }
    //针对于安卓8才有的正在通知字样
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notafi() {
        NotificationChannel channel = null;
        channel = new NotificationChannel("id","正在运行", NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getBaseContext(),"id").build();
        startForeground(10, notification);
        Intent intent = new Intent(getBaseContext(),NotfiService.class);
        getBaseContext().startForegroundService(intent);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        timer = null;
        super.onDestroy();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        EventBus.getDefault().unregister(this);
        timer = null;
        return super.onUnbind(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getListData(ListInfoEvent eva)
    {
        //获取
        //判断当前应用是否处于后台
        if (SystemUtils.isBackground(getBaseContext()))
        {
            //处于后台才处理事件
            //如果是在前台的话,内容会回调到页面订阅
            //展示数据
            if (eva.isSuccess())
            {
                //保存
                if (eva.getGuide() == null) {
                    Log.e("更新数据失败", "dddddd");
                    return;
                }
                HeroGuide guide = eva.getGuide();
                if (guide == null) {
                    return;
                }
                Log.e("获取到的数据",guide.getLocalruselt());
                listBeans = new ArrayList<>();
                listBeans.addAll(eva.getGuide().getList());
                //不存本地
                if (listBeans.size()>0)
                {
                    //获取第一个
                    showMsg(listBeans.get(0));
                }
            }
        }

    }
    public void showMsg(HeroGuide.ListBean listBean) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int result = listBean.getResult();
        if (result==1)
        {
            Notification notification = new NotificationCompat.Builder(this, "data")
                    .setContentTitle(listBean.getMatchType()==1?"竞技场":"战场")
                    .setContentText(listBean.getHero().getName())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.baiying)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.baiying))
                    .setAutoCancel(true)
                    .build();
            not_id = not_id+1;
            manager.notify(not_id, notification);
        }
        if (result==2)
        {
            Notification notification = new NotificationCompat.Builder(this, "data")
                    .setContentTitle(listBean.getMatchType()==1?"竞技场":"战场")
                    .setContentText(listBean.getHero().getName())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.baiying)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.baiying))
                    .setAutoCancel(true)
                    .build();
            //这里id如果一样的会重叠
            not_id = not_id+1;
            manager.notify(not_id, notification);
        }
        if (result==3)
        {
            Notification notification = new NotificationCompat.Builder(this, "data")
                    .setContentTitle(listBean.getMatchType()==1?"竞技场":"战场")
                    .setContentText(listBean.getHero().getName())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.baiying)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.baiying))
                    .setAutoCancel(true)
                    .build();
            //这里id如果一样的会重叠
            not_id = not_id+1;
            manager.notify(not_id, notification);
        }

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

}
