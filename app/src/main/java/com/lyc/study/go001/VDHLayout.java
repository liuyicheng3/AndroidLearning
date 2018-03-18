package com.lyc.study.go001;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by lyc on 17/9/29.
 */

public class VDHLayout extends RelativeLayout {
    private ViewDragHelper mDragger;
    private Point mAutoBackOriginPos = new Point();
    private boolean hasInit;


    public VDHLayout(Context context) {
        super(context);
        init();
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VDHLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


   private void init(){
       //第二个参数就是滑动灵敏度的意思
       mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback(){
           //这个地方实际上函数返回值为true就代表可以滑动 为false 则不能滑动
           @Override
           public boolean tryCaptureView(View child, int pointerId){
               return true;
           }

           //这个地方实际上left就代表 你将要移动到的位置的坐标。返回值就是最终确定的移动的位置。
           // 我们要让view滑动的范围在我们的layout之内
           //实际上就是判断如果这个坐标在layout之内 那我们就返回这个坐标值。
           //如果这个坐标在layout的边界处 那我们就只能返回边界的坐标给他。不能让他超出这个范围
           //除此之外就是如果你的layout设置了padding的话，也可以让子view的活动范围在padding之内的.

           @Override
           public int clampViewPositionVertical(View child, int top, int dy){
               final int topBound = getPaddingTop();
               final int bottomBound = getHeight() - child.getHeight();
               final int newTop = Math.min(Math.max(top, topBound), bottomBound);
               return newTop;
           }

           @Override
           public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
               super.onViewPositionChanged(changedView, left, top, dx, dy);
               mAutoBackOriginPos.set(left+dx,top+dy);
               hasInit = true;
           }

           //手指释放的时候回调
           @Override
           public void onViewReleased(View releasedChild, float xvel, float yvel){
               //mAutoBackView手指释放时可以自动回去
           }

           @Override
           public int clampViewPositionHorizontal(View child, int left, int dx) {
               return getWidth()-child.getWidth();
           }


           @Override
           public int getViewVerticalDragRange(View child){
               return getMeasuredHeight()-child.getMeasuredHeight();
           }
       });
   }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDragger.processTouchEvent(event);
        return true;
    }


}