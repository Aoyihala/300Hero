package com.example.evenalone.a300hero.wedgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.evenalone.a300hero.utils.SpUtils;



/**
 *画布
 * Created by admin on 2019/8/7.
 */

public class PositionImageView extends View {
    private Path path;

    public PositionImageView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public PositionImageView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PositionImageView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

		/*设置背景为白色*/
        canvas.drawColor(Color.WHITE);
        Paint paint=new Paint();
		/*去锯齿*/
        paint.setAntiAlias(true);
		/*设置paint的颜色*/
        paint.setColor(Color.RED);
		/*设置paint的　style　为STROKE：空心*/
        paint.setStyle(Paint.Style.STROKE);
		/*设置paint的外框宽度*/
        paint.setStrokeWidth(3);

	/*	*//*画一个空心三角形*//*
        Path path=new Path();
        path.moveTo(10,330);
        path.lineTo(70,330);
        path.lineTo(40,100);
        path.close();
        canvas.drawPath(path, paint);
		*/
		/*设置paint　的style为　FILL：实心*/
        paint.setStyle(Paint.Style.FILL);
		/*设置paint的颜色*/
        paint.setColor(SpUtils.getMainColor());

		/*画一个实心三角形*/
        Path path2=new Path();
        path2.moveTo(90,330);
        path2.lineTo(150,330);
        path2.lineTo(120,270);
        path2.close();
        canvas.drawPath(path2, paint);
        invalidate();

   /*     Shader mShader=new LinearGradient(0,0,100,100,
                new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},
                null,Shader.TileMode.REPEAT);
        paint.setShader(mShader);

		*//*画一个渐变色三角形*//*
        Path path4=new Path();
        path4.moveTo(170,330);
        path4.lineTo(230,330);
        path4.lineTo(200,270);
        path4.close();
        canvas.drawPath(path4,paint);*/

    }

}
