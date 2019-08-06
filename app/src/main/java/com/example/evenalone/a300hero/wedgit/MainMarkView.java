package com.example.evenalone.a300hero.wedgit;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.MakeViewBean;
import com.example.evenalone.a300hero.ui.GuaideInfoActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2019/8/6.
 */

public class MainMarkView extends MarkerView {
    private TextView tv_time;
    private RelativeLayout ra_color_item;
    private CircleImageView img_avator;
    private TextView tv_heroname;
    private TextView tv_stutae;
    private List<MakeViewBean> makeViewBeanList = new ArrayList<>();

    public void setMakeViewBeanList(List<MakeViewBean> makeViewBeanList) {
        this.makeViewBeanList = makeViewBeanList;
    }

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public MainMarkView(Context context) {
        super(context, R.layout.markview_main);
        tv_heroname = findViewById(R.id.tv_mark_name);
        ra_color_item = findViewById(R.id.ra_mark_coloritem);
        tv_stutae = findViewById(R.id.tv_mark_statue);
        img_avator = findViewById(R.id.img_mark_avator);
        tv_time = findViewById(R.id.tv_mark_time);
    }
    //每次重绘，会调用此方法刷新数据
    //注意只是重绘的时候调用
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        if (makeViewBeanList.size()>0)
        {
            int position = (int) e.getX();
            final MakeViewBean makeViewBean = makeViewBeanList.get(position);
            tv_time.setText(makeViewBean.getTime());
            ra_color_item.setBackgroundColor(SpUtils.getMainColor());
            tv_heroname.setText(makeViewBean.getHero());
            Glide.with(MyApplication.getContext()).load(Contacts.IMG+makeViewBean.getHero_img()).into(img_avator);
            if (makeViewBean.isWin())
            {
                tv_stutae.setText("赢");
                tv_stutae.setTextColor(UiUtlis.getColor(R.color.colorPrimary));

            }
            else {
                tv_stutae.setText("输");
                tv_stutae.setTextColor(UiUtlis.getColor(R.color.Red));
            }
            if (makeViewBean.getMy_type()==1)
            {
                tv_stutae.setText("验");
                tv_stutae.setTextColor(UiUtlis.getColor(R.color.blue));
            }
            //特殊情况
            if (makeViewBean.getType()==3)
            {
                tv_stutae.setText("逃");
                tv_stutae.setTextColor(UiUtlis.getColor(R.color.Red));
            }
            img_avator.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getContext(), GuaideInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",makeViewBean.getMatch_id());
                    intent.putExtras(bundle);
                    MyApplication.getContext().startActivity(intent);
                }
            });
        }
        super.refreshContent(e, highlight);

    }

    //布局的偏移量。就是布局显示在圆点的那个位置
    // -(width / 2) 布局水平居中
    //-(height) 布局显示在圆点上方
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "元";
    }





}
