package com.example.evenalone.a300hero.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 本地召唤师记录
 */
@Entity
public class LocalUserBean
{

    @Id(autoincrement = true)
    private Long id;

    private String nickname;

    private String password;
    //需要结合url请求网络使用
    //保留字段
    private String role_iocnfile;
    //英雄头像
    private String img_iconfile;

    private String jumpvalue;

    private String viotory;

    private String result;

    @Generated(hash = 1796874414)
    public LocalUserBean(Long id, String nickname, String password,
            String role_iocnfile, String img_iconfile, String jumpvalue,
            String viotory, String result) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.role_iocnfile = role_iocnfile;
        this.img_iconfile = img_iconfile;
        this.jumpvalue = jumpvalue;
        this.viotory = viotory;
        this.result = result;
    }

    @Generated(hash = 584561094)
    public LocalUserBean() {
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }



    public void setViotory(String viotory) {
        this.viotory = viotory;
    }

    public String getViotory() {
        return viotory;
    }

    public void setJumpvalue(String jumpvalue) {
        this.jumpvalue = jumpvalue;
    }


    public String getJumpvalue() {
        return jumpvalue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setImg_iconfile(String img_iconfile) {
        this.img_iconfile = img_iconfile;
    }

    public void setRole_iocnfile(String role_iocnfile) {
        this.role_iocnfile = role_iocnfile;
    }

    public String getImg_iconfile() {
        return img_iconfile;
    }

    public String getRole_iocnfile() {
        return role_iocnfile;
    }

}
