package com.example.evenalone.a300hero.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.ui.CharactorActivity;
import com.example.evenalone.a300hero.ui.HomeActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.UiUtlis;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CharatcorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ADD_TYPE = 0;
    private int LIST_TYPE = 1;
    private List<LocalUserBean> localUserBeanList = new ArrayList<>();
    private onLongClickListiener onLongClickListiener;
    private onClickListener onClickListener;
    private boolean b;
    private boolean allcheck;
    private boolean allcancel;//使用另一值来标识取消全选
    public void setLocalUserBeanList(List<LocalUserBean> localUserBeanList) {
        this.localUserBeanList = localUserBeanList;
    }

    public void setOnLongClickListiener(CharatcorAdapter.onLongClickListiener onLongClickListiener) {
        this.onLongClickListiener = onLongClickListiener;
    }

    public void setOnClickListener(CharatcorAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (getItemViewType(i) == LIST_TYPE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.charator_item, viewGroup, false);
            return new CharatorListViewHolder(view);
        } else if (getItemViewType(i) == ADD_TYPE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layer_item, viewGroup, false);
            return new AddViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof CharatorListViewHolder)
        {
            //要从第0个开始排
            LocalUserBean localUserBean = localUserBeanList.get(i-1);
            final CharatorListViewHolder listViewHolder = (CharatorListViewHolder) viewHolder;
            Glide.with(listViewHolder.itemView.getContext()).load(Contacts.ROLE_IMG+localUserBean.getIocnfile()).into(listViewHolder.imgCharaterAvator);
            //获取团分
            listViewHolder.tvCharaterUsername.setText(localUserBean.getNickname());
            listViewHolder.tvCharaterPower.setText("团分 "+localUserBean.getJumpvalue());
            listViewHolder.tvCharaterPercent.setText("胜率 "+localUserBean.getViotory());
            listViewHolder.checkboxCharatorCheck.setChecked(CharactorActivity.localUserBeanMap.get(localUserBean));
            if (b)
            {
                listViewHolder.checkboxCharatorCheck.setVisibility(View.VISIBLE);
            }
            else
            {
                listViewHolder.checkboxCharatorCheck.setVisibility(View.GONE);
            }
            if (allcheck&&b)
            {
                listViewHolder.checkboxCharatorCheck.setChecked(true);
                CharactorActivity.localUserBeanMap.put(localUserBean,true);
            }


            //计算团分
            int pwoer_win_adv = Integer.parseInt(localUserBean.getJumpvalue());
            if (pwoer_win_adv > 0 && pwoer_win_adv < 1500) {
                listViewHolder.imgCharaterPosition.setBackgroundResource(R.drawable.little_fish);
                listViewHolder.tvCharaterPosition.setText(MyApplication.getContext().getString(R.string.liitle_fish));
                listViewHolder.tvCharaterPosition.setBackgroundResource(R.drawable.little_fish_shape);
                listViewHolder.tvCharaterPosition.setTextColor(UiUtlis.getColor(R.color.gray));
                
        }
        if (pwoer_win_adv >= 1500 && pwoer_win_adv < 3000) {
            listViewHolder.imgCharaterPosition.setBackgroundResource(R.drawable.little_fish_bigger);
            listViewHolder.tvCharaterPosition.setText(MyApplication.getContext().getString(R.string.baiying));
            listViewHolder.tvCharaterPosition.setBackgroundResource(R.drawable.little_fish_bigger_shape);
            listViewHolder.tvCharaterPosition.setTextColor(UiUtlis.getColor(R.color.little_fish_bigger));
        }
        if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
            listViewHolder.imgCharaterPosition.setBackgroundResource(R.drawable.fishes);
            listViewHolder.tvCharaterPosition.setText(MyApplication.getContext().getString(R.string.gold));
            listViewHolder.tvCharaterPosition.setBackgroundResource(R.drawable.fishes_shap);
            listViewHolder.tvCharaterPosition.setTextColor(UiUtlis.getColor(R.color.fishes));
        }
        if (pwoer_win_adv >= 3000) {
            listViewHolder.imgCharaterPosition.setBackgroundResource(R.drawable.shark);
            listViewHolder.tvCharaterPosition.setText(MyApplication.getContext().getString(R.string.daemo));
            listViewHolder.tvCharaterPosition.setBackgroundResource(R.drawable.shark_shape);
            listViewHolder.tvCharaterPosition.setTextColor(UiUtlis.getColor(R.color.shark));
        }
        if (pwoer_win_adv>=4000)
        {
            listViewHolder.imgCharaterPosition.setBackgroundResource(R.drawable.jiucai);
            listViewHolder.tvCharaterPosition.setText(MyApplication.getContext().getString(R.string.litle));
            listViewHolder.tvCharaterPosition.setBackgroundResource(R.drawable.jiucai_shape);
            listViewHolder.tvCharaterPosition.setTextColor(UiUtlis.getColor(R.color.jiucai));
        }
            //记得注册长按事件和点击事件
            listViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onLongClickListiener!=null)
                    {
                        onLongClickListiener.onClick(listViewHolder.checkboxCharatorCheck,i);
                    }
                    return true;
                }
            });
            listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null)
                    {
                        onClickListener.onShortClick(listViewHolder.checkboxCharatorCheck,i);
                    }
                }
            });
        }
        if (viewHolder instanceof AddViewHolder)
        {
            AddViewHolder addViewHolder = (AddViewHolder) viewHolder;
            //前往角色添加的home页面
            addViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(viewHolder.itemView.getContext(),HomeActivity.class);
                    viewHolder.itemView.getContext().startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return localUserBeanList == null ? 1 : localUserBeanList.size() + 1;
    }

    @SuppressLint("NewApi")
    @Override
    public int getItemViewType(int position) {
        //当位置多一位时就该显示其他的item了
        if (position > 0) {
            return LIST_TYPE;
        } else {
            return ADD_TYPE;
        }
    }

    public void setSeclectMode(boolean b) {
        this.b=b;
    }

    public void setAllseclet(boolean isChecked) {
        this.allcheck = isChecked;
        allcancel = isChecked;
    }

    class CharatorListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_charator_check)
        CheckBox checkboxCharatorCheck;
        @BindView(R.id.img_charater_avator)
        CircleImageView imgCharaterAvator;
        @BindView(R.id.tv_charater_username)
        TextView tvCharaterUsername;
        @BindView(R.id.tv_charater_power)
        TextView tvCharaterPower;
        @BindView(R.id.tv_charater_percent)
        TextView tvCharaterPercent;
        @BindView(R.id.img_charater_position)
        ImageView imgCharaterPosition;
        @BindView(R.id.tv_charater_position)
        TextView tvCharaterPosition;
        @BindView(R.id.card_charator)
        CardView cardCharator;

        public CharatorListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class AddViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ly_charator_add)
        RelativeLayout lyCharatorAdd;

        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface onLongClickListiener
    {
        void onClick(CheckBox checkBox,int pos);
    }

    public interface onClickListener
    {
        void onShortClick(CheckBox checkBox,int pos);
    }
}
