package com.lyc.study.go008;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.lyc.common.Mlog;

/**
 * Created by lyc on 16/11/25.
 */

public class CustomViewGroup extends RelativeLayout {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean onInte=super.onInterceptTouchEvent(ev);
        Mlog.e("onInte"+onInte);
        return onInte;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouch=super.onTouchEvent(event);
        Mlog.e("onTouch"+onTouch);
        return onTouch;

    }
}