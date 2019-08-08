package com.example.evenalone.a300hero.wedgit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class NoScrollLinearLayout extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public NoScrollLinearLayout(Context context) {
        super(context);
    }

    public NoScrollLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NoScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }


}