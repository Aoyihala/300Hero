package com.example.evenalone.a300hero.event;

public class BaseEvent
{
    private String erroMsg;

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    public String getErroMsg() {
        return erroMsg;
    }
}
