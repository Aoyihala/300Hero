package com.example.evenalone.a300hero.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.ProxyListAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.event.SProxyEvent;
import com.example.evenalone.a300hero.event.UpdateEvent;
import com.example.evenalone.a300hero.utils.SpUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.img_userhead)
    CircleImageView imgUserhead;
    @BindView(R.id.tv_search_nickname)
    TextView tvSearchNickname;
    @BindView(R.id.li_userinfo)
    LinearLayout liUserinfo;
    @BindView(R.id.img_btn)
    ImageView imgBtn;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.checkbox_proxy)
    CheckBox checkboxProxy;
    @BindView(R.id.card_check)
    CardView cardCheck;
    @BindView(R.id.ra_a)
    RelativeLayout raA;
    @BindView(R.id.expand_proxy_view)
    ExpandableLayout expandProxyView;
    @BindView(R.id.recycler_proxy)
    RecyclerView recyclerProxy;
    @BindView(R.id.tv_next_page)
    TextView tvNextPage;
    @BindView(R.id.img_charater_manange)
    ImageView imgCharaterManange;
    @BindView(R.id.check_x)
    CheckBox checkX;
    @BindView(R.id.card_x)
    LinearLayout cardX;
    @BindView(R.id.check_y)
    CheckBox checkY;
    @BindView(R.id.card_y)
    LinearLayout cardY;
    private boolean loading_complete = false;
    private List<NetWorkProx> proxList = new ArrayList<>();
    private ProxyListAdapter listAdapter;
    private int page = 0;

    @Override
    protected void initview() {
        tvTopTitle.setText("设置");
        checkboxProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxProxy.isChecked()) {
                    expandProxyView.expand(true);
                    SpUtils.setPorxy(true);
                    MyApplication.getOkhttpUtils().updateproxy(page, SettingActivity.class);
                } else {
                    expandProxyView.collapse(true);
                    SpUtils.setPorxy(false);
                    MyApplication.getOkhttpUtils().init();
                }
            }
        });
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading_complete) {
                    page = page + 1;
                    MyApplication.getOkhttpUtils().updateproxy(page, SettingActivity.class);
                    loading_complete = false;
                } else {
                    Snackbar.make(toolBar, "加载中请稍后", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        checkX.setChecked(SpUtils.getX());
        checkY.setChecked(SpUtils.getY());
        cardX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkX.isChecked())
                {
                    SpUtils.setX(false);
                    checkX.setChecked(false);
                    EventBus.getDefault().post(new UpdateEvent());
                }
                else
                {
                    checkX.setChecked(true);
                    SpUtils.setX(true);
                    EventBus.getDefault().post(new UpdateEvent());
                }
            }
        });
        checkX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtils.setX(isChecked);
                EventBus.getDefault().post(new UpdateEvent());
            }
        });
        cardY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkY.isChecked())
                {
                    SpUtils.sety(false);
                    checkY.setChecked(false);
                    EventBus.getDefault().post(new UpdateEvent());
                }
                else
                {
                    SpUtils.sety(true);
                    checkY.setChecked(true);
                    EventBus.getDefault().post(new UpdateEvent());
                }
            }
        });
        checkY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtils.sety(isChecked);
                EventBus.getDefault().post(new UpdateEvent());
            }
        });
    }

    @Override
    protected void initdata() {
        listAdapter = new ProxyListAdapter();
        recyclerProxy.setHasFixedSize(true);
        recyclerProxy.setLayoutManager(new LinearLayoutManager(this));
        recyclerProxy.setAdapter(listAdapter);
        if (MyApplication.proxList != null && MyApplication.proxList.size() > 0) {
            listAdapter.setProxList(MyApplication.proxList);
            listAdapter.notifyDataSetChanged();
        }
        //设置视图状态
        checkboxProxy.setChecked(SpUtils.isPorxy());
        if (SpUtils.isPorxy()) {
            expandProxyView.setExpanded(true);
            MyApplication.getOkhttpUtils().updateproxy(page, SettingActivity.class);
        } else {
            expandProxyView.setExpanded(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    /**
     * proxy代理list
     *
     * @param eva
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getProxyList(SProxyEvent eva) {
        proxList = eva.getNetWorkProxes();
        MyApplication.proxList = proxList;
        listAdapter.setProxList(proxList);
        listAdapter.notifyDataSetChanged();
        MyApplication.getOkhttpUtils().setProxy(MyApplication.proxList, MyApplication.proxList.get(0));
        loading_complete = true;

    }


}
