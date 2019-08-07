package com.example.evenalone.a300hero.wedgit;

import android.content.Context;

import com.example.evenalone.a300hero.R;
import com.github.mikephil.charting.components.MarkerView;


/**
 * @author Lai
 * @time 2018/5/26 14:31
 * @describe describe
 */

public class PositionMarker extends MarkerView {

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public PositionMarker(Context context) {
        super(context, R.layout.line_chart_position);
    }
}