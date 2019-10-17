package com.example.evenalone.a300hero.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.ProxyListAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.bean.UpdateBean;
import com.example.evenalone.a300hero.event.SProxyEvent;
import com.example.evenalone.a300hero.event.UpdateEvent;
import com.example.evenalone.a300hero.event.UpdateVersionEvent;
import com.example.evenalone.a300hero.service.JobSchedulerManager;
import com.example.evenalone.a300hero.service.MyNotifiService;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.google.gson.Gson;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    @BindView(R.id.check_clock)
    CheckBox checkClock;
    @BindView(R.id.card_clock)
    CardView cardClock;
    @BindView(R.id.check_update)
    CheckBox checkUpdate;
    @BindView(R.id.card_update)
    CardView cardUpdate;
    @BindView(R.id.tv_toMyadress)
    TextView tv_myadress;
    private boolean loading_complete = false;
    private List<NetWorkProx> proxList = new ArrayList<>();
    private ProxyListAdapter listAdapter;
    private int page = 0;

    @Override
    protected void initview() {
        tvTopTitle.setText("设置");
        tv_myadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);    //为Intent设置Action属性
                intent.setData(Uri.parse("https://github.com/Aoyihala/300Hero")); //为Intent设置DATA属性
                startActivity(intent);
            }
        });
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
        checkClock.setChecked(SpUtils.isClock());
        cardX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkX.isChecked()) {
                    SpUtils.setX(false);
                    checkX.setChecked(false);
                    EventBus.getDefault().post(new UpdateEvent());
                } else {
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
                if (checkY.isChecked()) {
                    SpUtils.sety(false);
                    checkY.setChecked(false);
                    EventBus.getDefault().post(new UpdateEvent());
                } else {
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
        //设置定时任务
        cardClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkClock.setChecked(!checkClock.isChecked());
            }
        });
        checkClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtils.setClock(checkClock.isChecked());
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //目的是为了保活
                        JobSchedulerManager.getJobSchedulerInstance(SettingActivity.this).startJobScheduler();
                    } else {
                        Toast.makeText(MyApplication.getContext(), "安卓版本过低,至少在安卓5.1版本以上运行该功能", Toast.LENGTH_SHORT);
                    }
                    Snackbar.make(toolBar, "记得赋予软件自启权限", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        JobSchedulerManager.getJobSchedulerInstance(SettingActivity.this).stopJobScheduler();
                        //停止服务
                        Intent in = new Intent(SettingActivity.this, MyNotifiService.class);
                        SettingActivity.this.stopService(in);

                    } else {
                        Toast.makeText(MyApplication.getContext(), "安卓版本过低,至少在安卓5.1版本以上运行该功能", Toast.LENGTH_SHORT);
                    }
                }

            }
        });
        checkUpdate.setChecked(SpUtils.isupdate());
        //检查更新卡片
        cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Snackbar.make(toolBar,"检测更新",Snackbar.LENGTH_SHORT).show();
               check();
            }
        });
        //检查更新
        checkUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    SpUtils.saveUpdate(true);
                    //启用服务

                }
                else
                {
                    SpUtils.saveUpdate(false);
                    //关闭服务
                }
            }
        });
    }
    public String fileRead(File file) {
        FileReader reader = null;//定义一个fileReader对象，用来初始化BufferedReader
        try {
            reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
            StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            }
            bReader.close();
            String str = sb.toString();
            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;

    }
    private void check() {

        //尝试直接访问
        x.http().get(new RequestParams("http://cloud.thacg.cn/index.php/s/cpZc8tmaWPLZxsr/download"), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                if (result!=null)
                {
                    String json =  fileRead(result);
                    //json =String.format(json,Charset.forName("utf-8"));
                    Log.e("结果",json);

                    //序列化
                    UpdateBean updateBean = new Gson().fromJson(json,UpdateBean.class);
                    //判断版本号
                    String nowversion = UiUtlis.getVersion();
                    if (!updateBean.getVersion().equals(nowversion))
                    {
                        //如果不是相同版本
                        EventBus.getDefault().postSticky(new UpdateVersionEvent(updateBean));

                    }
                    else
                    {
                        Snackbar.make(toolBar,"已是最新版本!",Snackbar.LENGTH_SHORT).show();
                        Log.e("状态","当前已是最新版本");
                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.e("更新内容下载结束","结束");
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

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
