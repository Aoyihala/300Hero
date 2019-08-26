package com.example.evenalone.a300hero.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.adapter.MyViewPagerAdapter;
import com.example.evenalone.a300hero.adapter.PopLoaclUserListAdapter;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.bean.LocalUserBeanDao;
import com.example.evenalone.a300hero.bean.YourRole;
import com.example.evenalone.a300hero.event.BindEvent;
import com.example.evenalone.a300hero.event.JumpValueEvnet;
import com.example.evenalone.a300hero.ui.fragment.GayFragment;
import com.example.evenalone.a300hero.ui.fragment.HeroGuaideFragment;
import com.example.evenalone.a300hero.ui.fragment.UserFragment;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.example.evenalone.a300hero.wedgit.AppBarStateChangeListener;
import com.example.evenalone.a300hero.wedgit.MenuPopwindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

/**
 * 战绩列表界面
 */
public class ListActivity extends BaseActivity {


    @BindView(R.id.li_userinfo)
    LinearLayout liUserinfo;
    @BindView(R.id.img_btn)
    ImageView imgBtn;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.left_menu)
    NavigationView leftMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.img_jump_bg)
    ImageView imgJumpBg;
    @BindView(R.id.img_jump_userhead)
    CircleImageView imgJumpUserhead;
    @BindView(R.id.tv_jump_name_home)
    TextView tvJumpNameHome;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_jump_guaide_home)
    TextView tvJumpGuaideHome;
    @BindView(R.id.tv_jump_viotory_home)
    TextView tvJumpViotoryHome;
    @BindView(R.id.collse_layout)
    CollapsingToolbarLayout collseLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.img_charater_manange)
    ImageView imgCharaterManange;
    @BindView(R.id.img_userhead)
    CircleImageView imgUserhead;
    @BindView(R.id.tv_search_nickname)
    TextView tvSearchNickname;
    @BindView(R.id.img_home_duanwei)
    ImageView imgHomeDuanwei;
    @BindView(R.id.tv_home_duanwei)
    TextView tvHomeDuanwei;
    @BindView(R.id.btn_back)
    FloatingActionButton btn_back_action;
    private LocalUserBean localUserBean;
    private LocalUserBeanDao userBeanDao;
    private View head_view;
    private ImageView img_bg;
    private CircleImageView img_avator;
    private TextView tv_jump_value;
    private TextView tv_jump_viotroy;
    private TextView tv_jump_name;
    private boolean isloading = true;
    private String nickname;
    private boolean vistormode;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private HeroGuaideFragment guaideFragment;
    private UserFragment userFragment;
  /*  private GayFragment gayFragment;*/
    private MenuPopwindow menuPopwindow;
    private LinearLayout li_pop_setting;
    private LinearLayout li_pop_add;
    private RecyclerView recyclerView_local;
    private List<String> titles = new ArrayList<>();
    private PopLoaclUserListAdapter roleListAdapter;
    private List<LocalUserBean> localUserBeanList = new ArrayList<>();
    public boolean isfirst=true;
    private String visitor_name;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setVistormode(boolean vistormode) {
        this.vistormode = vistormode;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    protected void onResume() {
        super.onResume();
        roleListAdapter.setLocalUserBeanList(userBeanDao.loadAll());
        if (isfirst)
        {
            isfirst = false;
            return;
        }
       /* nickname = getIntent().getExtras().getString("nickname");
        vistormode = getIntent().getExtras().getBoolean("mode");*/
    }
    @Override
    protected void initview() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTopTitle.setVisibility(View.GONE);
        toolBar.setBackgroundColor(Color.TRANSPARENT);
        imgBtn.setImageResource(R.drawable.ic_keyboard_backspace_white_24dp);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.fish_avator);
        //高斯模糊化
        imgJumpBg.setImageBitmap(praseBitmap(bitmap));
        //监听appbar收缩事件
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //
                    liUserinfo.setVisibility(View.GONE);
                    imgBtn.setVisibility(View.GONE);
                    if (vistormode)
                    {
                        btn_back_action.show();
                    }

                } else if (state == State.COLLAPSED) {

                    liUserinfo.setVisibility(View.VISIBLE);
                    imgBtn.setVisibility(View.GONE);
                    if (vistormode)
                    {
                        btn_back_action.hide();
                    }

                } else {
                    liUserinfo.setVisibility(View.GONE);
                    imgBtn.setVisibility(View.GONE);

                }
            }
        });
        //角色列表
        roleListAdapter = new PopLoaclUserListAdapter(nickname);
        roleListAdapter.setLocalUserBeanList(userBeanDao.loadAll());
        menuPopwindow = new MenuPopwindow(this);
        recyclerView_local = menuPopwindow.getContentView().findViewById(R.id.recycler_user_history);
        li_pop_setting = menuPopwindow.getContentView().findViewById(R.id.li_pop_setting);
        li_pop_add = menuPopwindow.getContentView().findViewById(R.id.li_pop_add);
        recyclerView_local.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_local.setAdapter(roleListAdapter);
        roleListAdapter.notifyDataSetChanged();
        imgCharaterManange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPopwindow.showPopupWindow(v);
            }
        });
        /**
         * 设置
         */
        li_pop_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPopwindow.dismiss();
                startActivty(SettingActivity.class, false);
            }
        });
        /**
         * 添加角色
         */
        li_pop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPopwindow.dismiss();
                startActivty(HomeActivity.class, false);
            }
        });
        /**
         * 返回
         */
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 菜单点击事件
         */
        roleListAdapter.setClickPoslistener(new PopLoaclUserListAdapter.ClickItemListenner() {

            @Override
            public void onclick(LocalUserBean userBean) {
                //操作
                //重新走一遍
                nickname = userBean.getNickname();
                menuPopwindow.dismiss();
                /*SpUtils.selectUser(userBean.getNickname());*/
                //不用清除mianuser
                SpUtils.setMianUser(userBean.getNickname());
                //重新加载一次
                roleListAdapter.setLocalUserBeanList(userBeanDao.loadAll());
                roleListAdapter.notifyDataSetChanged();
                refresh();
            }
        });
        /**
         * 监听页面改变状态
         */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==1)
                {
                    //第二页
                    userFragment.loadUserData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        /**
         * 返回自己的战绩
         */
        btn_back_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Bundle bundle = new Bundle();
                bundle.putBoolean("mode",false);
                bundle.putString("nickname",SpUtils.getMainUser());
                Intent intent = new Intent(ListActivity.this,ListActivity.class);
                intent.putExtras(bundle);
                //清除所有相关的页面 创建新的在顶部
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
             MyApplication.removeStack();
              /* setVistormode(false);
               setNickname(SpUtils.getMainUser());
               refresh();*/
            }
        });

    }
    //刷新当前界面
    public void refresh() {
        //清除所有的视图状态
        clearViews();
        if (!vistormode) {
            //前往本地获取自己选择的用户
            userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
            //在本地获取
            localUserBean = searchuser(nickname);
            if (localUserBean != null) {
                updateView(localUserBean);
            }
            //请求网络加载用户信息(更新)
            btn_back_action.hide();
            requestUser();
        } else {
            //自己看别人的战绩
            btn_back_action.show();
            requestUser();
        }
        //提示fragment刷新
        guaideFragment.setNickname(nickname);
        guaideFragment.requestData(0, true);
        userFragment.setNickname(nickname);
        userFragment.loadUserData();


    }

    private void clearViews() {
        imgJumpUserhead.setImageBitmap(null);
        imgJumpUserhead.setImageDrawable(null);
        imgJumpUserhead.setBackgroundResource(R.drawable.fish_avator);
        imgHomeDuanwei.setBackgroundResource(0);
        tvHomeDuanwei.setText("加载中");
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.fish_avator);
        //高斯模糊化
        imgJumpBg.setImageBitmap(praseBitmap(bitmap));
        tvJumpGuaideHome.setText("加载中");
        tvJumpViotoryHome.setText("加载中");
        tvJumpNameHome.setText(nickname);
        guaideFragment.clearData();
        userFragment.clearData();

    }

    private Bitmap praseBitmap(Bitmap bitmap) {

        //创建一个缩小后的bitmap
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
        //创建将在ondraw中使用到的经过模糊处理后的bitmap
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        //创建RenderScript，ScriptIntrinsicBlur固定写法
        RenderScript rs = RenderScript.create(ListActivity.this);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //根据inputBitmap，outputBitmap分别分配内存
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        //设置模糊半径取值0-25之间，不同半径得到的模糊效果不同
        blurScript.setRadius(10);
        blurScript.setInput(tmpIn);
        blurScript.forEach(tmpOut);

        //得到最终的模糊bitmap
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    @Override
    protected void initdata() {
        //获取是否是观察者模式
        if (getIntent().getExtras() != null) {
            vistormode = getIntent().getExtras().getBoolean("mode");
            nickname = getIntent().getExtras().getString("nickname");
        }
        initfragment();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.setFragmentList(fragmentList, titles);
        myViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(myViewPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
        //这里分两种情况
        userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
        if (!vistormode) {
            //前往本地获取自己选择的用户
            //在本地获取
            localUserBean = searchuser(nickname );
            if (localUserBean != null) {
                updateView(localUserBean);
            }
            //请求网络加载用户信息(更新)
            requestUser();
        } else {
            //自己看别人的战绩
            requestUser();
        }
    }

    private void requestUser() {
        Request request = new Request.Builder()
                .url(Contacts.ROLE_URL + "?name=" + (nickname ))
                .build();
        MyApplication.getOkhttpUtils().sendRequest(request, YourRole.class);
    }



    @SuppressLint("ResourceType")
    private void initfragment() {
        Bundle bundle = new Bundle();
        bundle.putString("nickname",nickname);
        guaideFragment = new HeroGuaideFragment();
        userFragment = new UserFragment();
      /*  gayFragment = new GayFragment();*/
        guaideFragment.setArguments(bundle);
        userFragment.setArguments(bundle);
        titles = UiUtlis.getViewPagerTitle(R.array.title);
        fragmentList.add(guaideFragment);
        fragmentList.add(userFragment);
    }

    public LocalUserBean searchuser(String nickname) {
        LocalUserBean localUserBean = userBeanDao.queryBuilder().where(LocalUserBeanDao.Properties.Nickname.eq(nickname)).unique();
        return localUserBean;
    }


/*
    private void testhtml() {
      new Thread(new Runnable() {
          @Override
          public void run() {
              try {

                  // 网络加载HTML文档
                  Document doc = Jsoup.connect("https://voice.hupu.com/nba")
                          .timeout(5000) // 设置超时时间
                          .get(); // 使用GET方法访问URL
                  if (doc!=null)
                  {

                  }

              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }).start();

    }
    */


    /**
     * 配置侧滑菜单显示内容
     */
    private void configLeftmenu() {
        head_view = leftMenu.getHeaderView(0);
        img_bg = head_view.findViewById(R.id.img_background);
        img_avator = head_view.findViewById(R.id.img_avator);
        tv_jump_value = head_view.findViewById(R.id.tv_jump_value);
        tv_jump_viotroy = head_view.findViewById(R.id.tv_jump_viotory);
        tv_jump_name = head_view.findViewById(R.id.tv_jump_name);
        tv_jump_name.setText(localUserBean.getNickname());
        tv_jump_viotroy.setText("胜率:" + localUserBean.getViotory());
        tv_jump_value.setText("团分:" + localUserBean.getJumpvalue());
        //设置基本信息
        Glide.with(this).load(Contacts.ROLE_IMG + localUserBean.getIocnfile()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //去加载默认的头像

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();

                //设置图片
                img_bg.setImageBitmap(praseBitmap(bitmap));


                return false;

            }
        }).into(img_avator);


    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }
    //更新用户
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getYourRole(BindEvent eva) {
        if (eva.isSuccess()) {
            //保存
            if (eva.getYourRole() == null) {
                Log.e("更新数据失败", "da");
                return;
            }
            YourRole role = eva.getYourRole();
            if (role == null) {
                return;
            }
            if (!vistormode) {

                //Snackbar.make(toolBar, "欢迎召唤师," + role.getRole().getRoleName(), Snackbar.LENGTH_SHORT).show();
                LocalUserBean localUserBean = searchuser(role.getRole().getRoleName());
                String oldpower = null;
                boolean ishad = false;
                if (localUserBean == null) {
                    localUserBean = new LocalUserBean();

                } else {
                    localUserBean.setId(localUserBean.getId());
                    oldpower = localUserBean.getJumpvalue();
                    ishad = true;
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
                        if (TextUtils.isEmpty(type_icon)) {
                            type_icon = "0053.png";
                        }
                    }
                } else {
                    type_icon = "0053.png";
                }
                //计算一下胜率
                int wincount = role.getRole().getWinCount();
                int all = role.getRole().getMatchCount();
                int viotory = (int) (((double) wincount / (double) all) * 100);
                localUserBean.setIocnfile(type_icon);

                if (TextUtils.isEmpty(power) || power == null) {
                    localUserBean.setJumpvalue(oldpower);
                } else {
                    localUserBean.setJumpvalue(power);
                }
                localUserBean.setViotory(viotory + "%");
                localUserBean.setNickname(role.getRole().getRoleName());
                //观察模式不应该保存实体
              /*  if (ishad) {
                    userBeanDao.update(localUserBean);
                } else {
                    userBeanDao.save(localUserBean);
                }*/

                updateView(localUserBean);
                //以上更新用户信息
            } else {
                LocalUserBean localUserBean_user = new LocalUserBean();
                //处于观察者模式
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
                    if (TextUtils.isEmpty(type_icon)) {
                        type_icon = "0053.png";
                    }
                } else {
                    type_icon = "0053.png";
                }
                //计算一下胜率
                int wincount = role.getRole().getWinCount();
                int all = role.getRole().getMatchCount();
                int viotory = (int) (((double) wincount / (double) all) * 100);
                localUserBean_user.setIocnfile(type_icon);
                localUserBean_user.setJumpvalue(power);
                localUserBean_user.setViotory(viotory + "%");
                localUserBean_user.setNickname(role.getRole().getRoleName());
                updateView(localUserBean_user);
            }

        }
    }

    public void setJumpvalue(String value) {
        tvJumpGuaideHome.setText("团分:" + value);
    }

    /**
     * 更新视图,网络请求后的
     *
     * @param localUserBean
     */
    private void updateView(LocalUserBean localUserBean) {

        tvSearchNickname.setText(localUserBean.getNickname());
        tvSearchNickname.setTextColor(Color.WHITE);
        Glide.with(this).load(Contacts.ROLE_IMG + localUserBean.getIocnfile()).into(imgUserhead);
        tvJumpNameHome.setText(localUserBean.getNickname());
        tvJumpViotoryHome.setText("胜率:" + localUserBean.getViotory());
        if (localUserBean.getJumpvalue() == null || TextUtils.isEmpty(localUserBean.getJumpvalue()) || localUserBean.getJumpvalue().equals("null")) {
            tvJumpGuaideHome.setText("团分:加载中");
            imgHomeDuanwei.setVisibility(View.GONE);
            tvHomeDuanwei.setText("未获取段位");

        } else {
            tvJumpGuaideHome.setText("团分:" + localUserBean.getJumpvalue());
            int pwoer_win_adv = Integer.parseInt(localUserBean.getJumpvalue());
            if (pwoer_win_adv > 0 && pwoer_win_adv < 1000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.tong);
                tvHomeDuanwei.setText("青铜");
            }
            if (pwoer_win_adv >= 1000 && pwoer_win_adv < 2000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.baiying);
                tvHomeDuanwei.setText("白银");
            }
            if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.gold);
                tvHomeDuanwei.setText("黄金");
            }
            if (pwoer_win_adv >= 3000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.daemo);
                tvHomeDuanwei.setText("钻石");
            }
        }

        //设置基本信息
        Glide.with(this).load(Contacts.ROLE_IMG + localUserBean.getIocnfile()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //去加载默认的头像

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                //创建一个缩小后的bitmap
                Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
                //创建将在ondraw中使用到的经过模糊处理后的bitmap
                Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

                //创建RenderScript，ScriptIntrinsicBlur固定写法
                RenderScript rs = RenderScript.create(ListActivity.this);
                ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

                //根据inputBitmap，outputBitmap分别分配内存
                Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
                Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

                //设置模糊半径取值0-25之间，不同半径得到的模糊效果不同
                blurScript.setRadius(10);
                blurScript.setInput(tmpIn);
                blurScript.forEach(tmpOut);

                //得到最终的模糊bitmap
                tmpOut.copyTo(outputBitmap);
                //设置图片
                imgJumpBg.setImageBitmap(outputBitmap);
                //Palette用来更漂亮地展示配色
                Palette.from(bitmap)
                        .generate(new Palette.PaletteAsyncListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                List<Palette.Swatch> swatches = palette.getSwatches();
                                Palette.Swatch swatch = swatches.get(0);
                                int color = colorBurn(swatch.getRgb());
                                collseLayout.setContentScrimColor(color);
                                SpUtils.saveMainColor(color);
                                //暂时没有找到比较好的透明状态栏来适配这一套效果布局。直接替换掉StatusBar的颜色
                                getWindow().setStatusBarColor(color);
                                //修改fab图片前景颜色
                                //btn_back_action.setColorFilter(color);
                                btn_back_action.setBackgroundTintList(getColorStateListTest(color));
                            }
                        });

                return false;
            }
        }).into(imgJumpUserhead);

    }
    //获取方法，Fab颜色
    private ColorStateList getColorStateListTest(int color1) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int color = color1;
        int[] colors = new int[]{color, color, color, color};
        return new ColorStateList(states, colors);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void jumpValue(JumpValueEvnet eva) {
        String nickname_j = eva.getNickname();
        if (nickname_j.equals(nickname)) {
            setJumpvalue(eva.getValue());
            int pwoer_win_adv = Integer.parseInt(eva.getValue());
            if (pwoer_win_adv > 0 && pwoer_win_adv < 1000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.tong);
                tvHomeDuanwei.setText("青铜");
            }
            if (pwoer_win_adv >= 1000 && pwoer_win_adv < 2000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.baiying);
                tvHomeDuanwei.setText("白银");
            }
            if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.gold);
                tvHomeDuanwei.setText("黄金");
            }
            if (pwoer_win_adv >= 3000) {
                imgHomeDuanwei.setBackgroundResource(R.drawable.daemo);
                tvHomeDuanwei.setText("钻石");
            }
        }

    }


    /*
     * 颜色加深处理
     * */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }


}

