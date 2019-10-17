package com.example.evenalone.a300hero.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.evenalone.a300hero.bean.UpdateBean;
import com.example.evenalone.a300hero.event.UpdateVersionEvent;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;


public class UpdateService extends Service {
    //更新服务
    private Timer timer;
    //半小时检测一次更新
    private int time = 1000*60*30;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {

        startCheckUpdate();
        super.onCreate();
    }

    private void startCheckUpdate() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkUpdate();

            }
        },1000,time);

    }
    public String fileRead(File file) {
        FileReader reader = null;//定义一个fileReader对象，用来初始化BufferedReader
        try {
            reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
            StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            }
            bReader.close();
            String str = sb.toString();
            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
       return null;

    }
    private void checkUpdate()
    {
        //尝试直接访问
        x.http().get(new RequestParams("http://cloud.thacg.cn/index.php/s/cpZc8tmaWPLZxsr/download"), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                if (result!=null)
                {
                   String json =  fileRead(result);
                   //json =String.format(json,Charset.forName("utf-8"));
                   Log.e("结果",json);

                   //序列化
                    UpdateBean updateBean = new Gson().fromJson(json,UpdateBean.class);
                    //判断版本号
                    String nowversion = UiUtlis.getVersion();
                    if (!updateBean.getVersion().equals(nowversion))
                    {
                        //如果不是相同版本
                        EventBus.getDefault().postSticky(new UpdateVersionEvent(updateBean));

                    }
                    else
                    {
                        Log.e("状态","当前已是最新版本");
                    }

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
                Log.e("更新内容下载结束","结束");
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    class MyBinder extends Binder
    {
        public UpdateService getService()
        {
            return UpdateService.this;
        }

        public void toMessage(String json)
        {

        }
    }
}
