package com.example.evenalone.a300hero.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

public class PopLoaclUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalUserBean> localUserBeanList = new ArrayList<>();
    private ClickItemListenner clickItemListenner;
    public void setLocalUserBeanList(List<LocalUserBean> localUserBeanList) {
        this.localUserBeanList = localUserBeanList;
        LocalUserBean localUserBean_me=null;
        for (LocalUserBean localUserBean:localUserBeanList)
        {
            if (localUserBean.getNickname().equals(SpUtils.getNowUser()))
            {
                //移除当前页面
               localUserBean_me = localUserBean;
            }
        }
        localUserBeanList.remove(localUserBean_me);
    }

    public void setClickPoslistener(ClickItemListenner listenner)
    {
        this.clickItemListenner = listenner;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pop_recyler_item, viewGroup, false);
        return new PopLoaclViewHoleder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PopLoaclViewHoleder)
        {
            PopLoaclViewHoleder roleViewholder = (PopLoaclViewHoleder) viewHolder;
            final LocalUserBean userBean = localUserBeanList.get(i);
            roleViewholder.tvLoaclName.setText(userBean.getNickname());
            roleViewholder.tvLoaclJump.setText("团分:"+userBean.getJumpvalue()+" | 胜率:"+userBean.getViotory());
            Glide.with(viewHolder.itemView.getContext()).load(getImgUrl(userBean)).into(roleViewholder.imgLocalAvator);
            roleViewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (null!=clickItemListenner)
                        {
                            clickItemListenner.onclick(userBean);
                        }
                }
            });
        }
    }
    public String getImgUrl(LocalUserBean localUserBean)
    {
        String role = localUserBean.getRole_iocnfile();
        String img = localUserBean.getImg_iconfile();
        if (TextUtils.isEmpty(role))
        {
            return Contacts.IMG+img;
        }
        if (role.contains("herohead"))
        {
            return Contacts.IMG+role;
        }
        return Contacts.ROLE_IMG +role;
    }

    @Override
    public int getItemCount() {
        return localUserBeanList==null?0:localUserBeanList.size();
    }

    class PopLoaclViewHoleder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_local_avator)
        CircleImageView imgLocalAvator;
        @BindView(R.id.tv_loacl_name)
        TextView tvLoaclName;
        @BindView(R.id.tv_loacl_jump)
        TextView tvLoaclJump;
        public PopLoaclViewHoleder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ClickItemListenner
    {
        void onclick(LocalUserBean userBean);
    }
}
