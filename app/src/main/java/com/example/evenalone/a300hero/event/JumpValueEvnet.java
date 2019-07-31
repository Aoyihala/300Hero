package com.example.evenalone.a300hero.event;

public class JumpValueEvnet
{
    String nickname;
    String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public JumpValueEvnet(String nickname,String value) {
        this.nickname = nickname;
        this.value = value;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
