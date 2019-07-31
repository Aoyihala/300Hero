package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.LocalUserBean;

public class SpUtils
{
    /**
     * 切换当前用户
     * @param name
     */
    public static void selectUser(String name)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("nowuser",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname",name);
        editor.apply();
    }
    /**
     * 切换当前用户
     * @param name
     */
    public static void setMianUser(String name)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname_main",name);
        editor.apply();
    }
    //针对于观察者模式
    public static String getMainUser()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        String nickname = preferences.getString("nickname_main",null);
        LocalUserBean userBean = new LocalUserBean();
        userBean.setNickname(nickname);
        return userBean.getNickname();
    }

    /**
     * 获取当前选中的用户
     * @return
     */
    public static String getNowUser()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("nowuser",Context.MODE_PRIVATE);
        String nickname = preferences.getString("nickname",null);
        LocalUserBean userBean = new LocalUserBean();
        userBean.setNickname(nickname);
        return userBean.getNickname();
    }

    /**
     * 设置代理按钮
     * @param switchbtn
     */
    public static void setPorxy(boolean switchbtn)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("proxy",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("proxy",switchbtn);
        editor.apply();
    }

    public static boolean isPorxy()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("proxy",Context.MODE_PRIVATE);
        boolean porxy = preferences.getBoolean("proxy",false);

        return porxy;
    }

    public static void saveMainColor(int color) {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("color",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("main_color",color);
        editor.apply();
    }

    public static int getMainColor()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("color",Context.MODE_PRIVATE);
        int color = preferences.getInt("main_color",UiUtlis.getColor(R.color.Yellow));
        return color;
    }
}
