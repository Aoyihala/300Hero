package com.example.evenalone.a300hero.ui;

import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.CharatcorAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.bean.LocalUserBeanDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 角色管理界面
 */
public class CharactorActivity extends BaseActivity {


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
    @BindView(R.id.img_charater_manange)
    ImageView imgCharaterManange;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recycler_charatorlist)
    RecyclerView recyclerCharatorlist;
    @BindView(R.id.checkbox_allselect)
    CheckBox checkboxAllselect;
    @BindView(R.id.tv_select_delete)
    TextView tvSelectDelete;
    @BindView(R.id.tv_select_cancel)
    TextView tvSelectCancel;
    @BindView(R.id.card_deletemode)
    CardView cardDeletemode;
    private Vibrator vibrator = (Vibrator) MyApplication.getContext().getSystemService(MyApplication.getContext().VIBRATOR_SERVICE);
    private CharatcorAdapter charatcorAdapter;
    private LocalUserBeanDao userBeanDao;
    private List<LocalUserBean> localUserBeanList = new ArrayList<>();
    public static  Map<LocalUserBean,Boolean> localUserBeanMap = new HashMap<>();
    private boolean showselectmode = false;

    @Override
    protected void initview() {
        tvTopTitle.setText("绑定角色管理");
        recyclerCharatorlist.setAdapter(charatcorAdapter);
        recyclerCharatorlist.setLayoutManager(new LinearLayoutManager(this));
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSelectCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDeletemode.setVisibility(View.GONE);
                charatcorAdapter.setSeclectMode(false);
                localUserBeanMap.clear();
                loadList();
                charatcorAdapter.notifyDataSetChanged();
            }
        });
        checkboxAllselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                charatcorAdapter.setAllseclet(isChecked);
                charatcorAdapter.notifyDataSetChanged();
                if (!isChecked)
                {
                    //更新
                    localUserBeanMap.clear();
                    loadList();
                }
            }
        });
        tvSelectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接删除
                //获取map里为true的实体
                for (Map.Entry<LocalUserBean,Boolean> entry:localUserBeanMap.entrySet())
                {
                    if (entry.getValue())
                    {
                        userBeanDao.delete(entry.getKey());
                    }
                }
                charatcorAdapter.setSeclectMode(false);
                charatcorAdapter.setAllseclet(false);
                cardDeletemode.setVisibility(View.GONE);
                loadList();
            }
        });
        charatcorAdapter.setOnLongClickListiener(new CharatcorAdapter.onLongClickListiener() {
            @Override
            public void onClick(CheckBox checkBox, int pos) {
                vibrator.vibrate(1000);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(true);
                localUserBeanMap.put(localUserBeanList.get(pos - 1),true);
                showselectmode = true;
                cardDeletemode.setVisibility(View.VISIBLE);
                charatcorAdapter.setSeclectMode(true);
                charatcorAdapter.notifyDataSetChanged();
            }
        });
        charatcorAdapter.setOnClickListener(new CharatcorAdapter.onClickListener() {
            @Override
            public void onShortClick(CheckBox checkBox, int pos) {
                if (showselectmode) {
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                        localUserBeanMap.put(localUserBeanList.get(pos - 1),false);
                        charatcorAdapter.notifyItemChanged(pos);
                    } else {
                        checkBox.setChecked(true);
                        localUserBeanMap.put(localUserBeanList.get(pos - 1),true);
                        charatcorAdapter.notifyItemChanged(pos);
                    }
                    return;
                }
                //这里页面跳转至listactivity

            }
        });
    }

    @Override
    protected void initdata() {
        userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
        charatcorAdapter = new CharatcorAdapter();
        loadList();
    }

    private void loadList() {
        localUserBeanList = userBeanDao.loadAll();
        for (LocalUserBean localUserBean : localUserBeanList) {
            localUserBeanMap.put(localUserBean,false);
        }
        charatcorAdapter.setLocalUserBeanList(localUserBeanList);
        charatcorAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charactor;
    }

}
