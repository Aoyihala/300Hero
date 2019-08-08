package com.example.evenalone.a300hero.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.utils.Contacts;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2019/8/8.
 */

public class PowerValueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int LEFT = 203;
    private int RIGHT = 204;
    private int maxPower;
    private List<GameInfo.MatchBean.WinSideBean> winData;
    private List<GameInfo.MatchBean.LoseSideBean> loseData;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (getItemViewType(i) == LEFT) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.power_item, viewGroup, false);
            return new PowerValue(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.power_item2, viewGroup, false);
            return new PowerValueRight(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PowerValue) {
            PowerValue value = (PowerValue) viewHolder;
            if (maxPower > 0 && winData != null) {
                Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + winData.get(i).getHero().getIconFile()).into(value.imgPowerAvator);
                int pecent = (int) (((double) winData.get(i).getELO() / (double) maxPower) * 100);
                value.progressPowervalue.setPercent(20);
            }
        }
        if (viewHolder instanceof PowerValueRight)
        {
            PowerValueRight valueRight = (PowerValueRight) viewHolder;
            if (maxPower > 0 && loseData != null) {
                Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + loseData.get(i).getHero().getIconFile()).into(valueRight.imgPowerAvator2);
                int pecent = (int) (((double) loseData.get(i).getELO() / (double) maxPower) * 100);
                valueRight.progressPowervalue2.setPercent(20);


            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (winData != null && winData.size() > 0) {
            //左边
            return LEFT;
        } else {
            //右边
            return RIGHT;
        }
    }

    @Override
    public int getItemCount() {
        if (winData != null && winData.size() > 0) {
            return winData.size();
        } else if (loseData != null && loseData.size() > 0) {
            return loseData.size();
        }
        return 0;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public void setWinData(List<GameInfo.MatchBean.WinSideBean> winData) {
        this.winData = winData;
    }

    public void setLoseData(List<GameInfo.MatchBean.LoseSideBean> loseData) {
        this.loseData = loseData;
    }

    class PowerValueRight extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_powervalue2)
        MagicProgressBar progressPowervalue2;
        @BindView(R.id.img_power_avator2)
        CircleImageView imgPowerAvator2;
        public PowerValueRight(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PowerValue extends RecyclerView.ViewHolder {
        @BindView(R.id.img_power_avator)
        CircleImageView imgPowerAvator;
        @BindView(R.id.progress_powervalue)
        MagicProgressBar progressPowervalue;

        public PowerValue(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
