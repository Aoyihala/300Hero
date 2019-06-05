package com.example.evenalone.a300hero.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.ui.ListActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LocalRoleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LocalUserBean> localUserBeans = new ArrayList<>();

    public void setLocalUserBeans(List<LocalUserBean> localUserBeans) {
        this.localUserBeans = localUserBeans;


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_local_item, viewGroup, false);
        return new LocalRoleViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof LocalRoleViewholder)
        {
            LocalRoleViewholder roleViewholder = (LocalRoleViewholder) viewHolder;
            final LocalUserBean userBean = localUserBeans.get(i);
            roleViewholder.tvLoaclName.setText(userBean.getNickname());
            roleViewholder.tvLoaclJump.setText("团分:"+userBean.getJumpvalue()+" | 胜率:"+userBean.getViotory());
            Glide.with(viewHolder.itemView.getContext()).load(Contacts.ROLE_IMG+userBean.getIocnfile()).into(roleViewholder.imgLocalAvator);
            roleViewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpUtils.selectUser(userBean.getNickname());
                    Intent in = new Intent(viewHolder.itemView.getContext(),ListActivity.class);
                    viewHolder.itemView.getContext().startActivity(in);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return localUserBeans==null?0:localUserBeans.size();
    }


    class LocalRoleViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_local_avator)
        CircleImageView imgLocalAvator;
        @BindView(R.id.tv_loacl_name)
        TextView tvLoaclName;
        @BindView(R.id.tv_loacl_jump)
        TextView tvLoaclJump;
        public LocalRoleViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
