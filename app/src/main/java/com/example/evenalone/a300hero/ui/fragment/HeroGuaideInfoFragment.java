package com.example.evenalone.a300hero.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseFragment;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.google.gson.Gson;

import butterknife.BindView;

public class HeroGuaideInfoFragment extends BaseFragment {
    @BindView(R.id.tv_guaide_avdpower)
    TextView tvGuaideAvdpower;
    @BindView(R.id.recycer_winsaide)
    RecyclerView recycerWinsaide;
    @BindView(R.id.recycer_loseside)
    RecyclerView recycerLoseside;
    private Bundle bundle;
    private long id;
    private LocalGameInfoDao gameInfoDao;
    private LocalGameInfo gameInfo;
    private GameInfo gameInfo_real;
    @Override
    protected boolean setEventOpen() {
        return false;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initdata() {
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();
        bundle = getArguments();
        if (bundle!=null)
        {
            id = bundle.getLong("id");
            //查询
            gameInfo = searchGameinfoById(id);
            if (gameInfo!=null)
            {
                //解析数据
                gameInfo_real = new Gson().fromJson(gameInfo.getResult(),GameInfo.class);
                //直接配置adapter
                configadapter(gameInfo_real);
            }
            else
            {
                //请求网络

            }

        }
    }

    /**
     * 根据数据配置展示视图
     * @param gameInfo_real
     */
    private void configadapter(GameInfo gameInfo_real) {

    }

    private LocalGameInfo searchGameinfoById(long id)
    {
        LocalGameInfo gameInfo = gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.MactherId.eq(id)).unique();
        return gameInfo;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guaideinfo;
    }
}
