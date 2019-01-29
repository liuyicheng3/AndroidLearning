package com.lyc.study.go008;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.lyc.common.MLog;

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
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean result = super.dispatchTouchEvent(event);
        MLog.e(String.valueOf(result));
        return result;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean onInte=super.onInterceptTouchEvent(ev);
        MLog.e(String.valueOf(onInte));
        return onInte;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouch=super.onTouchEvent(event);
        MLog.e(String.valueOf(onTouch));
        return onTouch;

    }
}