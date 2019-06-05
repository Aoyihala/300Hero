package com.example.evenalone.a300hero.wedgit;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.example.evenalone.a300hero.R;

import razerdp.basepopup.BasePopupWindow;

public class MenuPopwindow extends BasePopupWindow {
    public MenuPopwindow(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.pop_window);
    }


    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultAlphaAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation()
    {
        return getDefaultAlphaAnimation(false);
    }
}
