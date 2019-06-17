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
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
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
            //按时间排序
            ListSort(localGaideListInfos);
            List<HeroGuide.ListBean.HeroBean> reapet_list = new ArrayList<>();
            //筛选重复的数据 key 重复元素名字 value 重复次数
            Map<HeroGuide.ListBean.HeroBean,Integer> repat_chance = new HashMap<>();
            for (LocalGaideListInfo info:localGaideListInfos)
            {
                HeroGuide.ListBean listBean = gson.fromJson(info.getResult(),HeroGuide.ListBean.class);
                if (repat_chance.containsKey(listBean.getHero()))
                {
                    Integer num = repat_chance.get(listBean.getHero());

                    repat_chance.put(listBean.getHero(), num+1);

                }else{

                    repat_chance.put(listBean.getHero(), 1);

                }
            }
            //使用次数大于4为常用英雄
            for (Map.Entry<HeroGuide.ListBean.HeroBean,Integer> entry:repat_chance.entrySet())
            {
                if (localGaideListInfos.size()==10)
                {
                    //使用超过两次就算
                    if (entry.getValue()>2)
                    {
                        reapet_list.add(entry.getKey());
                    }
                }
                else
                {
                    if (entry.getValue()>3)
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
