package com.example.evenalone.a300hero.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.example.evenalone.a300hero.wedgit.RadarView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 图表统计
 * Created by admin on 2019/6/17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HERO_ITEM = 0;
    private final int RADAR_ITEM = 1;
    private final int TALK_ITEM = 2;
    private final int LINE_ITEM = 3;
    private List<String> xAxisValue = new ArrayList<>();//X轴数据源
    private Map<String, String> usedHero = new HashMap<>();
    private Map<String, Integer> yourCard = new HashMap<>();
    private List<Integer> pwoerList=new ArrayList<>();

    public UserAdapter()
    {
        //描述值:团战 推塔 击杀 发育 贡献
        xAxisValue.add("团战");
        xAxisValue.add("击杀");
        xAxisValue.add("推塔");
        xAxisValue.add("发育");
        xAxisValue.add("贡献");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (getItemViewType(i) == HERO_ITEM) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.herouse_item, viewGroup, false);
            return new HeroItemViewholder(view);
        }
        else if (getItemViewType(i) == RADAR_ITEM) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radar_item, viewGroup, false);
            return new RadarViewholder(view);
        }else if (getItemViewType(i)==TALK_ITEM)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.talk_item,viewGroup,false);
            return new TalkViewHolder(view);
        }

        else if (getItemViewType(i) == LINE_ITEM) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lineuser_item, viewGroup, false);
            return new LineViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof HeroItemViewholder)
        {
            HeroItemViewholder heroItemViewholder = (HeroItemViewholder) viewHolder;
            if (usedHero!=null&&usedHero.size()>=3)
            {
                List<String> heroname = new ArrayList<>();
                List<String> heroicon = new ArrayList<>();
                for (Map.Entry<String,String> entry:usedHero.entrySet())
                {
                    heroicon.add(entry.getValue());
                    heroname.add(entry.getKey());
                }
                Glide.with(MyApplication.getContext()).load(Contacts.IMG+heroicon.get(0)).into(heroItemViewholder.imgHero1);
                Glide.with(MyApplication.getContext()).load(Contacts.IMG+heroicon.get(1)).into(heroItemViewholder.imgHero2);
                Glide.with(MyApplication.getContext()).load(Contacts.IMG+heroicon.get(2)).into(heroItemViewholder.imgHero3);

            }
        }
        if (viewHolder instanceof RadarViewholder)
        {
            RadarViewholder radarViewholder = (RadarViewholder) viewHolder;


            List<Double> floatList = new LinkedList<>();
            if (yourCard!=null&&yourCard.size()>0)
            {
                boolean ismax = false;
                for (Map.Entry<String,Integer> entry:yourCard.entrySet())
                {
                    if (entry.getValue()>=50)
                    {
                        //满值用100计算
                        ismax = true;
                    }
                    if (entry.getKey().equals("团战"))
                    {
                        floatList.add(Double.valueOf(entry.getValue()));
                    }
                    if (entry.getKey().equals("击杀"))
                    {
                        floatList.add(Double.valueOf(entry.getValue()));
                    }
                    if (entry.getKey().equals("推塔"))
                    {
                        floatList.add(Double.valueOf(entry.getValue()));
                    }
                    if (entry.getKey().equals("发育"))
                    {
                        floatList.add(Double.valueOf(entry.getValue()));

                    }
                    if (entry.getKey().equals("贡献"))
                    {
                        floatList.add(Double.valueOf(entry.getValue()));
                    }
                }
                if (ismax)
                {
                    //有一项超过了100,其余也不会差 kda的参数都是一发动全身
                    radarViewholder.radarItem.setMaxValue(100);
                }
                else
                {
                    radarViewholder.radarItem.setMaxValue(50);
                }
                radarViewholder.radarItem.setData(floatList);
                Paint valuePaint = new Paint();
                valuePaint.setColor(SpUtils.getMainColor());
                valuePaint.setAntiAlias(true);
                valuePaint.setStyle(Paint.Style.FILL);
                radarViewholder.radarItem.setValuePaint(valuePaint);
            }
        }
        if (viewHolder instanceof TalkViewHolder)
        {
            TalkViewHolder talkViewHolder = (TalkViewHolder) viewHolder;
            int tuanindex=0;
            int killindex=0;
            int tower=0;
            int gongxian=0;
            int money=0;
            if (yourCard!=null)
            {
                for (Map.Entry<String,Integer> entry:yourCard.entrySet())
                {

                    if (entry.getKey().equals("团战"))
                    {
                        tuanindex = entry.getValue();
                    }
                    if (entry.getKey().equals("击杀"))
                    {
                        killindex = entry.getValue();
                    }
                    if (entry.getKey().equals("推塔"))
                    {
                        tower = entry.getValue();
                    }
                    if (entry.getKey().equals("发育"))
                    {
                        money = entry.getValue();
                    }
                    if (entry.getKey().equals("贡献"))
                    {
                        gongxian = entry.getValue();
                    }
                }
                talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk8)+"”");
                //杀人鬼
                //条件是 比例90%及以上
                //这一个只按人头算
                if (killindex<50&&killindex>40)
                {
                    //按比例50
                    talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk1)+"”");
                }
                if (killindex>50&&killindex>=85)
                {
                    //按比例100
                    talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk1)+"”");
                }
                //神の队友
                //计算贡献
                //将会出现击杀厉害的角色,他的击杀数高,这里要关掉这个阀值,同时还不能于推塔冲突
                if (gongxian<50&&gongxian>40)
                {
                    //排除击杀上位
                    if (killindex<50&&killindex<40)
                    {
                        //排除推塔上位
                        if (tower<50&&tower<40)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk6)+"”");
                        }
                    }
                    //排除击杀上位
                    if (killindex>50&&killindex<85)
                    {
                        //排除推塔上位
                        if (tower>50&&tower<85)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk6)+"”");
                        }
                    }
                }
                if (gongxian>50&&gongxian>85)
                {
                    //排除击杀上位
                    if (killindex<50&&killindex<40)
                    {
                        //排除推塔上位
                        if (tower<50&&tower<40)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk6)+"”");
                        }
                    }
                    //排除击杀上位
                    if (killindex>50&&killindex<85)
                    {
                        //排除推塔上位
                        if (tower>50&&tower<85)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk6)+"”");
                        }
                    }
                }
                //单机王
                if (money<50&&money>40)
                {
                    if (killindex<30&&tower<10&&gongxian<10)
                    {
                        talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk2)+"”");
                    }

                }
                if (money>50&&money>85)
                {
                    if (killindex<50&&tower<10&&gongxian<10)
                    {
                        talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk2)+"”");
                    }

                }
                //划水队友
                if (money<20&&killindex<20&&gongxian<20&&tower<20&&gongxian<20)
                {
                    talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk3)+"”");
                }
                //无情の推塔机器
                if (tower<50&&tower>40)
                {

                    //排除击杀上位
                    if (killindex<50&&killindex<40)
                    {
                        //排除团战上位
                        if (tuanindex<50&&tuanindex<40)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk4)+"”");
                        }
                    }
                    //排除击杀上位
                    if (killindex>50&&killindex<85)
                    {
                        //排除推塔上位
                        if (tuanindex>50&&tuanindex<85)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk4)+"”");
                        }
                    }

                }
                if (tower>50&&tower>85)
                {

                    //排除击杀上位
                    if (killindex<50&&killindex<40)
                    {
                        //排除团战上位
                        if (tuanindex<50&&tuanindex<40)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk4)+"”");
                        }
                    }
                    //排除击杀上位
                    if (killindex>50&&killindex<85)
                    {
                        //排除推塔上位
                        if (tuanindex>50&&tuanindex<85)
                        {
                            talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk4)+"”");
                        }
                    }

                }
            }
            else
            {
                talkViewHolder.tvTalk.setText("“"+MyApplication.getContext().getString(R.string.talk7)+"”");
            }

        }
        //团分变化统计图
        if (viewHolder instanceof LineViewHolder) {
            //只统计最近30局团分变动,最少10局
            LineViewHolder lineViewHolder = (LineViewHolder) viewHolder;
            lineViewHolder.lineChartItem.setNoDataText("数据不足");
            LineData lineData = new LineData();
            List<Entry> entries = new ArrayList<>();
            for (int i1 = 0; i1 < pwoerList.size(); i1++)
            {
                entries.add(new Entry(i1, pwoerList.get(i1)));
            }

            LineDataSet dataSet = new LineDataSet(entries,"团分走势图"); // add entries to dataset
            lineData.addDataSet(dataSet);
            lineViewHolder.lineChartItem.setData(lineData);
            lineViewHolder.lineChartItem.invalidate();
            //设置样式
            YAxis rightAxis =  lineViewHolder.lineChartItem.getAxisRight();

            //设置图表右边的y轴禁用
            rightAxis.setEnabled(false);
            YAxis leftAxis =  lineViewHolder.lineChartItem.getAxisLeft();
            //设置图表左边的y轴禁用
            leftAxis.setEnabled(false);
            //设置x轴
            XAxis xAxis =  lineViewHolder.lineChartItem.getXAxis();
            xAxis.setTextColor(Color.parseColor("#333333"));
            xAxis.setTextSize(11f);
            xAxis.setAxisMinimum(0f);
            xAxis.setDrawAxisLine(true);//是否绘制轴线
            xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
            xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
            xAxis.setGranularity(1f);//禁止放大后x轴标签重绘


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
        {
            return HERO_ITEM;
        }else if (position==1)
        {
            return RADAR_ITEM;
        }else if (position==2)
        {
            return TALK_ITEM;
        }
        else if (position==3)
        {
            return LINE_ITEM;
        }
        return 0;

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setUsedHero(Map<String, String> usedHero) {
        this.usedHero = usedHero;
        notifyDataSetChanged();
    }

    public void setYourCard(Map<String, Integer> yourCard) {
        this.yourCard = yourCard;
        notifyDataSetChanged();

    }

    public void setPwoerList(List<Integer> pwoerList) {
        this.pwoerList = pwoerList;
        notifyDataSetChanged();
    }

    class HeroItemViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_hero_1)
        CircleImageView imgHero1;
        @BindView(R.id.img_hero_2)
        CircleImageView imgHero2;
        @BindView(R.id.img_hero_3)
        CircleImageView imgHero3;
        @BindView(R.id.li_hero_item)
        LinearLayout liHeroItem;

        public HeroItemViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class RadarViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.radar_item)
        RadarView radarItem;

        public RadarViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class LineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.line_chart_item)
        LineChart lineChartItem;
        public LineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TalkViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_talk)
        TextView tvTalk;
        public TalkViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
