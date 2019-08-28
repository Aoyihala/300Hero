package com.example.evenalone.a300hero.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.example.evenalone.a300hero.utils.ToolsFactory;

/**
 * 桌面视图服务
 * */
public class BindToolsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.e("列表更新服务","启用");
        return new ToolsFactory(getApplicationContext(), intent);
    }
}
