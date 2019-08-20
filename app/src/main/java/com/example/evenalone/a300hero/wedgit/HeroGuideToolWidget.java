package com.example.evenalone.a300hero.wedgit;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.ToolsListAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.bean.LocalUserBeanDao;
import com.example.evenalone.a300hero.ui.GuaideInfoActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 桌面小工具 类似于广播
 */
public class HeroGuideToolWidget extends AppWidgetProvider {
    private ToolsListAdapter toolsListAdapter;
    private LocalGaideListInfoDao gaideListInfoDao;
    private LocalUserBeanDao userBeanDao;
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e("data","小工具执行了广播刷新");
        //第二步接收了广播
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //添加入屏幕会执行该方法
        //从屏幕上删除也会执行该方法
        Log.e("data","小工具执行了刷新");
        updateAllAppWidgets(context,appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.e("data","小工具执行了添加");

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.e("data","小工具执行了删除");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.e("data","小工具执行了去除");
        //从屏幕上去除第一步执行了该方法
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    // 更新所有的 widget
    private void updateAllAppWidgets(final Context context, AppWidgetManager appWidgetManager,int[] ints) {
        Log.d("小工具更新", "updateAllAppWidgets(): size=" + ints.length);
        // widget 的id
        int appID;
        userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        for (int i=0;i<ints.length;i++)
        {
            appID = ints[i];
            // 获取 example_appwidget.xml 对应的RemoteViews
            final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.tools_layout);
            //设置头像 昵称 还有自己的团分胜率详情
            String user = SpUtils.getMainUser()==null||SpUtils.getMainUser().equals("")?SpUtils.getNowUser():SpUtils.getMainUser();
            final LocalUserBean localUserBean = finduser(user);
            //下载头像
            x.http().get(new RequestParams(getImgUrl(localUserBean)), new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    if (result!=null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeFile(result.getPath());
                        remoteView.setImageViewBitmap(R.id.img_tool_hero,bitmap);
                        //设置其他内容
                        remoteView.setTextViewText(R.id.tv_tool_name,localUserBean.getNickname());
                        remoteView.setTextViewText(R.id.tv_tool_userdes,"团分"+localUserBean.getJumpvalue()+" 胜率"+localUserBean.getViotory()+"%");
                        //判断团分
                        int power = Integer.parseInt(localUserBean.getJumpvalue());
                        if (power > 0 && power < 1000) {
                            remoteView.setImageViewResource(R.id.img_tool_duanwei,R.drawable.tong);
                        }
                        if (power >= 1000 && power < 2000) {
                            remoteView.setImageViewResource(R.id.img_tool_duanwei,R.drawable.baiying);
                        }
                        if (power >= 2000 && power < 3000) {
                            remoteView.setImageViewResource(R.id.img_tool_duanwei,R.drawable.gold);
                        }
                        if (power >= 3000) {
                            remoteView.setImageViewResource(R.id.img_tool_duanwei,R.drawable.daemo);
                        }

                        //Palette用来更漂亮地展示配色
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @SuppressLint("NewApi")
                                    @Override
                                    public void onGenerated(@NonNull Palette palette) {
                                        List<Palette.Swatch> swatches = palette.getSwatches();
                                        Palette.Swatch swatch = swatches.get(0);
                                        int color = colorBurn(swatch.getRgb());
                                        remoteView.setInt(R.id.tools_user_bg, "setBackgroundColor", color);

                                    }
                                });
                        //设置适配器
                        toolsListAdapter = new ToolsListAdapter(context,R.layout.tools_game_item);
                        //获取数据库里的list,获取最新10条
                        toolsListAdapter.setListBeans(getguadies());
                        //listview配置

                    }


                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("data","小工具出现错误:"+ex.getLocalizedMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    Log.e("data","小工具头像下载完成");
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
            // 更新 widget
            appWidgetManager.updateAppWidget(appID, remoteView);
        }
        
    }
    private LocalUserBean finduser(String name)
    {
        return userBeanDao.queryBuilder().where(LocalUserBeanDao.Properties.Nickname.eq(name)).unique();
    }
    private List<HeroGuide.ListBean> getguadies()
    {
        List<LocalGaideListInfo> localGaideListInfos =  gaideListInfoDao.loadAll();
        //排序
        ListSort(localGaideListInfos);
        if (localGaideListInfos.size()>10)
        {
            localGaideListInfos = localGaideListInfos.subList(0,10);
        }
        List<HeroGuide.ListBean> hero_guaidelist = new ArrayList<>();
        for (LocalGaideListInfo info:localGaideListInfos)
        {
            hero_guaidelist.add(new Gson().fromJson(info.getResult(),HeroGuide.ListBean.class));
        }
        return hero_guaidelist;
    }

    //排序
    private static void ListSort(List<LocalGaideListInfo> list) {
        Collections.sort(list, new Comparator<LocalGaideListInfo>() {
            @Override
            public int compare(LocalGaideListInfo o1, LocalGaideListInfo o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public String getImgUrl(LocalUserBean localUserBean)
    {
        String role = localUserBean.getRole_iocnfile();
        String img = localUserBean.getImg_iconfile();
        if (TextUtils.isEmpty(img))
        {
            return Contacts.ROLE_IMG +role;
        }
        return Contacts.IMG+img;
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
}
