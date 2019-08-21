package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.example.evenalone.a300hero.event.ListInfoEvent;
import com.google.gson.Gson;

import org.greenrobot.greendao.AbstractDao;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ToolsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private LocalGaideListInfoDao gaideListInfoDao;
    private List<HeroGuide.ListBean> localGaideListInfos = new ArrayList<>();

    public ToolsFactory(Context applicationContext, Intent intent) {
        this.context = applicationContext;
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
    }

    @Override
    public void onCreate() {
        //创建时
        localGaideListInfos = getguadies();

    }
    /*
     * 当调用notifyAppWidgetViewDataChanged方法时，触发这个方法
     * 例如：MyRemoteViewsFactory.notifyAppWidgetViewDataChanged();
     */
    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        localGaideListInfos.clear();
    }

    @Override
    public int getCount() {
        return localGaideListInfos == null ? 0 : localGaideListInfos.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        // 创建在当前索引位置要显示的View
        final HeroGuide.ListBean listBean = localGaideListInfos.get(position);
        final RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.tools_game_item);
        rv.setTextViewText(R.id.tv_tool_type,listBean.getMatchType()==1?"竞技场":"战场");
        rv.setTextViewText(R.id.tv_tool_time,listBean.getMatchDate());
        x.http().get(new RequestParams(Contacts.IMG + listBean.getHero().getIconFile()), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                if (result != null) {
                    /*//头像 id资源
                    SimpleTagImageView simpleTagImageView = convertView.findViewById(R.id.img_tool_hero);
                    //描述
                    TextView tv_win_lose = convertView.findViewById(R.id.tv_tool_flag);
                    //类型
                    TextView tv_type = convertView.findViewById(R.id.tv_tool_type);
                    TextView tv_time = convertView.findViewWithTag(R.id.tv_tool_time);
                    TextView tv_user_des = convertView.findViewById(R.id.tv_tool_userdes);*/

                    Bitmap bitmap = BitmapFactory.decodeFile(result.getPath());
                    bitmap = makeRoundCorner(bitmap);
                    rv.setImageViewBitmap(R.id.img_tool_hero,bitmap);
                    rv.setTextViewText(R.id.tv_tool_type,listBean.getMatchType()==1?"竞技场":"战场");
                    rv.setTextViewText(R.id.tv_tool_time,listBean.getMatchDate());
                    rv.setTextViewText(R.id.tv_tool_guaide,"开发中");
                    // 填充Intent，填充在AppWdigetProvider中创建的PendingIntent
                    Intent intent = new Intent();
                    // 传入点击行的数据
                    intent.putExtra("id",listBean.getMatchID());
                    rv.setOnClickFillInIntent(R.id.tool_game_item_bg, intent);
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
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private List<HeroGuide.ListBean> getguadies() {
        List<LocalGaideListInfo> localGaideListInfos = gaideListInfoDao.loadAll();
        //排序
        ListSort(localGaideListInfos);
        if (localGaideListInfos.size() > 10) {
            localGaideListInfos = localGaideListInfos.subList(0, 10);
        }
        List<HeroGuide.ListBean> hero_guaidelist = new ArrayList<>();
        for (LocalGaideListInfo info : localGaideListInfos) {
            hero_guaidelist.add(new Gson().fromJson(info.getResult(), HeroGuide.ListBean.class));
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


    //获取圆形bitmap
    public static Bitmap makeRoundCorner(Bitmap bitmap) {
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
}
