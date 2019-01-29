package com.lyc.study.go014;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.lyc.common.MLog;

/**
 * 底部摇摆动画
 */
public class CustomRotateAnim extends Animation {
    /**
     * 控件宽
     */
    private int mWidth;

    /**
     * 控件高
     */
    private int mHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        this.mWidth = width;
        this.mHeight = height;
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        MLog.e("interpolatedTime:"+interpolatedTime+" degree:"+Math.sin(interpolatedTime * Math.PI*8)*10);
        // 左右摇摆
        t.getMatrix().setRotate((float) (Math.sin(interpolatedTime * Math.PI*8)*10), mWidth / 2, mHeight*7/6);
        super.applyTransformation(interpolatedTime, t);
    }
}