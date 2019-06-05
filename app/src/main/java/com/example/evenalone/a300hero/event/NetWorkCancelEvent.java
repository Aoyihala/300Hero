package com.example.evenalone.a300hero.event;

public class NetWorkCancelEvent {

    private String classname;

    public NetWorkCancelEvent(String classname) {
        this.classname = classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassname() {
        return classname;
    }
}
