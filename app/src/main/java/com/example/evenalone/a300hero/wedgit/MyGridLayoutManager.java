package com.example.evenalone.a300hero.wedgit;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

public class MyGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;
 
    public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
 
    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }
 
    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
 
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }
 
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
