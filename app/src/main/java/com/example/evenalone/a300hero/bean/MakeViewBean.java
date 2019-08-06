package com.example.evenalone.a300hero.bean;

/**
 * 展示在统计图上的视图
 * Created by admin on 2019/8/5.
 */

public class MakeViewBean {

    private long match_id;

    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }

    public long getMatch_id() {
        return match_id;
    }

    private String time;

    private boolean win;

    private int type;
    //默认0 无异常
    private int my_type;

    private String hero;

    private String hero_img;

    private int power;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public int getMy_type() {
        return my_type;
    }

    public void setHero_img(String hero_img) {
        this.hero_img = hero_img;
    }

    public int getPower() {
        return power;
    }

    public void setMy_type(int my_type) {
        this.my_type = my_type;
    }

    public int getType() {
        return type;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getHero() {
        return hero;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHero_img() {
        return hero_img;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }
}
