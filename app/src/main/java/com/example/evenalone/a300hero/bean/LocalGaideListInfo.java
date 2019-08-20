package com.example.evenalone.a300hero.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *用于本地做数据对比
 * 保存一个info
 * 战绩列表
 * Created by admin on 2019/6/14.
 */
@Entity
public class LocalGaideListInfo {
    @Id(autoincrement = true)
    private Long id;

    private String time;

    private String result;

    private String nickname;

    private long MatchId;


    public void setMatchId(long matchId) {
        MatchId = matchId;
    }

    public long getMatchId() {
        return MatchId;
    }

    @Generated(hash = 1744191600)
    public LocalGaideListInfo(Long id, String time, String result, String nickname,
            long MatchId) {
        this.id = id;
        this.time = time;
        this.result = result;
        this.nickname = nickname;
        this.MatchId = MatchId;
    }

    @Generated(hash = 737756361)
    public LocalGaideListInfo() {
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
