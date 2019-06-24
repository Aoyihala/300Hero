package com.example.evenalone.a300hero.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.evenalone.a300hero.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

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
    private final int LINE_ITEM = 2;
    private List<String> xAxisValue = new ArrayList<>();//X轴数据源

    public UserAdapter()
    {
        //描述值:团战 推塔 击杀 发育 贡献
        xAxisValue.add("团战");
        xAxisValue.add("推塔");
        xAxisValue.add("击杀");
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

        }
        if (viewHolder instanceof RadarViewholder)
        {
            RadarViewholder radarViewholder = (RadarViewholder) viewHolder;
            radarViewholder.radarItem.getDescription().setEnabled(false);
            XAxis xAxis =  radarViewholder.radarItem.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(10);
            xAxis.setLabelCount(xAxisValue.size());
            xAxis.setCenterAxisLabels(true);//设置标签居中
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));
            List<RadarEntry> radarEntries = new ArrayList<>();
            radarEntries.add(new RadarEntry(80));
            radarEntries.add(new RadarEntry(85));
            radarEntries.add(new RadarEntry(90));
            radarEntries.add(new RadarEntry(70));
            radarEntries.add(new RadarEntry(95));
            RadarDataSet radarDataSet = new RadarDataSet(radarEntries, "数据一");
            // 实心填充区域颜色
            radarDataSet.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            // 是否实心填充区域
            radarDataSet.setDrawFilled(true);
            RadarData radarData = new RadarData(radarDataSet);
            radarViewholder.radarItem.setData(radarData);
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
        RadarChart radarItem;

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
