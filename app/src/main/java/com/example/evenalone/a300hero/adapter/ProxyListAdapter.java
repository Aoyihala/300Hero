package com.example.evenalone.a300hero.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.bean.NetWorkProx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProxyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NetWorkProx> proxList = new ArrayList<>();

    public void setProxList(List<NetWorkProx> proxList) {
        this.proxList = proxList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.proxy_item, viewGroup, false);
        return new ProxyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ProxyViewHolder)
        {
             ProxyViewHolder proxyViewHolder = (ProxyViewHolder) viewHolder;
             NetWorkProx prox = proxList.get(i);
             proxyViewHolder.tvIp.setText(prox.getIp());
             proxyViewHolder.tvAdress.setText(prox.getAdress());
             proxyViewHolder.tvDay.setText("存活天数:"+prox.getDay());
             proxyViewHolder.tvUpadteTime.setText("更新时间:"+prox.getUpdate_time());
             proxyViewHolder.tvType.setText(prox.getType());

        }


    }

    @Override
    public int getItemCount() {
        return proxList==null?0:proxList.size();
    }

    class ProxyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ip)
        TextView tvIp;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_adress)
        TextView tvAdress;
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_upadte_time)
        TextView tvUpadteTime;
        public ProxyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
