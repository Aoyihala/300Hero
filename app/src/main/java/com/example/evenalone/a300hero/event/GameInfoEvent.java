package com.example.evenalone.a300hero.event;

import com.example.evenalone.a300hero.bean.GameInfo;

public class GameInfoEvent extends BaseEvent
{
    private GameInfo gameInfo;

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public GameInfoEvent(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }
}
