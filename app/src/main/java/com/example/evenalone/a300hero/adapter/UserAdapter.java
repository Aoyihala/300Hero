package com.example.evenalone.a300hero.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rorbin.q.radarview.RadarView;

/**
 * 图表统计
 * Created by admin on 2019/6/17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HERO_ITEM = 0;
    private final int RADAR_ITEM = 1;
    private final int LINE_ITEM = 2;
    private List<String> xAxisValue = new ArrayList<>();//X轴数据源
    private Map<String, String> usedHero = new HashMap<>();
    private Map<String, Integer> yourCard = new HashMap<>();

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
        } else if (getItemViewType(i) == RADAR_ITEM) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radar_item, viewGroup, false);
            return new RadarViewholder(view);
        } else if (getItemViewType(i) == LINE_ITEM) {
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

            int tuanindex;
            int killindex;
            int tower;
            int gongxian;
            int money;
            radarViewholder.radarItem.setVertexText(xAxisValue);

            List<Float> floatList = new ArrayList<>();
            if (yourCard!=null&&yourCard.size()>0)
            {
                for (Map.Entry<String,Integer> entry:yourCard.entrySet())
                {
                    if (entry.getKey().equals("团战"))
                    {
                        floatList.add(Float.valueOf(entry.getValue()));
                    }
                    if (entry.getKey().equals("击杀"))
                    {
                        floatList.add(Float.valueOf(entry.getValue()));
                    }
                    if (entry.getKey().equals("推塔"))
                    {
                        floatList.add(Float.valueOf(entry.getValue()));
                    }
                    if (entry.getKey().equals("发育"))
                    {
                        floatList.add(Float.valueOf(entry.getValue()));

                    }
                    if (entry.getKey().equals("贡献"))
                    {
                        floatList.add(Float.valueOf(entry.getValue()));
                    }
                }
                rorbin.q.radarview.RadarData radarData = new rorbin.q.radarview.RadarData(floatList,"平常");
                radarData.setColor(UiUtlis.getColor(R.color.Yellow));
                radarViewholder.radarItem.addData(radarData);
                radarViewholder.radarItem.animeValue(2000);
            }



        }
        if (viewHolder instanceof LineViewHolder)
        {

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
            return LINE_ITEM;
        }
        return 0;

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setUsedHero(Map<String, String> usedHero) {
        this.usedHero = usedHero;
        notifyDataSetChanged();
    }

    public void setYourCard(Map<String, Integer> yourCard) {
        this.yourCard = yourCard;
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
}
