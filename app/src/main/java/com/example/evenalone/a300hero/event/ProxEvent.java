package com.example.evenalone.a300hero.event;

import com.example.evenalone.a300hero.bean.NetWorkProx;

import java.util.List;

public class ProxEvent
{
    private List<NetWorkProx> netWorkProxes;

    public ProxEvent(List<NetWorkProx> netWorkProxes) {
        this.netWorkProxes = netWorkProxes;
    }

    public void setNetWorkProxes(List<NetWorkProx> netWorkProxes) {
        this.netWorkProxes = netWorkProxes;
    }

    public List<NetWorkProx> getNetWorkProxes() {
        return netWorkProxes;
    }
}
