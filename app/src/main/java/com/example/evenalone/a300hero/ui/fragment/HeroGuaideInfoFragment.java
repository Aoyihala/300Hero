package com.example.evenalone.a300hero.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.base.BaseFragment;

import butterknife.BindView;

public class HeroGuaideInfoFragment extends BaseFragment {
    @BindView(R.id.tv_guaide_avdpower)
    TextView tvGuaideAvdpower;
    @BindView(R.id.recycer_winsaide)
    RecyclerView recycerWinsaide;
    @BindView(R.id.recycer_loseside)
    RecyclerView recycerLoseside;

    @Override
    protected boolean setEventOpen() {
        return false;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initdata() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guaideinfo;
    }
}
