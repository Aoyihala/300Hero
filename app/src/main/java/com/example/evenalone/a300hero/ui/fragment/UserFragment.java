package com.example.evenalone.a300hero.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.evenalone.a300hero.bean.MakeViewBean;
import com.example.evenalone.a300hero.event.UpdateEvent;
import com.example.evenalone.a300hero.utils.GameUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.wedgit.MySwitch;
import com.google.gson.Gson;
import com.rey.material.widget.Switch;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private LocalGaideListInfoDao gaideListInfoDao;
    private List<HeroGuide.ListBean> listBeanList = new ArrayList<>();
    private List<LocalGaideListInfo> localGaideListInfos = new ArrayList<>();
    private List<String> reapet_list = new ArrayList<>();
    private Map<String,String> icon_map = new HashMap<>();
    private Map<String,Integer> my_guaide = new HashMap<>();
    private List<MakeViewBean> powerlist = new ArrayList<>();
    private LocalGameInfoDao gameInfoDao;
    private UserAdapter userAdapter;
    private Thread my_thread;
    private GameUtils gameUtils;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==GameUtils.GET_USEDHERO)
            {
                //常用英雄
                icon_map = (Map<String, String>) msg.obj;
                if (icon_map!=null)
                {
                    userAdapter.setUsedHero(icon_map);
                }
            }
            else if (msg.what==GameUtils.GET_PWOERLIST)
            {
                powerlist = (List<MakeViewBean>) msg.obj;
                if (powerlist!=null)
                {
                    userAdapter.setPwoerList(powerlist);
                }
            }
            else
            {
                //用户成绩
                my_guaide = (Map<String, Integer>) msg.obj;
                if (my_guaide!=null)
                {
                    userAdapter.setYourCard(my_guaide);
                }

            }
            swipeBase.setRefreshing(false);

            return true;
        }
    });
    @Override
    protected boolean setEventOpen() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(UpdateEvent eva)
    {
        //loadUserData();
        userAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initview() {
        swipeBase.setColorSchemeColors(SpUtils.getMainColor());
        recycerUserinfo.setAdapter(userAdapter);
        recycerUserinfo.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //停止上一个线程
                if (my_thread!=null)
                {
                    my_thread.interrupt();
                    my_thread=null;
                }
                loadUserData();
            }
        });
        userAdapter.setMoreClickListener(new UserAdapter.OnMoreClickListener() {
            @Override
            public void onSwitchClick(MySwitch s, int pos) {
                if (s.isChecked())
                {
                    s.setChecked(false);
                    SpUtils.setMoreMode(false);
                   //调用
                    getMorePwoer();
                }
                else
                {
                    s.setChecked(true);
                    SpUtils.setMoreMode(true);
                    getMorePwoer();
                }
            }
        });
        }
        //根据用户的选择去获取团分
        public void getMorePwoer()
        {
            if (my_thread!=null&&my_thread.isInterrupted())
            {
                //清空
                my_thread = null;
            }
            my_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (gameUtils==null)
                    {
                        gameUtils = new GameUtils();
                    }
                         /*   gameUtils.getMyGuaide(SpUtils.getNowUser(),handler);
                            gameUtils.getUsedHero(SpUtils.getNowUser(),handler);*/
                    gameUtils.getMyPower(SpUtils.getNowUser(),handler);
                }
            });
            my_thread.start();
        }

    @Override
    protected void initdata() {
        gameUtils = new GameUtils();
        userAdapter = new UserAdapter();
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();

        //loadUserData();

    }

    public void loadUserData() {

            //计算数据,延时操作
            my_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (gameUtils==null)
                    {
                        gameUtils = new GameUtils();
                    }
                    gameUtils.getMyGuaide(SpUtils.getNowUser(),handler);
                    gameUtils.getUsedHero(SpUtils.getNowUser(),handler);
                    gameUtils.getMyPower(SpUtils.getNowUser(),handler);

    }
});
        //加载
        my_thread.start();
        }



    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_user;
    }



}
