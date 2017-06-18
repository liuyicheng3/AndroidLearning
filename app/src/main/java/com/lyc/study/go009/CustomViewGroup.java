package com.lyc.study.go009;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lyc.common.Mlog;

/**
 * Created by lyc on 17/1/29.
 */

public class CustomViewGroup extends FrameLayout {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Mlog.e("width:"+w+" height:"+h);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Mlog.e("onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        Mlog.e("onMeasure>>"+"width:"+width+" height:"+height);    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Mlog.e("onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
