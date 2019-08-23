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
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.HeroGuide;

import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private LocalGaideListInfoDao gaideListInfoDao;
    private List<HeroGuide.ListBean> localGaideListInfos = new ArrayList<>();
    private List<LocalGaideListInfo> localGaideListInfoList = new ArrayList<>();
    private RemoteViews rv;
    private Map<Integer, Boolean> flags = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    private Intent intent;
    private Handler handler = new Handler(Looper.getMainLooper());
    private LocalGameInfoDao gameInfoDao;
    private Bitmap mBitmap;

    public ToolsFactory(Context applicationContext, Intent intent) {
        this.context = applicationContext;
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();
    }


    private LocalGameInfo findoneGame(long matchid)
    {
        return gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.MactherId.eq(matchid)).unique();
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
    public RemoteViews getViewAt(final int position) {
        // 创建在当前索引位置要显示的View
        final HeroGuide.ListBean listBean = localGaideListInfos.get(position);
        final RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.tools_game_item);
        rv.setTextViewText(R.id.tv_tool_type,listBean.getMatchType()==1?"竞技场":"战场");
        rv.setTextViewText(R.id.tv_tool_time,listBean.getMatchDate());
        LocalGameInfo gameInfo = findoneGame(listBean.getMatchID());
        LocalGaideListInfo gaideListInfo = localGaideListInfoList.get(position);
        String wei = gaideListInfo.getMyWeizi();
        if (!TextUtils.isEmpty(wei))
        {
            rv.setTextViewText(R.id.tv_tool_weizi,wei);
            if (wei.equals("MVP")||wei.equals("躺输")||wei.equals("神队友"))
            {
                rv.setInt(R.id.tv_tool_weizi,"setBackground",R.drawable.shap_mvp);
            }
            if (wei.equals("划水"))
            {
                rv.setInt(R.id.tv_tool_weizi,"setBackground",R.drawable.shap_huashui);
            }
            if (wei.equals("坑"))
            {
                rv.setInt(R.id.tv_tool_weizi,"setBackground",R.drawable.shap_keng);
            }
        }

        if (gameInfo!=null)
        {
            rv.setTextViewText(R.id.tv_tool_guaide,gameInfo.getMygaide());


        }
        else
        {
            rv.setTextViewText(R.id.tv_tool_guaide,"点击查看页面获取");

        }

        if (listBean.getResult()==1)
        {
            rv.setTextViewText(R.id.tv_tool_flag,"WIN");
            rv.setTextColor(R.id.tv_tool_flag,UiUtlis.getColor(R.color.colorPrimary));
        }
        if (listBean.getResult()==2)
        {
            rv.setTextViewText(R.id.tv_tool_flag,"LOSE");
            rv.setTextColor(R.id.tv_tool_flag,UiUtlis.getColor(R.color.Red));
        }
        if (listBean.getResult()==3)
        {
            rv.setTextViewText(R.id.tv_tool_flag,"ESCAPE");
            rv.setTextColor(R.id.tv_tool_flag,UiUtlis.getColor(R.color.blue));
        }
        flags.put(position, false);
        handler.post(new Runnable() {
            @Override
            public void run() {
                x.http().get(new RequestParams(Contacts.IMG + listBean.getHero().getIconFile()), new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        if (result!=null)
                        {
                            mBitmap = BitmapFactory.decodeFile(result.getAbsolutePath());
                            flags.put(position, true);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        flags.put(position, true);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        flags.put(position, true);
                    }

                    @Override
                    public void onFinished() {
                        flags.put(position, true);
                    }

                    @Override
                    public void onWaiting() {
                        flags.put(position, true);
                    }

                    @Override
                    public void onStarted() {
                        flags.put(position, true);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        flags.put(position, true);
                    }
                });
            }
        });
//在此一直等待获取到网络图片
        while (!flags.get(position)) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flags.put(position, false);
        if (mBitmap != null) {
            rv.setImageViewBitmap(R.id.img_tool_hero,makeRoundCorner(mBitmap));
        } else {
            rv.setImageViewResource(R.id.img_tool_hero, R.drawable.fish);
        }
        mBitmap = null;
        // 填充Intent，填充在AppWdigetProvider中创建的PendingIntent
        Intent intent = new Intent();
        // 传入点击行的数据
        intent.putExtra("id",listBean.getMatchID());
        rv.setOnClickFillInIntent(R.id.tool_game_item_bg, intent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 10;
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
     localGaideListInfoList = gaideListInfoDao.loadAll();
        //排序
        ListSort(localGaideListInfoList);
        if (localGaideListInfoList.size() > 10) {
            localGaideListInfoList= localGaideListInfoList.subList(0, 10);
        }
        List<HeroGuide.ListBean> hero_guaidelist = new ArrayList<>();
        for (LocalGaideListInfo info : localGaideListInfoList) {
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
