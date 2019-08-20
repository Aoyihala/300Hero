package com.example.evenalone.a300hero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.utils.Contacts;

import net.wujingchao.android.view.SimpleTagImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 小工具适配器
 */
public class ToolsListAdapter extends ArrayAdapter {
    private List<HeroGuide.ListBean> listBeans = new ArrayList<>();

    public void setListBeans(List<HeroGuide.ListBean> listBeans) {
        this.listBeans = listBeans;
        notifyDataSetChanged();
    }

    public ToolsListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HeroGuide.ListBean listBean = listBeans.get(position);
        if (convertView==null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tools_game_item,parent,false);
        }
        //头像
        SimpleTagImageView simpleTagImageView = convertView.findViewById(R.id.img_tool_hero);
        //描述
        TextView tv_win_lose = convertView.findViewById(R.id.tv_tool_flag);
        //类型
        TextView tv_type = convertView.findViewById(R.id.tv_tool_type);
        TextView tv_time = convertView.findViewWithTag(R.id.tv_tool_time);
        TextView tv_user_des = convertView.findViewById(R.id.tv_tool_userdes);
        //计算
        Glide.with(simpleTagImageView).load(Contacts.IMG+listBeans.get(position).getHero().getIconFile()).into(simpleTagImageView);

        return convertView;
    }

    @Override
    public int getCount() {
        return listBeans==null?0:listBeans.size();
    }
}
