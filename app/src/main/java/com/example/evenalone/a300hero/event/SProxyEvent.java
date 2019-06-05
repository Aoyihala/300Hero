package com.example.evenalone.a300hero.event;

import com.example.evenalone.a300hero.bean.NetWorkProx;

import java.util.List;

public class SProxyEvent
{
    private List<NetWorkProx> netWorkProxes;

    public SProxyEvent(List<NetWorkProx> netWorkProxes) {
        this.netWorkProxes = netWorkProxes;
    }

    public void setNetWorkProxes(List<NetWorkProx> netWorkProxes) {
        this.netWorkProxes = netWorkProxes;
    }

    public List<NetWorkProx> getNetWorkProxes() {
        return netWorkProxes;
    }
}
