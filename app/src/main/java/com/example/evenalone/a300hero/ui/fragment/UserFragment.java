package com.example.evenalone.a300hero.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.UserAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseFragment;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用户资格详情
 */
public class UserFragment extends BaseFragment {
    @BindView(R.id.recycer_userinfo)
    RecyclerView recycerUserinfo;
    @BindView(R.id.swipe_base)
    SwipeRefreshLayout swipeBase;
    private UserAdapter userAdapter;
    private LocalGaideListInfoDao gaideListInfoDao;
    private List<HeroGuide.ListBean> listBeanList = new ArrayList<>();
    private List<LocalGaideListInfo> localGaideListInfos = new ArrayList<>();
    private List<String> reapet_list = new ArrayList<>();
    private HashMap<String,String> icon_map = new HashMap<>();
    private LocalGameInfoDao gameInfoDao;
    @Override
    protected boolean setEventOpen() {
        return false;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initdata() {
        userAdapter = new UserAdapter();
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();
        //计算数据,延时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadingdata();
            }
        }).start();

    }

    private void loadingdata() {
        //获取对应角色的数据
        localGaideListInfos = searchbyName(SpUtils.getNowUser());
        //数据至少大于一条内容
        Gson gson = new Gson();
        //10条开始统计
        if (localGaideListInfos.size()>=10)
        {
            icon_map = new HashMap<>();
            //按时间排序
            ListSort(localGaideListInfos);
             reapet_list = new ArrayList<>();
            //筛选重复的数据 key 重复元素名字 value 重复次数
            Map<String,Integer> repat_chance = new HashMap<>();
            for (LocalGaideListInfo info:localGaideListInfos)
            {
                HeroGuide.ListBean listBean = gson.fromJson(info.getResult(),HeroGuide.ListBean.class);
                icon_map.put(listBean.getHero().getName(),listBean.getHero().getIconFile());
                if (repat_chance.containsKey(listBean.getHero().getName()))
                {
                    Integer num = repat_chance.get(listBean.getHero().getName());

                    repat_chance.put(listBean.getHero().getName(), num+1);

                }else{

                    repat_chance.put(listBean.getHero().getName(), 1);

                }
            }
            //使用次数大于4为常用英雄
            for (Map.Entry<String,Integer> entry:repat_chance.entrySet())
            {
                if (localGaideListInfos.size()==10)
                {
                    //使用超过两次就算
                    if (entry.getValue()>=2)
                    {
                        reapet_list.add(entry.getKey());
                    }
                }
                else
                {
                    if (entry.getValue()>=3)
                    {
                        reapet_list.add(entry.getKey());
                    }
                }

            }
            //最多取三位
            if (reapet_list.size()>3)
            {
                reapet_list = reapet_list.subList(0,2);
            }
            //---------------------------------------------------------------
            //设置雷达基准图
            //以下为100%一局基准
            //最大补兵数
            int max_killunincount = 500;
            //最大推塔数
            int max_towercount = 15;
            //最大杀敌数(50),不算战场人机，超过60不计算
            int max_killcount = 60;
            //最大助攻数，助攻比人头好拿
            int max_assinatcount = 120;
            //最大经济状况
            long max_money = 30000;
            int all_killcount = 0;
            long all_money = 0;
            int all_assciont = 0;
            int all_tower = 0;
            int all_unicount = 0;
            //计算贡献 (总人头+总助攻)/（理想助攻+理想人头）
            //获取总人头 总助攻 总杀敌 总经济 总推塔
            List<GameInfo> gameInfoList = new ArrayList<>();
            for (LocalGaideListInfo info:localGaideListInfos)
            {
                HeroGuide.ListBean listBean = gson.fromJson(info.getResult(),HeroGuide.ListBean.class);
                //查询战局
                GameInfo in = getGameinfo(listBean.getMatchID(),gson);
                in.setMyresult(listBean.getResult());
                gameInfoList.add(in);
            }
            //筛选战局
            //输赢都要统计
            for (GameInfo info:gameInfoList)
            {
                if (info.getMyresult()==1)
                {
                    for (GameInfo.MatchBean.WinSideBean winSideBean:info.getMatch().getWinSide())
                    {
                        if (winSideBean.getRoleName().equals(SpUtils.getNowUser()))
                        {
                            all_killcount = all_killcount+winSideBean.getKillCount();
                            all_money = all_money +winSideBean.getTotalMoney();
                            all_tower = all_tower+winSideBean.getTowerDestroy();
                            all_unicount = all_unicount+winSideBean.getKillUnitCount();
                            all_assciont = all_assciont+winSideBean.getAssistCount();
                        }
                    }
                }
                else
                {
                    for (GameInfo.MatchBean.LoseSideBean loseSideBean:info.getMatch().getLoseSide())
                    {
                        if (loseSideBean.getRoleName().equals(SpUtils.getNowUser()))
                        {
                            all_killcount = all_killcount+loseSideBean.getKillCount();
                            all_money = all_money +loseSideBean.getTotalMoney();
                            all_tower = all_tower+loseSideBean.getTowerDestroy();
                            all_unicount = all_unicount+loseSideBean.getKillUnitCount();
                            all_assciont = all_assciont+loseSideBean.getAssistCount();
                        }
                    }
                }
            }
            //计算百分比


            //获取自己的团分变化
            List<Integer> jumpvalue = new ArrayList<>();

        }

    }

    private static void ListSort(List<LocalGaideListInfo> list) {
        Collections.sort(list, new Comparator<LocalGaideListInfo>() {
            @Override
            public int compare(LocalGaideListInfo o1, LocalGaideListInfo o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    private GameInfo getGameinfo(long matchid,Gson gson)
    {
         LocalGameInfo gameInfo = gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.MactherId.eq(matchid)).unique();

         return gson.fromJson(gameInfo.getResult(),GameInfo.class);
    }

    private List<LocalGaideListInfo> searchbyName(String nickname)
    {
        List<LocalGaideListInfo> datas = gaideListInfoDao.queryBuilder().where(LocalGaideListInfoDao.Properties.Nickname.eq(nickname)).list();
        return datas;
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_user;
    }



}
