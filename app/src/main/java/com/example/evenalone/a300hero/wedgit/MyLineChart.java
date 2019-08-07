package com.example.evenalone.a300hero.wedgit;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.example.evenalone.a300hero.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.lang.ref.WeakReference;

/**
 * 自定义的折线统计图
 * Created by admin on 2019/8/7.
 */

public class MyLineChart  extends LineChart{
    //弱引用覆盖物对象,防止内存泄漏,不被回收
    private WeakReference<MainMarkView> mDetailsReference;
    private WeakReference<PositionMarker> mPositionMarkerReference;

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyLineChart(Context context) {
        super(context);
    }

    /**
     * 所有覆盖物是否为空
     *
     * @return TRUE FALSE
     */
    public boolean isMarkerAllNull() {
        if (mPositionMarkerReference!=null&&mDetailsReference!=null)
        {
            return mDetailsReference.get() == null  && mPositionMarkerReference.get() == null;
        }
        return true;

    }

    public void setDetailsMarkerView(MainMarkView detailsMarkerView) {
        mDetailsReference = new WeakReference<>(detailsMarkerView);
    }


    public void setPositionMarker(PositionMarker positionMarker) {
        mPositionMarkerReference = new WeakReference<>(positionMarker);
    }


    /**
     复制父类的 drawMarkers方法，并且更换上自己的markerView
     * draws all MarkerViews on the highlighted positions
     */
    protected void drawMarkers(Canvas canvas) {
        //圆点
        //线
        //主要覆盖物
        if (mDetailsReference==null||mPositionMarkerReference==null)
        {
            return;
        }
        MainMarkView mDetailsMarkerView = mDetailsReference.get();
        PositionMarker mPositionMarker = mPositionMarkerReference.get();

        // if there is no marker view or drawing marker is disabled
        if (mDetailsMarkerView == null || mPositionMarker == null || !isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);

            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            LineDataSet dataSetByIndex = (LineDataSet) getLineData().getDataSetByIndex(highlight.getDataSetIndex());

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            float circleRadius = dataSetByIndex.getCircleRadius();

            //pos[0], pos[1] x 和 y
            // callbacks to update the content
            mDetailsMarkerView.refreshContent(e, highlight);

            mDetailsMarkerView.draw(canvas, pos[0], pos[1] - mPositionMarker.getHeight());


            mPositionMarker.refreshContent(e, highlight);
            mPositionMarker.draw(canvas, pos[0] - mPositionMarker.getWidth() / 2, pos[1] - mPositionMarker.getHeight());

        }
    }
}
