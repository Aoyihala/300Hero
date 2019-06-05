package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
}
