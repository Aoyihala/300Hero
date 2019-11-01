package com.example.evenalone.a300hero.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.base.BaseActivity;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.event.ListInfoEvent;
import com.example.evenalone.a300hero.ui.body.BodyData;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class TipoffActivity extends BaseActivity {


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
    @BindView(R.id.img_tipoff_bg)
    ImageView imgTipoffBg;
    @BindView(R.id.tv_tipoff_power)
    TextView tvTipoffPower;
    @BindView(R.id.tv_tipoff_percent)
    TextView tvTipoffPercent;
    @BindView(R.id.img_tipoff_position)
    ImageView imgTipoffPosition;
    @BindView(R.id.tv_tipoff_position)
    TextView tvTipoffPosition;
    @BindView(R.id.tv_tipoff_number)
    TextView tvTipoffNumber;
    @BindView(R.id.spinner_reason)
    NiceSpinner spinnerReason;
    @BindView(R.id.btn_tipoff)
    CardView btnTipoff;
    @BindView(R.id.img_tipoff_avator)
    CircleImageView imgTipoffAvator;
    @BindView(R.id.tv_tipoff_name)
    TextView tvTipoffName;
    @BindView(R.id.tv_tipoff_gotoinfo)
    TextView tvTipoffGotoinfo;
    @BindView(R.id.line_chart_view)
    LineChart lineChartView;
    @BindView(R.id.progress_tipoff)
    ProgressBar progressTipoff;
    @BindView(R.id.tv_tipoff_connectstate)
    TextView tvTipoffConnectstate;
    private BodyData bodyData;
    private GameInfo.MatchBean.LoseSideBean loseSideBean;
    private GameInfo.MatchBean.WinSideBean winSideBean;
    private boolean win;
    private List<String> items = new ArrayList<>();
    private List<HeroGuide.ListBean> listBeans = new ArrayList<>();
    private List<GameInfo> gameInfoList = new ArrayList<>();
    private List<Entry> entrie_power = new ArrayList<>();
    private List<Entry> entrie_dead = new ArrayList<>();
    private List<Entry> entrie_kill = new ArrayList<>();
    private List<Integer> powerlist = new ArrayList<>();
    private List<Integer> deadlist = new ArrayList<>();
    private List<Integer> killlist = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<GameInfo.MatchBean.WinSideBean> winSideBeanList = new ArrayList<>();
    private List<GameInfo.MatchBean.LoseSideBean> loseSideBeans = new ArrayList<>();
    private int size = 0;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1001) {
                if (listBeans != null && listBeans.size() > 0) {
                    if (size < listBeans.size() - 1) {
                        size = size + 1;
                        Log.e("当前正在采集", "" + size);
                        tvTipoffConnectstate.setText("采集中:"+size+"/"+listBeans.size()+"个");
                        startRequestGameInfo(size);
                    } else {
                        //结束
                        Log.e("状态", "采集所有战绩结束");
                        Toast.makeText(MyApplication.getContext(), "可以进行举报操作", Toast.LENGTH_SHORT).show();
                        //拆分数据
                        progressTipoff.setVisibility(View.GONE);
                        tvTipoffConnectstate.setVisibility(View.GONE);
                        parselineData();
                    }
                }
            }
            return true;
        }
    });

    //处理拆分被举报人的数据
    private void parselineData() {
        if (gameInfoList != null) {
            //获取昵称
            String nickname = tvTipoffName.getText().toString().trim();
            //统计list里自己所有的信息

            for (int i = 0; i < gameInfoList.size(); i++) {
                winSideBeanList.addAll(gameInfoList.get(i).getMatch().getWinSide());
                loseSideBeans.addAll(gameInfoList.get(i).getMatch().getLoseSide());
            }
            eachlose(loseSideBeans, nickname);
            eachwin(winSideBeanList, nickname);

            initlinecard();

        }
    }

    private void eachlose(List<GameInfo.MatchBean.LoseSideBean> loseSide, String nickname) {
        for (GameInfo.MatchBean.LoseSideBean loseSideBean : loseSide) {
            if (loseSideBean.getRoleName().contains(nickname)) {
                deadlist.add(loseSideBean.getDeathCount());
                killlist.add(loseSideBean.getKillCount());
                powerlist.add(loseSideBean.getAssistCount());

            }
        }
    }

    private void eachwin(List<GameInfo.MatchBean.WinSideBean> winSide, String nickname) {
        for (GameInfo.MatchBean.WinSideBean winSideBean : winSide) {
            if (winSideBean.getRoleName().contains(nickname)) {
                deadlist.add(winSideBean.getDeathCount());
                killlist.add(winSideBean.getKillCount());
                powerlist.add(winSideBean.getAssistCount());
            }
        }
    }

    @Override
    protected void initview() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setBackBtn(imgBtn);
        tvTopTitle.setText("举报");
        spinnerReason.attachDataSource(items);
        initcard();
        getUserData();
        btnTipoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initlinecard() {
        lineChartView.setNoDataText("获取数据中");
        LineData lineData_entries = new LineData();
        for (int i = 0; i < powerlist.size(); i++) {
            entrie_power.add(new Entry(i, powerlist.get(i)));
        }
        for (int i = 0; i < killlist.size(); i++) {
            entrie_kill.add(new Entry(i, killlist.get(i)));
        }
        for (int i = 0; i < deadlist.size(); i++) {
            entrie_dead.add(new Entry(i, deadlist.get(i)));
        }
        //三组数据
        LineDataSet data_dead = new LineDataSet(entrie_dead, "死亡(送)趋势");
        LineDataSet data_power = new LineDataSet(entrie_power, "助攻趋势");
        LineDataSet data_kill = new LineDataSet(entrie_kill, "杀敌趋势");
        data_dead.setCircleColor(UiUtlis.getColor(R.color.black));
        data_dead.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        data_dead.setColor(UiUtlis.getColor(R.color.black));
        data_power.setCircleColor(UiUtlis.getColor(R.color.blue));
        data_power.setColor(UiUtlis.getColor(R.color.blue));
        data_power.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        data_kill.setColor(UiUtlis.getColor(R.color.Red));
        data_kill.setCircleColor(UiUtlis.getColor(R.color.Red));
        data_kill.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineData_entries.addDataSet(data_dead);
        lineData_entries.addDataSet(data_kill);
        lineData_entries.addDataSet(data_power);
        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        lineChartView.setTouchEnabled(true);//禁止手势
        lineChartView.setDragEnabled(true);//平移允许
        lineChartView.setDoubleTapToZoomEnabled(true);//双击放大静止
        YAxis rightAxis = lineChartView.getAxisRight();

        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = lineChartView.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(false);
        //设置x轴
        XAxis xAxis = lineChartView.getXAxis();
        xAxis.setTextColor(SpUtils.getMainColor());
        xAxis.setTextSize(10f);
        xAxis.setAxisMinimum(0f);

        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值

        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线

        xAxis.setAxisLineColor(SpUtils.getMainColor());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
        /*  xAxis.setValueFormatter(new IndexAxisValueFormatter(x_str));*/
        lineChartView.setDescription(description);
        lineChartView.setData(lineData_entries);
        lineChartView.invalidate();
        lineChartView.setVisibility(View.VISIBLE);
    }

    private void initcard() {
        if (win) {
            updateview(winSideBean);
        } else {
            updateview(loseSideBean);
        }
    }

    //更新视图
    private void updateview(GameInfo.MatchBean.WinSideBean winSideBean) {
        Glide.with(this).load(Contacts.IMG + winSideBean.getHero().getIconFile())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //高斯模糊
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                        imgTipoffBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgTipoffBg.setImageBitmap(praseBitmap(bitmapDrawable.getBitmap()));
                        return false;
                    }
                })
                .into(imgTipoffAvator);
        tvTipoffName.setText(winSideBean.getRoleName());
        int percent = (int) (((double) winSideBean.getWinCount() / (double) winSideBean.getMatchCount()) * 100);
        tvTipoffPercent.setText("胜率:" + percent + "%");
        tvTipoffPower.setText("团分:" + winSideBean.getELO());
        updateview(winSideBean.getELO());
    }


    private void updateview(int pwoer_win_adv) {


        if (pwoer_win_adv > 0 && pwoer_win_adv < 1500) {
            imgTipoffPosition.setBackgroundResource(R.drawable.little_fish);
            tvTipoffPosition.setText(getString(R.string.liitle_fish));
        }
        if (pwoer_win_adv >= 1500 && pwoer_win_adv < 3000) {
            imgTipoffPosition.setBackgroundResource(R.drawable.little_fish_bigger);
            tvTipoffPosition.setText(getString(R.string.liitle_fish_bigger));
        }
        if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
            imgTipoffPosition.setBackgroundResource(R.drawable.fishes);
            tvTipoffPosition.setText(getString(R.string.big_fissh));
        }
        if (pwoer_win_adv >= 3000) {
            imgTipoffPosition.setBackgroundResource(R.drawable.shark);
            tvTipoffPosition.setText(getString(R.string.shark));
        }
        if (pwoer_win_adv >= 4000) {
            imgTipoffPosition.setBackgroundResource(R.drawable.jiucai);
            tvTipoffPosition.setText(getString(R.string.jiucai));
        }
    }

    //模糊
    private Bitmap praseBitmap(Bitmap bitmap) {

        //创建一个缩小后的bitmap
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
        //创建将在ondraw中使用到的经过模糊处理后的bitmap
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        //创建RenderScript
        RenderScript rs = RenderScript.create(this);
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

    private void updateview(GameInfo.MatchBean.LoseSideBean loseSideBean) {
        Glide.with(this).load(Contacts.IMG + loseSideBean.getHero().getIconFile())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //高斯模糊
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                        imgTipoffBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgTipoffBg.setImageBitmap(praseBitmap(bitmapDrawable.getBitmap()));
                        return false;
                    }
                })
                .into(imgTipoffAvator);
        tvTipoffName.setText(loseSideBean.getRoleName());
        int percent = (int) (((double) loseSideBean.getWinCount() / (double) loseSideBean.getMatchCount()) * 100);
        tvTipoffPercent.setText("胜率:" + percent + "%");
        tvTipoffPower.setText("团分:" + loseSideBean.getELO());
        updateview(loseSideBean.getELO());

    }

    @Override
    protected void initdata() {
        items.add("挂机");
        items.add("送头");
        win = getIntent().getBooleanExtra("win", false);
        if (!win) {
            loseSideBean = (GameInfo.MatchBean.LoseSideBean) getIntent().getSerializableExtra("data");
        } else {
            winSideBean = (GameInfo.MatchBean.WinSideBean) getIntent().getSerializableExtra("data");
        }

    }

    private void getUserData() {
        Request request = new Request.Builder()
                .url(Contacts.LIST_URL + "?name=" + tvTipoffName.getText() + "&index=" + 0)
                .build();
        MyApplication.getOkhttpUtils().sendRequest(request, HeroGuide.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tipoff;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gettipoffUserData(ListInfoEvent eva) {
        if (eva.isSuccess()) {
            if (eva.getGuide() == null) {
                Snackbar.make(toolBar, "获取被举报人战局失败,请尝试重新到该界面", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (!eva.getGuide().getResult().equals("OK")) {
                Log.e("访问战绩列表失败", eva.getErroMsg() + " ");
                Snackbar.make(toolBar, "今日访问很频繁，请避开高峰期比如周末或者尝试到设置界面打开代理模式", Snackbar.LENGTH_SHORT).show();
                return;
            }
            listBeans = eva.getGuide().getList();
            //开始请求
            if (listBeans != null && listBeans.size() > 0) {
                startRequestGameInfo(0);
            }

        }
    }

    private void startRequestGameInfo(int position) {
        x.http().get(new RequestParams(Contacts.MATCH_GAME + "?id=" + listBeans.get(position).getMatchID()), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    GameInfo gameInfo = new Gson().fromJson(result, GameInfo.class);
                    gameInfoList.add(gameInfo);
                    handler.sendEmptyMessage(1001);
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

            }
        });
    }
}
