package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.LocalUserBean;

public class SpUtils
{
 /*   *//**
     * 切换当前用户
     * @param name
     *//*
    public static void selectUser(String name)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("nowuser",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname",name);
        editor.apply();
    }*/
    /**
     * 切换当前用户
     * @param name
     */
    public static void backUser(String name)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("nowuser",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("back_nickname",name);
        editor.apply();
    }
    public static String getBackUser()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("nowuser",Context.MODE_PRIVATE);
        String nickname = preferences.getString("back_nickname",null);

        return nickname;
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

   /* *//**
     * 获取当前选中的用户
     * @return
     *//*
    public static String getNowUser()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("nowuser",Context.MODE_PRIVATE);
        String nickname = preferences.getString("nickname",null);
        LocalUserBean userBean = new LocalUserBean();
        userBean.setNickname(nickname);
        return userBean.getNickname();
    }
*/
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

    public static void setMoreMode(boolean moreMode) {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("mode",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("more_mode",moreMode);
        editor.apply();
    }

    public static boolean isMoreMode()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("mode",Context.MODE_PRIVATE);
        boolean color = preferences.getBoolean("more_mode",true);
        return color;
    }

    public static void setX(boolean x) {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("xy",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("x",x);
        editor.apply();
    }
    public static boolean getX()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("xy",Context.MODE_PRIVATE);
        boolean color = preferences.getBoolean("x",true);
        return color;
    }
    public static void sety(boolean y) {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("xy",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("y",y);
        editor.apply();
    }
    public static boolean getY()
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("xy",Context.MODE_PRIVATE);
        boolean color = preferences.getBoolean("y",true);
        return color;
    }

    public static boolean isClock() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("clock",Context.MODE_PRIVATE);
        boolean color = preferences.getBoolean("auto",false);
        return color;
    }
    public static void setClock(boolean clock)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("clock",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("auto",clock);
        editor.apply();
    }

    public static void setTimeSpeed(long timeSpeed)
    {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("time_speed",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("speed",timeSpeed);
        editor.apply();
    }

    public static long getLasttime() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("time_speed",Context.MODE_PRIVATE);
        return preferences.getLong("speed",0);

    }
}
