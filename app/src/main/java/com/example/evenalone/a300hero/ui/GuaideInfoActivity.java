package com.example.evenalone.a300hero.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.MyViewPagerAdapter;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.ui.fragment.HeroGuaideInfoFragment;
import com.example.evenalone.a300hero.ui.fragment.PowerValueFragment;

import java.util.ArrayList;
import java.util.List;

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
    private long id;
    private Bundle bundle;
    private HeroGuaideInfoFragment infoFragment;
    private PowerValueFragment valueFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titlse= new ArrayList<>();
    private MyViewPagerAdapter viewPagerAdapter;
    @Override
    protected void initview() {

    }

    @Override
    protected void initdata() {
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        if (getIntent().getExtras()!=null)
        {
            inintfragment();

        }
    }

    private void inintfragment() {
        infoFragment = new HeroGuaideInfoFragment();
        valueFragment = new PowerValueFragment();
        infoFragment.setArguments(getIntent().getExtras());
        valueFragment.setArguments(getIntent().getExtras());
        fragmentList.add(infoFragment);
        fragmentList.add(valueFragment);
        titlse.add("对局详情");
        titlse.add("团分对比");
        viewPagerGuaide.setAdapter(viewPagerAdapter);
        viewPagerAdapter.setFragmentList(fragmentList,titlse);
        viewPagerAdapter.notifyDataSetChanged();
        tabHeroGuaide.setupWithViewPager(viewPagerGuaide);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guaide_info;
    }
}
