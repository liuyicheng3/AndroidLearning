package com.lyc.study.go008;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lyc.common.MLog;

/**
 * Created by lyc on 16/12/24.
 */

public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow(){
        MLog.e("on attache ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MLog.e("on onMeasure ");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        MLog.e("on onLayout ");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean result = super.dispatchTouchEvent(event);
        MLog.e(String.valueOf(result));
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouch=super.onTouchEvent(event);
        MLog.e(String.valueOf(onTouch));
        return onTouch;

    }
}
