package com.example.evenalone.a300hero.ui.body;

import com.example.evenalone.a300hero.base.BaseActivity;

public class BodyData
{
    private long myroleid;
    private long targetroleid;
    private String nickname;
    private String reason;
    private String bodydata;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setBodydata(String bodydata) {
        this.bodydata = bodydata;
    }

    public void setMyroleid(long myroleid) {
        this.myroleid = myroleid;
    }

    public void setTargetroleid(long targetroleid) {
        this.targetroleid = targetroleid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getReason() {
        return reason;
    }

    public long getMyroleid() {
        return myroleid;
    }

    public long getTargetroleid() {
        return targetroleid;
    }

    public String getBodydata() {
        return bodydata;
    }

}
