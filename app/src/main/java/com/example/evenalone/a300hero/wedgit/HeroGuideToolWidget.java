package com.example.evenalone.a300hero.wedgit;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.ToolsListAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.bean.LocalUserBeanDao;
import com.example.evenalone.a300hero.service.BindToolsService;
import com.example.evenalone.a300hero.ui.GuaideInfoActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.SystemUtils;
import com.google.gson.Gson;
import com.igexin.sdk.GActivity;

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
import java.util.Random;
import java.util.Set;

/**
 * 桌面小工具 类似于广播
 */
public class HeroGuideToolWidget extends AppWidgetProvider {
    public static Integer randomNumber;
    private ToolsListAdapter toolsListAdapter;
    private LocalGaideListInfoDao gaideListInfoDao;
    private LocalUserBeanDao userBeanDao;
    public final static String clickAction = "com.example.evenalone.a300hero.wedgit.CLICK_ACTION";
    private static final String ACTION_PACKAGE_DATA_CLEARED = "com.example.evenalone.a300hero.wedgit.intent.action.SETTINGS_PACKAGE_DATA_CLEARED";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e("data","小工具执行了广播刷新");
        //第二步接收了广播

        String action = intent.getAction();

        if (action.equals("refresh")) {
            // 刷新Widget
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context,HeroGuideToolWidget.class);

           /* MyRemoteViewsFactory.mList.add("音乐"+ i);*/

            // 这句话会调用RemoteViewSerivce中RemoteViewsFactory的onDataSetChanged()方法。
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn),
                    R.id.list_tool_listview);

        }else if (action.equals(clickAction)) {
                // 单击Wdiget中ListView的某一项会显示一个Toast提示。
                Toast.makeText(context, intent.getLongExtra("id",0)+" ",
                        Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putLong("id",intent.getLongExtra("id",0));
                 bundle.putString("nickname",SpUtils.getMainUser());
                Intent intent1 = new Intent(context,GuaideInfoActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtras(bundle);
                context.startActivity(intent1);

        }

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
       Log.e("data","试图改变大小");
    }

    // 更新所有的 widget
    private void updateAllAppWidgets(final Context context, final AppWidgetManager appWidgetManager, final int[] ints) {
        Log.d("小工具更新", "updateAllAppWidgets(): size=" + ints.length);
        // widget 的id

        userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        // 获取Widget的组件名
        final ComponentName thisWidget = new ComponentName(context,
                HeroGuideToolWidget.class);
        for (int i=0;i<ints.length;i++)
        {
            randomNumber = new Random().nextInt(1000);
            boolean isService = SystemUtils.isServiceWork(context,"com.example.evenalone.a300hero.service.MyNotifiService");
            // 获取 example_appwidget.xml 对应的RemoteViews
            final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.tools_layout);
            // 把这个Widget绑定到RemoteViewsService
            Intent intent = new Intent(context,BindToolsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ints[i]+randomNumber);
            intent.setData(Uri.fromParts("content", String.valueOf(ints[i]+randomNumber), null));
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            intent.putExtra("random",randomNumber );
            // 设置适配器
            remoteView.setRemoteAdapter(R.id.list_tool_listview, intent);
            // 点击列表触发事件
            Intent clickIntent = new Intent(context, HeroGuideToolWidget.class);
            // 设置Action，方便在onReceive中区别点击事件
            clickIntent.setAction(clickAction);
            if (isService)
            {
                remoteView.setTextViewText(R.id.tv_tool_updatetime,"服务已运行");
            }
            else
            {
                remoteView.setTextViewText(R.id.tv_tool_updatetime,"服务没有运行");
            }
            clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(
                    context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteView.setPendingIntentTemplate(R.id.list_tool_listview,
                    pendingIntentTemplate);

            //设置头像 昵称 还有自己的团分胜率详情
            String user = SpUtils.getMainUser();
            final LocalUserBean localUserBean = finduser(user);
            //下载头像
            x.http().get(new RequestParams(Contacts.ROLE_IMG+localUserBean.getIocnfile()), new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    if (result!=null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeFile(result.getPath());
                        bitmap = makeRoundCorner(bitmap);
                        remoteView.setImageViewBitmap(R.id.img_tool_avator
                                ,bitmap);
                        //设置其他内容
                        remoteView.setTextViewText(R.id.tv_tool_name,localUserBean.getNickname());
                        remoteView.setTextViewText(R.id.tv_tool_userdes,"团分"+localUserBean.getJumpvalue()+" 胜率"+localUserBean.getViotory());
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
                        remoteView.setInt(R.id.tool_foot_bg,"setBackgroundColor",SpUtils.getMainColor());

                        // 设置当显示的widget_list为空显示的View
                        //remoteView.setEmptyView(R.id.widget_list, R.layout.none_data);

                        remoteView.setInt(R.id.tools_user_bg, "setBackgroundColor", SpUtils.getMainColor());
                        //Palette用来更漂亮地展示配色
                       /* Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @SuppressLint("NewApi")
                                    @Override
                                    public void onGenerated(@NonNull Palette palette) {
                                        List<Palette.Swatch> swatches = palette.getSwatches();
                                        Palette.Swatch swatch = swatches.get(0);
                                        int color = colorBurn(swatch.getRgb());
                                        remoteView.setInt(R.id.tools_bg, "setBackgroundColor", SpUtils.getMainColor());
                                    }
                                });*/

                        //只有一个widget所以
                        appWidgetManager.updateAppWidget(thisWidget, remoteView);
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

        }
        
    }
    //获取圆形bitmap
    public static Bitmap makeRoundCorner(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height/2;
        if (width > height) {
            left = (width - height)/2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width)/2;
            right = width;
            bottom = top + width;
            roundPx = width/2;
        }


        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    //获取保存的user
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
