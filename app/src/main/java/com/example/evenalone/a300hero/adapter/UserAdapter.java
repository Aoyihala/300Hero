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
