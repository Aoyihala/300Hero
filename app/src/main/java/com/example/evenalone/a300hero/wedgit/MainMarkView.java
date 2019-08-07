package com.example.evenalone.a300hero.wedgit;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.MakeViewBean;
import com.example.evenalone.a300hero.ui.GuaideInfoActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.rey.material.util.ColorUtil;

import org.xutils.common.util.DensityUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 *
 * Created by admin on 2019/8/6.
 */

public class MainMarkView extends MarkerView {
    public static final int ARROW_SIZE = 30; // 箭头的大小
    private static final float CIRCLE_OFFSET = 10;//因为我这里的折点是圆圈，所以要偏移，防止直接指向了圆心
    private static final float STOKE_WIDTH = 5;//这里对于stroke_width的宽度也要做一定偏移
    private TextView tv_time;
    private RelativeLayout ra_color_item;
    private CircleImageView img_avator;
    private TextView tv_heroname;
    private TextView tv_stutae;
    private CardView card_mark;
    private int index;
    private int oldIndex = -1;
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
        card_mark = findViewById(R.id.card_mark);
    }
    //每次重绘，会调用此方法刷新数据
    //注意只是重绘的时候调用
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        if (makeViewBeanList.size()>0)
        {
            index = highlight.getDataSetIndex();//这个方法用于获得折线是哪根
            card_mark.setCardBackgroundColor(SpUtils.getMainColor());
            int position = (int) e.getX();
            final MakeViewBean makeViewBean = makeViewBeanList.get(position);
            tv_time.setText(makeViewBean.getTime());
            ra_color_item.setBackgroundColor(SpUtils.getMainColor());
            tv_heroname.setText(makeViewBean.getHero());
            //设置空
            img_avator.setBackground(null);
            Glide.with(MyApplication.getContext()).load(Contacts.IMG+makeViewBean.getHero_img())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (resource != null) {
                                if (oldIndex != index) {
                                    MainMarkView.this.getChartView().invalidate();
                                    oldIndex = index;
                                }
                                img_avator.setBackground(resource);
                                MainMarkView.this.getChartView().invalidate();
                            }
                            return false;
                        }
                    }).into(img_avator);
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
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        MPPointF offset = getOffset();
        Chart chart = getChartView();
        float width = getWidth();
        float height = getHeight();
// posY \posX 指的是markerView左上角点在图表上面的位置
//处理Y方向
        if (posY <= height + ARROW_SIZE) {// 如果点y坐标小于markerView的高度，如果不处理会超出上边界，处理了之后这时候箭头是向上的，我们需要把图标下移一个箭头的大小
            offset.y = ARROW_SIZE;
        } else {//否则属于正常情况，因为我们默认是箭头朝下，然后正常偏移就是，需要向上偏移markerView高度和arrow size，再加一个stroke的宽度，因为你需要看到对话框的上面的边框
            offset.y = -height - ARROW_SIZE - STOKE_WIDTH; // 40 arrow height   5 stroke width
        }
//处理X方向，分为3种情况，1、在图表左边 2、在图表中间 3、在图表右边
//
        if (posX > chart.getWidth() - width) {//如果超过右边界，则向左偏移markerView的宽度
            offset.x = -width;
        } else {//默认情况，不偏移（因为是点是在左上角）
            offset.x = 0;
            if (posX > width / 2) {//如果大于markerView的一半，说明箭头在中间，所以向右偏移一半宽度
                offset.x = -(width / 2);
            }
        }
        return offset;
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        Paint paint = new Paint();//绘制边框的画笔
        paint.setStrokeWidth(STOKE_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(SpUtils.getMainColor());

        Paint whitePaint = new Paint();//绘制底色白色的画笔
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(SpUtils.getMainColor());

        Chart chart = getChartView();
        float width = getWidth();
        float height = getHeight();

        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);
        int saveId = canvas.save();

        Path path = new Path();
        if (posY < height + ARROW_SIZE) {//处理超过上边界
            path = new Path();
            path.moveTo(0, 0);
            if (posX > chart.getWidth() - width) {//超过右边界
                path.lineTo(width - ARROW_SIZE, 0);
                path.lineTo(width, -ARROW_SIZE + CIRCLE_OFFSET);
                path.lineTo(width, 0);
            } else {
                if (posX > width / 2) {//在图表中间
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0);
                    path.lineTo(width / 2, -ARROW_SIZE + CIRCLE_OFFSET);
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0);
                } else {//超过左边界
                    path.lineTo(0, -ARROW_SIZE + CIRCLE_OFFSET);
                    path.lineTo(0 + ARROW_SIZE, 0);
                }
            }
            path.lineTo(0 + width, 0);
            path.lineTo(0 + width, 0 + height);
            path.lineTo(0, 0 + height);
            path.lineTo(0, 0);
            path.offset(posX + offset.x, posY + offset.y);
        } else {//没有超过上边界
            path = new Path();
            path.moveTo(0, 0);
            path.lineTo(0 + width, 0);
            path.lineTo(0 + width, 0 + height);
            if (posX > chart.getWidth() - width) {
                path.lineTo(width, height + ARROW_SIZE - CIRCLE_OFFSET);
                path.lineTo(width - ARROW_SIZE, 0 + height);
                path.lineTo(0, 0 + height);
            } else {
                if (posX > width / 2) {
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0 + height);
                    path.lineTo(width / 2, height + ARROW_SIZE - CIRCLE_OFFSET);
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0 + height);
                    path.lineTo(0, 0 + height);
                } else {
                    path.lineTo(0 + ARROW_SIZE, 0 + height);
                    path.lineTo(0, height + ARROW_SIZE - CIRCLE_OFFSET);
                    path.lineTo(0, 0 + height);
                }
            }
            path.lineTo(0, 0);
            path.offset(posX + offset.x, posY + offset.y);
        }

        // translate to the correct position and draw
        canvas.drawPath(path, whitePaint);
        canvas.drawPath(path, paint);
        canvas.translate(posX + offset.x, posY + offset.y);
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "元";
    }





}
