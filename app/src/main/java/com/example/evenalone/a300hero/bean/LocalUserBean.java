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
    private String iocnfile;

    private String jumpvalue;

    private String viotory;

    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Generated(hash = 956457997)
    public LocalUserBean(Long id, String nickname, String password, String iocnfile,
            String jumpvalue, String viotory, String result) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.iocnfile = iocnfile;
        this.jumpvalue = jumpvalue;
        this.viotory = viotory;
        this.result = result;
    }

    @Generated(hash = 584561094)
    public LocalUserBean() {
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

    public String getIocnfile() {
        return this.iocnfile;
    }

    public void setIocnfile(String iocnfile) {
        this.iocnfile = iocnfile;
    }
}
