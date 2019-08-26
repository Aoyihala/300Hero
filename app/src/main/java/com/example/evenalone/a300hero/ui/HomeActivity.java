package com.example.evenalone.a300hero.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.LocalRoleListAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.bean.LocalUserBeanDao;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.bean.YourRole;
import com.example.evenalone.a300hero.event.BindEvent;
import com.example.evenalone.a300hero.event.ProxEvent;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.progress_binding)
    ProgressBar progressBinding;
    @BindView(R.id.li_search_view)
    LinearLayout liSearchView;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.recycer_myrole_list)
    RecyclerView recycerMyroleList;
    private String name;
    private LocalUserBeanDao userBeanDao;
    private LocalRoleListAdapter roleListAdapter;
    private List<LocalUserBean> userBeanList = new ArrayList<>();
    @Override
    protected void initview() {

        imgSearch.setVisibility(View.VISIBLE);
        //监听成功按钮
        editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.ACTION_DOWN||event.getKeyCode()==KeyEvent.ACTION_UP) {
                    name = editSearch.getText().toString();
                    if (!TextUtils.isEmpty(name)) {
                        Request request = new Request.Builder()
                                .url(Contacts.ROLE_URL + "?name=" + name)
                                .build();
                        MyApplication.getOkhttpUtils().sendRequest(request, YourRole.class);
                    } else {
                        Snackbar.make(imgSearch, "请输入召唤师名称", Snackbar.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });
        /**
         * 备用
         */
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editSearch.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    Request request = new Request.Builder()
                            .url(Contacts.ROLE_URL + "?name=" + name)
                            .build();
                    MyApplication.getOkhttpUtils().sendRequest(request, YourRole.class);
                    progressBinding.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(imgSearch, "请输入召唤师名称", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 前往设置界面
         */
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivty(SettingActivity.class,false);
            }
        });

        roleListAdapter.setOnlocalHisClickListener(new LocalRoleListAdapter.OnlocalHisClickListener() {
            @Override
            public void onClick(String name) {
               /* SpUtils.selectUser(name);*/
                SpUtils.setMianUser(name);
                Bundle bundle = new Bundle();
                bundle.putString("nickname",name);
                bundle.putBoolean("mode",false);
                startActivtyWithBundle(ListActivity.class,true,bundle);
            }
        });
    }


    @Override
    protected void initdata() {
        //建立数据库储存

        roleListAdapter = new LocalRoleListAdapter();
        userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
        userBeanList = userBeanDao.loadAll();
        if (userBeanList!=null&&userBeanList.size()>0)
        {

            roleListAdapter.setLocalUserBeans(userBeanList);
            recycerMyroleList.setLayoutManager(new LinearLayoutManager(this));
            recycerMyroleList.setAdapter(roleListAdapter);
            roleListAdapter.notifyDataSetChanged();
            editSearch.setHint("添加新角色");
        }


        if (SpUtils.isPorxy())
        {
            //代理模式已开启
            MyApplication.getOkhttpUtils().updateproxy(0,HomeActivity.class);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    /*
    @Override
    protected void setBackBtn(View view) {
        super.setBackBtn(imgBtn);
    }
    */
    public LocalUserBean searchuser(String nickname) {
        LocalUserBean localUserBean = userBeanDao.queryBuilder().where(LocalUserBeanDao.Properties.Nickname.eq(nickname)).unique();
        return localUserBean;
    }
    //查找角色回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getProxy(ProxEvent eva) {
        List<NetWorkProx> proxList = eva.getNetWorkProxes();
        if (proxList != null && proxList.size() > 0) {
            MyApplication.proxList = proxList;
            Toast.makeText(MyApplication.getContext(), "代理设置成功", Toast.LENGTH_SHORT).show();
            MyApplication.getOkhttpUtils().setProxy(proxList, proxList.get(0));
        } else {
            Snackbar.make(imgSearch, "今日访问很频繁，可能会照成访问失败", Snackbar.LENGTH_SHORT).show();
        }
    }
    //获取绑定用户
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBindUser(BindEvent event) {
        if (event.isSuccess()) {
            if (event.getYourRole() == null) {
                Snackbar.make(imgSearch, "未找到该角色", Snackbar.LENGTH_SHORT).show();
                progressBinding.setVisibility(View.GONE);
                return;
            }
            //成功
            boolean ishad = false;
            YourRole role = event.getYourRole();
            if (!role.getResult().equals("OK")) {
                String nocharater = "sql: no rows in result set";
                Snackbar.make(imgSearch, role.getResult().equals(nocharater)?"没有找到该角色,请检查输入":"今日访问很频繁，请避开高峰期比如周末或者尝试到设置界面打开代理模式", Snackbar.LENGTH_SHORT).show();
                progressBinding.setVisibility(View.GONE);
                return;
            }
            LocalUserBean localUserBean = new LocalUserBean();
            localUserBean = searchuser(role.getRole().getRoleName());
            if (localUserBean != null) {
                localUserBean.setId(localUserBean.getId());
                ishad = true;
            } else {
                localUserBean = new LocalUserBean();
            }
            //获取rank里的(也就是最近一局)
            List<YourRole.RankBean> rankBeans = role.getRank();
            //头像地址
            String type_icon = null;
            //团分
            String power = null;
            if (rankBeans != null && rankBeans.size() > 0) {
                for (YourRole.RankBean rankBean : rankBeans) {
                    if (rankBean.getType() > 15) {
                        //type 1-15是行为排行
                        //以上英雄排行
                        //type -15加上.png后缀评价url就是图片地址
                        //只取一个
                        if (rankBean.getType() - 15 > 100) {
                            type_icon = "0" + (rankBean.getType() - 15) + ".png";
                        }
                        if (rankBean.getType() - 15 < 100) {
                            type_icon = "00" + (rankBean.getType() - 15) + ".png";
                        }

                    }
                    if (rankBean.getType() == 1) {
                        //获取团分
                        power = rankBean.getValue();
                    }
                }
            } else {
                type_icon = "0053.png";
            }
            //计算一下胜率
            int wincount = role.getRole().getWinCount();
            int all = role.getRole().getMatchCount();
            int viotory = (int) (((double) wincount / (double) all) * 100);
            localUserBean.setNickname(name);
            localUserBean.setViotory(viotory + "%");
            localUserBean.setIocnfile(type_icon);
            localUserBean.setJumpvalue(power);
            localUserBean.setResult(new Gson().toJson(role));
            //暂时未接入极光
            //save包含了insert和update
            if (ishad) {
                userBeanDao.update(localUserBean);
            } else {
                userBeanDao.save(localUserBean);
            }
            progressBinding.setVisibility(View.GONE);
            //选定
            /*SpUtils.selectUser(localUserBean.getNickname());*/
            SpUtils.setMianUser(localUserBean.getNickname());
            Bundle bundle = new Bundle();
            bundle.putString("nickname",name);
            bundle.putBoolean("mode",false);
            startActivtyWithBundle(ListActivity.class, true,bundle);
        } else {
            Snackbar.make(imgSearch, "查询角色失败:" + event.getErroMsg(), Snackbar.LENGTH_SHORT).show();
            progressBinding.setVisibility(View.GONE);
        }
    }
}
