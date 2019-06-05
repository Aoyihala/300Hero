package com.example.evenalone.a300hero.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 对局详情
 */
@Entity
public class LocalGameInfo {
    @Id(autoincrement = true)
    private Long id;
    private Long mactherId;
    //存放json
    private String result;

    private String mygaide;

    public void setMygaide(String mygaide) {
        this.mygaide = mygaide;
    }

    public String getMygaide() {
        return mygaide;
    }

    @Generated(hash = 1599658482)
    public LocalGameInfo(Long id, Long mactherId, String result, String mygaide) {
        this.id = id;
        this.mactherId = mactherId;
        this.result = result;
        this.mygaide = mygaide;
    }

    @Generated(hash = 1523443636)
    public LocalGameInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMactherId() {
        return this.mactherId;
    }
    public void setMactherId(Long mactherId) {
        this.mactherId = mactherId;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }



}
