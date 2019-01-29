package com.lyc.study.go008;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.lyc.common.MLog;

/**
 * Created by lyc on 16/11/25.
 */

public class CustomViewGroup2 extends FrameLayout {
    public CustomViewGroup2(Context context) {
        super(context);
    }

    public CustomViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        MLog.e("dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean onInte=super.onInterceptTouchEvent(ev);
        MLog.e("onInte"+onInte);
        return onInte;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouch=super.onTouchEvent(event);
        MLog.e("onTouch"+onTouch);
        return onTouch;

    }
}