package com.lyc.study.go009;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lyc.common.Mlog;

/**
 * Created by lyc on 17/1/29.
 */

public class CustomLinnearLayout extends LinearLayout {

    public CustomLinnearLayout(Context context) {
        super(context);
    }

    public CustomLinnearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinnearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getOrientation() ==VERTICAL){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }else {
            int childCout=  getChildCount();
            View targetChild = null;
            int targetPos= -1;
            int measureWidth= 0;
            if (childCout>1){
                for (int i=0;i<childCout;i++){
                    View child=getChildAt(i);

                    if ("vip".equals(child.getTag())){
                        targetChild = child;
                        targetPos= i;
                        Mlog.e("MeasureSpec.getSize(widthMeasureSpec):"+MeasureSpec.getSize(widthMeasureSpec));
                        int fakeWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.AT_MOST);
                        measureChild(targetChild,fakeWidthMeasureSpec,heightMeasureSpec);
                        measureWidth = targetChild.getMeasuredWidth();
                        Mlog.e("measureWidth:"+measureWidth);
                        break;
                    }
                }
            }

            if (targetChild!=null){
                int leftWidth = MeasureSpec.getSize(widthMeasureSpec)- measureWidth;
                Mlog.e("leftWidth:"+leftWidth);
                int leftWidthMeasureSpec = MeasureSpec.makeMeasureSpec(leftWidth,MeasureSpec.getMode(widthMeasureSpec));
                super.onMeasure(leftWidthMeasureSpec,heightMeasureSpec);
                measureChild(targetChild,MeasureSpec.makeMeasureSpec(measureWidth,MeasureSpec.EXACTLY),heightMeasureSpec);
                setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);

            }else {
                super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            }



        }
    }






}
