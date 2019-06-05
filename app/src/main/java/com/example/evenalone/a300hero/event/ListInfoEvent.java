package com.example.evenalone.a300hero.event;

import com.example.evenalone.a300hero.bean.HeroGuide;

public class ListInfoEvent extends BaseEvent
{
    private HeroGuide guide;

    public ListInfoEvent(HeroGuide guide)
    {
        this.guide = guide;
    }

    public void setGuide(HeroGuide guide) {
        this.guide = guide;
    }

    public HeroGuide getGuide() {
        return guide;
    }
}
