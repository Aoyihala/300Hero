package com.example.evenalone.a300hero.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.GaideInfoAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseFragment;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.example.evenalone.a300hero.event.GameInfoEvent;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.GameUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

public class HeroGuaideInfoFragment extends BaseFragment {
    @BindView(R.id.tv_guaide_avdpower)
    TextView tvGuaideAvdpower;
    @BindView(R.id.recycer_winsaide)
    RecyclerView recycerWinsaide;
    @BindView(R.id.recycer_loseside)
    RecyclerView recycerLoseside;
    @BindView(R.id.view_team_color_win)
    View viewTeamColorWin;
    @BindView(R.id.view_team_color_lose)
    View viewTeamColorLose;
    @BindView(R.id.img_power)
    ImageView imgPower;
    @BindView(R.id.tv_power_des)
    TextView tvPowerDes;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private Bundle bundle;
    private long id;
    private LocalGameInfoDao gameInfoDao;
    private LocalGameInfo gameInfo;
    private GameInfo gameInfo_real;
    private GaideInfoAdapter winadapter;
    private GaideInfoAdapter loseadapter;
    private GameUtils gameUtils;

    //0蓝 1红
    @Override
    protected boolean setEventOpen() {
        return true;
    }

    @Override
    protected void initview() {
        recycerLoseside.setNestedScrollingEnabled(false);
        recycerWinsaide.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initdata() {
        gameUtils = new GameUtils();
        winadapter = new GaideInfoAdapter();
        winadapter.setIswin(true);
        loseadapter = new GaideInfoAdapter();
        loseadapter.setIswin(false);
        recycerWinsaide.setAdapter(winadapter);
        recycerLoseside.setAdapter(loseadapter);
        recycerLoseside.setLayoutManager(new LinearLayoutManager(compatActivity));
        recycerWinsaide.setLayoutManager(new LinearLayoutManager(compatActivity));
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();
        bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("id");
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

    /**
     * 根据数据配置展示视图
     *
     * @param gameInfo_real
     */
    private void configadapter(GameInfo gameInfo_real) {
        List<GameInfo.MatchBean.WinSideBean> winSideBeanList = gameInfo_real.getMatch().getWinSide();
        List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = gameInfo_real.getMatch().getLoseSide();
        winadapter.setWinSideBeanList(winSideBeanList);
        loseadapter.setLoseSideBeanList(loseSideBeanList);
        //设置耗时，总金额等等
        winadapter.notifyDataSetChanged();
        loseadapter.notifyDataSetChanged();
        //获取双方队伍属性
        int win_team = gameInfo_real.getMatch().getWinSide().get(0).getTeamResult();

        //计算平均团分
        int win_power = 0;
        int lose_power = 0;
        for (GameInfo.MatchBean.WinSideBean winSideBean : winSideBeanList) {
            win_power = win_power + winSideBean.getELO();
        }
        for (GameInfo.MatchBean.LoseSideBean loseSideBean : loseSideBeanList) {
            lose_power = lose_power + loseSideBean.getELO();
        }
        //算平均团分和设置队伍标识
        if (win_team == 0) {

            //蓝队
            viewTeamColorWin.setBackgroundColor(UiUtlis.getColor(R.color.blue));
            viewTeamColorLose.setBackgroundColor(UiUtlis.getColor(R.color.Red));
            if (winSideBeanList.size() > 0 && loseSideBeanList.size() > 0) {
                tvGuaideAvdpower.setText("蓝方:" + (win_power / winSideBeanList.size()) + " | " + "红方:" + (lose_power / winSideBeanList.size()));
            }


        } else {
            //红队
            viewTeamColorLose.setBackgroundColor(UiUtlis.getColor(R.color.blue));
            viewTeamColorWin.setBackgroundColor(UiUtlis.getColor(R.color.Red));
            if (winSideBeanList.size() > 0 && loseSideBeanList.size() > 0) {
                tvGuaideAvdpower.setText("蓝方:" + (lose_power / loseSideBeanList.size()) + " | " + "红方:" + (win_power / winSideBeanList.size()));
            }

        }
        int pwoer_win_adv = (win_power / winSideBeanList.size());
        if (pwoer_win_adv > 0 && pwoer_win_adv < 1000) {
            imgPower.setBackgroundResource(R.drawable.tong);
            tvPowerDes.setText(R.string.tong);
        }
        if (pwoer_win_adv >= 1000 && pwoer_win_adv < 2000) {
            imgPower.setBackgroundResource(R.drawable.baiying);
            tvPowerDes.setText(R.string.baiying);
        }
        if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
            imgPower.setBackgroundResource(R.drawable.gold);
            tvPowerDes.setText(R.string.gold);
        }
        if (pwoer_win_adv >= 3000) {
            imgPower.setBackgroundResource(R.drawable.daemo);
            tvPowerDes.setText(R.string.daemo);
        }
        if (winSideBeanList.size() == 0 || loseSideBeanList.size() == 0) {
            imgPower.setVisibility(View.GONE);
            tvPowerDes.setText("人机局?脚本局?");
        }
        int minit = gameInfo_real.getMatch().getUsedTime()/60;
        tvTime.setText(" 耗时:"+minit+"分钟");


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
        return R.layout.fragment_guaideinfo;
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
                String res = gameUtils.getKillUserDataWin(eva.getGameInfo().getMatch().getWinSide(), SpUtils.getNowUser());
                String res_lose = gameUtils.getKillUserDataLose(eva.getGameInfo().getMatch().getLoseSide(), SpUtils.getNowUser());
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
}
