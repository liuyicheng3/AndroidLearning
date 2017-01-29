package com.lyc.study.go009;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;

/**
 * Created by lyc on 17/1/29.
 */

public class SonViewGroup extends FrameLayout {

    public SonViewGroup(Context context) {
        super(context);
    }

    public SonViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SonViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Mlog.e("width:" + w + " height:" + h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newWidthMeasureSpec=MeasureSpec.makeMeasureSpec(UtilsManager.dip2px(getContext(),200),MeasureSpec.EXACTLY);
//        setMeasuredDimension(newWidthMeasureSpec,heightMeasureSpec);
        super.onMeasure(newWidthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(newWidthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Mlog.e("onMeasure>>" + "width:" + width + " height:" + height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Mlog.e("onLayout");
    }
}
