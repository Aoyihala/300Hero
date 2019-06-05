package com.example.evenalone.a300hero.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.view.View;


import com.example.evenalone.a300hero.app.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 *
 * Created by even alone on 2018/6/2.
 */

public class UiUtlis {
    private UiUtlis()
    {

    }

    private static Context getContext()
    {
        return MyApplication.getContext();
    }



    public static List<String> getViewPagerTitle(@IdRes int id)
    {
        @SuppressLint("ResourceType") String[] rs = getContext().getResources().getStringArray(id);
        List<String> list = new ArrayList<>();
        for (String s :rs)
        {
            list.add(s);
        }
        return list;
    }


    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            String labelRes = packageInfo.packageName;
            return labelRes ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地版本号
     * @return 版本号
     */
    public static String getVersion()
    {
        String version ="";

        PackageManager manager = getContext().getPackageManager();
        try
        {
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(),0);
            version = info.versionName;
            return version;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 取得颜色
     * @param colorid 颜色id
     * @return
     */
    public static int getColor(int colorid)
    {
        return getContext().getResources().getColor(colorid);
    }

    /**
     * 获取view
     * @param viewid 视图id
     * @return
     */
    public static View getView(int viewid)
    {
        return View.inflate(getContext(),viewid,null);
    }

    /**
     * 根据 url 获取 host name
     * http://www.gcssloop.com/ => www.gcssloop.com
     */
    public static String getHost(String url) {
        if (url == null || url.trim().equals("")) {
            return "";
        }
        String host = "";
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            host = matcher.group();
        }
        return host;
    }

    /**
     * 获取随机颜色
     * @return
     **
    public static int RandomColor()
    {

        int[] customizedColors = getContext().getResources().getIntArray(R.array.customizedColors);
        int customizedColor = customizedColors[new Random().nextInt(customizedColors.length)];
        return customizedColor;
    }
    */

}
