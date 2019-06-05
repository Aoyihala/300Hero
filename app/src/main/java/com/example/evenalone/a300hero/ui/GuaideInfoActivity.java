package com.example.evenalone.a300hero.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.base.BaseActivity;

import butterknife.BindView;

public class GuaideInfoActivity extends BaseActivity {


    @BindView(R.id.img_btn)
    ImageView imgBtn;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.img_charater_manange)
    ImageView imgCharaterManange;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_hero_guaide)
    TabLayout tabHeroGuaide;
    @BindView(R.id.view_pager_guaide)
    ViewPager viewPagerGuaide;

    @Override
    protected void initview() {

    }

    @Override
    protected void initdata() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guaide_info;
    }
}
