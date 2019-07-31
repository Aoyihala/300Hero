package com.example.evenalone.a300hero.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.UiUtlis;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquaipeMentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GameInfo.MatchBean.WinSideBean.EquipBean> win_equipBeanList = new ArrayList<>();
    private List<GameInfo.MatchBean.LoseSideBean.EquipBeanX> lose_equipBeanList = new ArrayList<>();
    private boolean iswin = false;

    public void setIswin(boolean iswin) {
        this.iswin = iswin;
    }

    public void setLose_equipBeanList(List<GameInfo.MatchBean.LoseSideBean.EquipBeanX> lose_equipBeanList) {
        this.lose_equipBeanList = lose_equipBeanList;
    }

    public void setWin_equipBeanList(List<GameInfo.MatchBean.WinSideBean.EquipBean> win_equipBeanList) {
        this.win_equipBeanList = win_equipBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = UiUtlis.getView(R.layout.equaipe_item);
        return new ImgViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ImgViewholder imgViewholder = (ImgViewholder) viewHolder;
        if (iswin)
        {
            Glide.with(imgViewholder.itemView.getContext()).load(Contacts.IMG+win_equipBeanList.get(i).getIconFile()).into(imgViewholder.imgEquaipe);
        }
        else
        {
            Glide.with(imgViewholder.itemView.getContext()).load(Contacts.IMG+lose_equipBeanList.get(i).getIconFile()).into(imgViewholder.imgEquaipe);
        }


    }

    @Override
    public int getItemCount() {
        if (win_equipBeanList!=null&&win_equipBeanList.size()>0)
        {
            return win_equipBeanList.size();
        }
        if (lose_equipBeanList!=null&&lose_equipBeanList.size()>0)
        {
            return lose_equipBeanList.size();
        }
        return 0;
    }

    class ImgViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_equaipe)
        ImageView imgEquaipe;
        public ImgViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}