package com.example.evenalone.a300hero.event;

import com.example.evenalone.a300hero.bean.YourRole;

public class BindEvent extends BaseEvent
{
    private YourRole yourRole;

    public BindEvent(YourRole role)
    {
        this.yourRole = role;
    }

    public void setYourRole(YourRole yourRole) {
        this.yourRole = yourRole;
    }

    @Override
    public String getErroMsg() {
        return super.getErroMsg();
    }

    public YourRole getYourRole() {
        return yourRole;
    }
}
