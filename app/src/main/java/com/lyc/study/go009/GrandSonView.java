package com.lyc.study.go009;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lyc.common.MLog;

/**
 * Created by lyc on 17/1/29.
 */

public class GrandSonView extends FrameLayout {

    public GrandSonView(Context context) {
        super(context);
    }

    public GrandSonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrandSonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        MLog.e("width:"+w+" height:"+h);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        MLog.e("onMeasure>>"+"width:"+width+" height:"+height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        MLog.e("onLayout");
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        MLog.e(String.valueOf(result));
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
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
