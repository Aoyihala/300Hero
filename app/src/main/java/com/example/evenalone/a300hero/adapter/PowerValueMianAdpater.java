package com.example.evenalone.a300hero.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.example.evenalone.a300hero.wedgit.NoScrollLinearLayout;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * powerValue主要适配器
 * Created by admin on 2019/8/8.
 */

public class PowerValueMianAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int POWER_HEAD = 0;
    private int POWER_BODY = 1;
    private GameInfo data = new GameInfo();
    private PowerValueAdapter winadpater;
    private PowerValueAdapter loseadapter;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (getItemViewType(i) == POWER_HEAD) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.power_head, viewGroup, false);
            return new PowerHeadViewHodler(view);
        } else if (getItemViewType(i) == POWER_BODY) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.power_body, viewGroup, false);
            return new PowerBodyViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (data==null||data.getMatch()==null||data.getMatch().getLoseSide()==null||data.getMatch().getWinSide()==null)
        {
            return;
        }
        List<GameInfo.MatchBean.WinSideBean> winSideBeanList = data.getMatch().getWinSide();
        List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = data.getMatch().getLoseSide();
        if (viewHolder instanceof PowerHeadViewHodler)
        {
            PowerHeadViewHodler headViewHodler = (PowerHeadViewHodler) viewHolder;
            if (winSideBeanList.size()>0&&loseSideBeanList.size()>0)
            {
                int winteam = winSideBeanList.get(0).getTeamResult();
                if (winteam==0)
                {
                    headViewHodler.card1.setCardBackgroundColor(UiUtlis.getColor(R.color.blue));
                    headViewHodler.card2.setCardBackgroundColor(UiUtlis.getColor(R.color.Red));
                    headViewHodler.text1.setText("输");
                    headViewHodler.text2.setText("赢");
                }
                else
                {
                    headViewHodler.card1.setCardBackgroundColor(UiUtlis.getColor(R.color.Red));
                    headViewHodler.card2.setCardBackgroundColor(UiUtlis.getColor(R.color.blue));
                    headViewHodler.text1.setText("赢");
                    headViewHodler.text2.setText("输");
                }
            }
        }
        if (viewHolder instanceof PowerBodyViewHolder)
        {
            if (data!=null)
            {
                PowerBodyViewHolder bodyViewHolder = (PowerBodyViewHolder) viewHolder;

                //获取一个最大团分作为max
                if (winSideBeanList.size()>0&&loseSideBeanList.size()>0)
                {
                    ListSortWin(winSideBeanList);
                    ListSortLose(loseSideBeanList);
                    //获取最大值
                    int winpower = winSideBeanList.get(0).getELO();
                    int losepower = loseSideBeanList.get(0).getELO();
                    int maxpower = winpower>losepower?winpower:losepower;
                    winadpater = new PowerValueAdapter();
                    loseadapter = new PowerValueAdapter();
                    int winteam = winSideBeanList.get(0).getTeamResult();
                    if (winteam==0)
                    {
                        winadpater.setMaxPower(maxpower,0);
                        loseadapter.setMaxPower(maxpower,1);
                    }
                    else
                    {
                        winadpater.setMaxPower(maxpower,1);
                        loseadapter.setMaxPower(maxpower,0);
                    }


                    winadpater.setWinData(winSideBeanList);
                    loseadapter.setLoseData(loseSideBeanList);
                    bodyViewHolder.recycerPowerWinsaide.setNestedScrollingEnabled(false);
                    bodyViewHolder.recycerPowerWinsaide.setLayoutManager(new NoScrollLinearLayout(viewHolder
                    .itemView.getContext()));
                    bodyViewHolder.recycerPowerLosesaide.setNestedScrollingEnabled(false);
                    bodyViewHolder.recycerPowerLosesaide.setLayoutManager(new NoScrollLinearLayout(viewHolder.itemView.getContext(), LinearLayoutManager.VERTICAL,true));
                    bodyViewHolder.recycerPowerWinsaide.setAdapter(winadpater);
                    bodyViewHolder.recycerPowerLosesaide.setAdapter(loseadapter);
                    winadpater.notifyDataSetChanged();
                    loseadapter.notifyDataSetChanged();
                }
            }
        }

    }

    //排序
    private static void ListSortWin(List<GameInfo.MatchBean.WinSideBean> list) {
        Collections.sort(list, new Comparator<GameInfo.MatchBean.WinSideBean>() {
            @Override
            public int compare(GameInfo.MatchBean.WinSideBean o1, GameInfo.MatchBean.WinSideBean o2) {
                try {

                    if (o1.getELO() < o2.getELO()) {
                        return 1;
                    } else if (o1.getELO() > o2.getELO()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    //排序
    private static void ListSortLose(List<GameInfo.MatchBean.LoseSideBean> list) {
        Collections.sort(list, new Comparator<GameInfo.MatchBean.LoseSideBean>() {
            @Override
            public int compare(GameInfo.MatchBean.LoseSideBean o1, GameInfo.MatchBean.LoseSideBean o2) {
                try {

                    if (o1.getELO() < o2.getELO()) {
                        return 1;
                    } else if (o1.getELO() > o2.getELO()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void setData(GameInfo data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void updateAnimate() {
        //更新
        if (loseadapter!=null&&winadpater!=null)
        {
            loseadapter.notifyDataSetChanged();
            winadpater.notifyDataSetChanged();
        }
    }

    class PowerHeadViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.text_1)
        TextView text1;
        @BindView(R.id.card_1)
        CardView card1;
        @BindView(R.id.text_2)
        TextView text2;
        @BindView(R.id.card_2)
        CardView card2;

        public PowerHeadViewHodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PowerBodyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycer_power_winsaide)
        RecyclerView recycerPowerWinsaide;
        @BindView(R.id.recycer_power_losesaide)
        RecyclerView recycerPowerLosesaide;
        public PowerBodyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
