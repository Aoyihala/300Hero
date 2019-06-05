package com.example.evenalone.a300hero.ui.fragment;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.HerolistAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseFragment;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.event.ListInfoEvent;
import com.example.evenalone.a300hero.event.NetWorkCancelEvent;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

/**
 * 战绩
 */
public class HeroGuaideFragment extends BaseFragment {
    @BindView(R.id.img_refresh)
    ImageView imgRefresh;
    @BindView(R.id.tv_next_page)
    TextView tvNextPage;
    @BindView(R.id.recycler_guaide_list)
    RecyclerView recyclerGuaideList;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_back_page)
    TextView tvBackPage;
    private HerolistAdapter herolistAdapter;
    private int page = 0;
    private boolean next=false;
    private boolean back=false;
    private boolean loadingcomplete = false;
    private List<HeroGuide.ListBean> listBeans = new ArrayList<>();

    @Override
    protected boolean setEventOpen() {
        return true;
    }

    @Override
    protected void initview() {
        startRefresh();
        recyclerGuaideList.setLayoutManager(new LinearLayoutManager(compatActivity));
        recyclerGuaideList.setAdapter(herolistAdapter);
        tvNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingcomplete) {
                    if (listBeans.size()<10)
                    {
                        //对已加载的数据进行判断
                        //表示没有了
                        Snackbar.make(tvBackPage,"没有更多战绩了",Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    tvNextPage.setText("下一页");
                    next = true;
                    page = page + 10;
                    requestData(page, false);
                    startRefresh();
                    loadingcomplete = false;

                }
                else
                {
                    Snackbar.make(tvNextPage, "正在加载中", Snackbar.LENGTH_LONG).setAction("取消加载", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyApplication.getOkhttpUtils().callCancel();
                        }
                    }).setActionTextColor(UiUtlis.getColor(R.color.Red)).show();
                }

            }
        });
        tvBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingcomplete)
                {
                    if (page>0)
                    {
                        page=page-10;
                    }
                    back = true;
                    //请求数据
                    requestData(page, false);
                    startRefresh();
                    loadingcomplete = false;
                }
                else
                {
                    Snackbar.make(tvNextPage, "正在加载中", Snackbar.LENGTH_LONG).setAction("取消加载", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyApplication.getOkhttpUtils().callCancel();
                        }
                    }).setActionTextColor(UiUtlis.getColor(R.color.Red)).show();
                }
            }
        });
    }

    public void startRefresh() {
        Animation animation = AnimationUtils.loadAnimation(compatActivity, R.anim.round);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        animation.setFillAfter(true);
        //给图片添加动画
        imgRefresh.startAnimation(animation);
    }

    public void stoprefresh() {
        imgRefresh.clearAnimation();
    }

    @Override
    protected void initdata() {

        herolistAdapter = new HerolistAdapter();

        requestData(page, false);
    }

    public void requestData(int page, boolean b)
    {
        if (b)
        {
            tvBackPage.setVisibility(View.GONE);
            this.page=0;
            startRefresh();
            recyclerGuaideList.removeAllViews();
        }
        tvState.setText(R.string.loading);
        Request request = new Request.Builder()
                .url(Contacts.LIST_URL + "?name=" + SpUtils.getNowUser() + "&index=" + page)
                .build();
        MyApplication.getOkhttpUtils().sendRequest(request, HeroGuide.class);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_heroguide;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHerolist(ListInfoEvent eva) {
        if (eva.isSuccess()) {
            if (eva.getGuide() == null) {
                Snackbar.make(tvNextPage, "获取战局失败,请尝试刷新", Snackbar.LENGTH_SHORT).show();
                tvState.setText(R.string.loading_faild);
                return;
            }
            if (!eva.getGuide().getResult().equals("OK"))
            {
                Snackbar.make(tvNextPage,"今日访问很频繁，请避开高峰期比如周末或者尝试到设置界面打开代理模式",Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (eva.getGuide().getList().size()==0)
            {
                //表示没有了
                Snackbar.make(tvBackPage,"没有更多战绩了",Snackbar.LENGTH_SHORT).show();
                page=page-10;
                return;
            }
            listBeans.clear();
            recyclerGuaideList.removeAllViews();
            listBeans = new ArrayList<>();
            listBeans.addAll(eva.getGuide().getList());
            herolistAdapter.setListBeans(listBeans);
            herolistAdapter.notifyDataSetChanged();
            stoprefresh();
            loadingcomplete = true;
            tvState.setText("加载完成");

            if (page>=10)
            {
                tvBackPage.setVisibility(View.VISIBLE);
            }
            if (page<10)
            {
                tvBackPage.setVisibility(View.GONE);
            }
            next = false;
            back =false;
        }
    }

    //只要有取消操作的就可以
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cancelEvent(NetWorkCancelEvent eva)
    {
        if (eva.getClassname().equals(HeroGuide.class.getSimpleName()))
        {
            //是请求战绩的操作
            //重置ui
            stoprefresh();
            loadingcomplete = true;
            tvState.setText("停止加载");

            //撤回翻页操作
            if (back)
            {
                page = page+10;
            }
            if (next)
            {
                if (page>10)
                {
                    page = page-10;
                }
              else
                {
                    page =0;
                }
            }
            if (page==0)
            {
                tvNextPage.setText("刷新");
            }

            Toast.makeText(MyApplication.getContext(),"已停止访问",Toast.LENGTH_SHORT).show();

        }

    }
}
