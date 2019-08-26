package com.example.evenalone.a300hero.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.PowerValueMianAdpater;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseFragment;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.example.evenalone.a300hero.event.GameInfoEvent;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.GameUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;

public class PowerValueFragment extends BaseFragment {
    @BindView(R.id.recycler_powervalue)
    RecyclerView recyclerPowervalue;
    Unbinder unbinder;
    private Bundle bundle;
    private long id;
    private LocalGameInfoDao gameInfoDao;
    private LocalGameInfo gameInfo;
    private GameInfo gameInfo_real;
    private GameUtils gameUtils;
    private PowerValueMianAdpater mianAdpater;
    private String nickname;
    @Override
    protected boolean setEventOpen() {
        return true;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initdata() {
        gameUtils = new GameUtils();
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();
        mianAdpater = new PowerValueMianAdpater();
        recyclerPowervalue.setLayoutManager(new LinearLayoutManager(compatActivity));
        recyclerPowervalue.setAdapter(mianAdpater);
        bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("id");
            nickname = bundle.getString("nickname");
            //查询
            gameInfo = searchGameinfoById(id);
            if (gameInfo != null) {
                //解析数据
                gameInfo_real = new Gson().fromJson(gameInfo.getResult(), GameInfo.class);
                //直接配置adapter
                configadapter(gameInfo_real);
            } else {
                //请求网络
                Request request = new Request.Builder()
                        .url(Contacts.MATCH_GAME + "?id=" + id)
                        .build();
                MyApplication.getOkhttpUtils().sendRequest(request, GameInfo.class);
            }
        }


    }

    private void configadapter(GameInfo gameInfo_real) {
        mianAdpater.setData(gameInfo_real);
    }

    private LocalGameInfo searchGameinfoById(long id) {
        LocalGameInfo gameInfo = gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.MactherId.eq(id)).unique();
        return gameInfo;
    }

    private LocalGameInfo searchGameinfoByResult(String re) {
        LocalGameInfo gameInfo = gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.Result.eq(re)).unique();
        return gameInfo;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_powervalue;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGameinfo(GameInfoEvent eva) {
        if (eva.isSuccess()) {
            //保存本地数据库
            if (eva.getGameInfo() == null) {
                return;
            }
            boolean ishave = false;
            LocalGameInfo localGameInfo = searchGameinfoByResult(eva.getGameInfo().getLocalruselt());
            if (localGameInfo != null) {
                ishave = true;
                localGameInfo.setId(localGameInfo.getId());
                localGameInfo.setResult(eva.getGameInfo().getLocalruselt());
                localGameInfo.setMactherId(id);
            } else {
                ishave = false;
                localGameInfo = new LocalGameInfo();
                localGameInfo.setResult(eva.getGameInfo().getLocalruselt());
                localGameInfo.setMactherId(id);
                String res = gameUtils.getKillUserDataWin(eva.getGameInfo().getMatch().getWinSide(), nickname);
                String res_lose = gameUtils.getKillUserDataLose(eva.getGameInfo().getMatch().getLoseSide(), nickname);
                if (TextUtils.isEmpty(res)) {
                    localGameInfo.setMygaide(res_lose);
                } else {
                    localGameInfo.setMygaide(res);
                }
            }

            if (ishave) {
                gameInfoDao.update(localGameInfo);
            } else {
                gameInfoDao.save(localGameInfo);
            }
            configadapter(eva.getGameInfo());
        }
    }

    public void updateAnimate() {
        if (mianAdpater!=null)
        {
            mianAdpater.updateAnimate();
        }
    }
}
