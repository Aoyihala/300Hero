package com.example.evenalone.a300hero.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.UiUtlis;
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
    private int team;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PowerValue) {
            PowerValue value = (PowerValue) viewHolder;
            if (maxPower > 0 && winData != null) {
               /* Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + winData.get(i).getHero().getIconFile()).into(value.imgPowerAvator);*/
                try {
                    value.imgPowerAvator.setTag(null);
                    MyApplication.getImageCenter().setPic(value.imgPowerAvator,winData.get(i).getHero().getName(),Contacts.IMG+winData.get(i).getHero().getIconFile());
                } catch (Exception e) {
                    value.imgPowerAvator.setTag(null);
                    Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + winData.get(i).getHero().getIconFile()).into(value.imgPowerAvator);
                }
                double pecent = (((double) winData.get(i).getELO() / (double) maxPower));
                value.progressPowervalue.setSmoothPercent((float) pecent, 2000);
                value.tvPowerDes.setText(winData.get(i).getRoleName() );
                value.tvPowerDesPower.setText(winData.get(i).getELO() + "/" + maxPower);
                value.tvPowerDesPower.invalidate();
                if (winData.get(i).getTeamResult() == 0) {
                    value.progressPowervalue.setFillColor(UiUtlis.getColor(R.color.blue));
                } else {
                    value.progressPowervalue.setFillColor(UiUtlis.getColor(R.color.Red));
                }
            }
        }
        if (viewHolder instanceof PowerValueRight) {
            PowerValueRight valueRight = (PowerValueRight) viewHolder;
            if (maxPower > 0 && loseData != null) {
                /*Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + loseData.get(i).getHero().getIconFile()).into(valueRight.imgPowerAvator2);*/
                try {
                    valueRight.imgPowerAvator2.setTag(null);
                    MyApplication.getImageCenter().setPic(valueRight.imgPowerAvator2,loseData.get(i).getHero().getName(),Contacts.IMG+loseData.get(i).getHero().getIconFile());
                } catch (Exception e) {
                    valueRight.imgPowerAvator2.setTag(null);
                    Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + loseData.get(i).getHero().getIconFile()).into(valueRight.imgPowerAvator2);
                }
                double pecent = (((double) loseData.get(i).getELO() / (double) maxPower));
                valueRight.progressPowervalue2.setSmoothPercent((float) pecent, 2000);
                valueRight.tvPowerDes2.setText(loseData.get(i).getRoleName());
                valueRight.tvPowerDesPower2.setText(  maxPower+"/"+loseData.get(i).getELO());
                valueRight.tvPowerDesPower2.invalidate();
                if (loseData.get(i).getTeamResult() == 0) {
                    valueRight.progressPowervalue2.setFillColor(UiUtlis.getColor(R.color.blue));
                } else {
                    valueRight.progressPowervalue2.setFillColor(UiUtlis.getColor(R.color.Red));
                }


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

    public void setMaxPower(int maxPower, int i) {
        this.maxPower = maxPower;
        team = i;
    }

    public void setWinData(List<GameInfo.MatchBean.WinSideBean> winData) {
        this.winData = winData;
    }

    public void setLoseData(List<GameInfo.MatchBean.LoseSideBean> loseData) {
        this.loseData = loseData;
    }

    class PowerValueRight extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_power_des2)
        TextView tvPowerDes2;
        @BindView(R.id.tv_power_des_power2)
        TextView tvPowerDesPower2;
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
        @BindView(R.id.tv_power_des)
        TextView tvPowerDes;
        @BindView(R.id.tv_power_des_power)
        TextView tvPowerDesPower;
        @BindView(R.id.progress_powervalue)
        MagicProgressBar progressPowervalue;

        public PowerValue(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
