package com.example.evenalone.a300hero.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.event.JumpValueEvnet;
import com.example.evenalone.a300hero.event.ListInfoEvent;
import com.example.evenalone.a300hero.ui.GuaideInfoActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.OkhttpUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.SystemUtils;
import com.google.gson.Gson;

import net.wujingchao.android.view.SimpleTagImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    private void sendCustomNotification(final HeroGuide.ListBean listBean){
        final RemoteViews remoteViews =new RemoteViews(getPackageName(),R.layout.notfiy_layout);
        //使用图片加载
        String url = Contacts.IMG+listBean.getHero().getIconFile();
        x.http().get(new RequestParams(url), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Bitmap result_b = BitmapFactory.decodeFile(result.getAbsolutePath());
                remoteViews.setImageViewBitmap(R.id.img_notify_avator,result_b);
                //处理数据
                if (listBean.getResult()==1)
                {
                    remoteViews.setTextViewText(R.id.tv_notifiy_status,"WIN");
                }
                if (listBean.getResult()==2)
                {
                    remoteViews.setTextViewText(R.id.tv_notifiy_status,"LOSE");
                }
                if (listBean.getResult()==3)
                {
                    remoteViews.setTextViewText(R.id.tv_notifiy_status,"ESCAPE");
                }
                //请求战绩详情
                requestInfo(listBean,remoteViews,result_b);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("data","下载图片出现错误"+ex.getLocalizedMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.e("data","图片获取完成");
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }

    private void requestInfo(final HeroGuide.ListBean listBean, final RemoteViews remoteViews, final Bitmap drawable) {
        RequestParams requestParams = new RequestParams();
        requestParams.setUri(Contacts.MATCH_GAME+"?id="+listBean.getMatchID());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.e("获取的对局数据",result);
                    //这里序列化完成后记得存入数据库,或者不,少一点操作
                    GameInfo gameInfo = new Gson().fromJson(result, GameInfo.class);
                    //筛选自己的战绩结果
                    //获取自己的信息(包括杀敌死亡)
                    //输
                    int kda;
                    //杀敌数量
                    int kill = 0;
                    //经济
                    long money = 0;
                    //助攻
                    int asstent = 0;
                    //小兵击杀
                    int killunitcount = 0;
                    //死亡次数
                    int deadcount = 0;
                    //记录全局变量查看数据异常
                    if (gameInfo==null)
                    {
                        return;
                    }
                        //赢了
                        //获取赢的玩家列表
                        List<GameInfo.MatchBean.WinSideBean> winSideBeanList = gameInfo.getMatch().getWinSide();
                        for (GameInfo.MatchBean.WinSideBean winSideBean : winSideBeanList) {

                            kill = winSideBean.getKillCount();
                            money = winSideBean.getTotalMoney();
                            deadcount = winSideBean.getDeathCount();
                            asstent = winSideBean.getAssistCount();
                        }
                        /*
                        GameInfo.MatchBean.WinSideBean winSideBean = new GameUtils().winMvpget(winSideBeanList);
                        if (winSideBean!=null)
                        {
                            Log.e("对局时间为:"+listBean.getMatchDate(),"mvp是"+winSideBean.getRoleName());
                        }
                        */
                        //输了
                        List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = gameInfo.getMatch().getLoseSide();
                        for (GameInfo.MatchBean.LoseSideBean loseSideBean : loseSideBeanList) {
                            if (loseSideBean.getRoleName().equals(SpUtils.getNowUser())) {
                                kill = loseSideBean.getKillCount();
                                money = loseSideBean.getTotalMoney();
                                deadcount = loseSideBean.getDeathCount();
                                asstent = loseSideBean.getAssistCount();
                            }
                        }
                        /*
                        GameInfo.MatchBean.LoseSideBean loseSideBean = new GameUtils().loseMvpget(loseSideBeanList);
                        if (loseSideBean!=null)
                        {
                            Log.e("对局时间为:"+listBean.getMatchDate(),"mvp是"+loseSideBean.getRoleName());
                        }
                        */
                    String result_o = kill+"杀 "+deadcount+"死 "+asstent+"助 ";
                    remoteViews.setTextViewText(R.id.tv_notifiy_info,result_o);
                    if (listBean.getMatchType()==1)
                    {
                        remoteViews.setTextViewText(R.id.tv_notifiy_type,"竞技场");
                    }
                    else
                    {
                        remoteViews.setTextViewText(R.id.tv_notifiy_type,"战场");
                    }
                    remoteViews.setTextViewText(R.id.tv_notifiy_time,listBean.getMatchDate());
                    //获取颜色
                    //Palette用来更漂亮地展示配色
                    Palette.from(drawable)
                            .generate(new Palette.PaletteAsyncListener() {
                                @SuppressLint("NewApi")
                                @Override
                                public void onGenerated(@NonNull Palette palette) {
                                    List<Palette.Swatch> swatches = palette.getSwatches();
                                    Palette.Swatch swatch = swatches.get(0);
                                    int color = colorBurn(swatch.getRgb());
                                    remoteViews.setInt(R.id.layout_notify_bg, "setBackgroundColor", color);
                                    Intent intent = new Intent(getApplicationContext(),GuaideInfoActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("id",listBean.getMatchID());
                                    intent.putExtras(bundle);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                    remoteViews.setOnClickPendingIntent(R.id.layout_notify_bg,pendingIntent);
                                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    //到此为止 notify加工全部完毕
                                    Notification notification = new NotificationCompat.Builder(getBaseContext(), "data")
                                            .setCustomContentView(remoteViews)
                                            .setCustomBigContentView(remoteViews)
                                            .setWhen(System.currentTimeMillis())
                                            .setTicker("新通知")
                                            .setSmallIcon(R.drawable.baiying)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.baiying))
                                            .setAutoCancel(true)
                                            .build();
                                    not_id = not_id+1;
                                    manager.notify(not_id, notification);
                                }
                            });
                    }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*
     * 颜色加深处理
     * */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
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
                    sendCustomNotification(listBeans.get(0));
                }
            }
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
