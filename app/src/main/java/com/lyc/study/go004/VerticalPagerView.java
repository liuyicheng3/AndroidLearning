package com.lyc.study.go004;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.lyc.common.Mlog;

/**
 * lyc
 */
public class VerticalPagerView extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop;
    private int TIME = 300;
    private  boolean canScroll=true;
    private OnScrollListener onScrollListener = null;

    private android.os.Handler handler=new android.os.Handler();

    private int pageHeight0 = 0;
    private int pageHeight1 = 0;


    public VerticalPagerView(Context context) {
        super(context);
        init(getContext());
    }

    public VerticalPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(getContext());
    }

    public VerticalPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(getContext());
    }

    void init(Context context) {
        mScroller = new Scroller(getContext(), sInterpolator);
        setFocusable(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    public void setScroolAble(boolean canScroll){
        this.canScroll=canScroll;
    }


    public void showUP(){
        mScroller.startScroll(0,0,0,pageHeight0);
        invalidate();
    }
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        pageHeight0=0;
        pageHeight1=0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                if (i == 0) {
                    child.measure(widthMeasureSpec, heightMeasureSpec);
                    pageHeight0 = child.getMeasuredHeight();
                }else if(i == 1){
                    int parentHeight=MeasureSpec.getSize(heightMeasureSpec)/4;
                    int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.EXACTLY);
                    child.measure(widthMeasureSpec, childHeightMeasureSpec);
                    pageHeight1 = child.getMeasuredHeight();
                    Mlog.e("pageHeight1"+pageHeight1);
                }
                else {
                    child.measure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int layoutHeight = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child.getVisibility() != GONE){
                final int childHeight = child.getMeasuredHeight();
                child.layout(0, layoutHeight, child.getMeasuredWidth(), layoutHeight + childHeight);
                layoutHeight += childHeight;
            }
        }
    }

    private boolean mIsBeingDragged;
    private float mLastMotionY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Mlog.e("onInterceptTouchEvent" + ev);

        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float y = ev.getY();
                mLastMotionY = y;
                mIsBeingDragged = !mScroller.isFinished();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float y = ev.getY();
                final int yDiff = (int) Math.abs(y - mLastMotionY);
                Mlog.e("yDiff > mTouchSlop"+yDiff +">"+mTouchSlop);
                if (yDiff > mTouchSlop) {// 垂直移动距离达到滑动距离，进行手势拦截
                    mIsBeingDragged = true;
                    mLastMotionY = y;
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            /* Release the drag */
                mIsBeingDragged = false;
                break;
        }
        return mIsBeingDragged;
    }

    VelocityTracker mVelocityTracker;

    public boolean onTouchEvent(MotionEvent event) {
        Mlog.e("onTouchEvent" + canScroll);
        isInit=true;

        if(!canScroll){
            return false;
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Mlog.e("onTouchEvent0" + event);

                isFingerTouch=true;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionY = event.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                Mlog.e("onTouchEvent1" + event);

                isFingerTouch=true;
                final int deltaY = (int) (mLastMotionY - y);
                mLastMotionY = y;
                if (getScrollY() >= 0 && getScrollY() <= getAllScrollY()) {
                    Mlog.e("onTouchEvent1--" + event);

                    if (deltaY >= 0) {
                        Mlog.e("onTouchEvent1-0" +"deltaY"+deltaY+"getAllScrollY() - getScrollY()"+(getAllScrollY() - getScrollY()));
                        //手指向下拖动的时候处理滑动
                        scrollBy(0, Math.min(deltaY, (getAllScrollY() - getScrollY() > 0 ? getAllScrollY() - getScrollY() : 0)));

                    } else {

                        if(getScrollY()==0){
                            Mlog.e("onTouchEvent1-?" + event);

                            return true;
                        }
                        Mlog.e("onTouchEvent1-1" + event);

                        scrollBy(0, getScrollY() != 0 ? Math.max(deltaY, -getScrollY()) : deltaY);
                    }
                    onScrollListener.onScrollPercent((float) getScrollY()/(float) pageHeight0);
                }

                break;

            case MotionEvent.ACTION_UP:
                Mlog.e("onTouchEvent2" + event);

                isFingerTouch=false;
                mVelocityTracker.computeCurrentVelocity(1000, ViewConfiguration.get(this.getContext()).getScaledMaximumFlingVelocity());
                int initialVelocity = (int) mVelocityTracker.getYVelocity();
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }

                boolean isUpScroll = false;

                if (initialVelocity > 500) {
                    isUpScroll = false;
                } else if (initialVelocity < -500) {
                    isUpScroll = true;
                } else {// 根据当前位置进行判断

                    if ((getScrollY() - getNearestMinY(getScrollY())) > getCurrentScrollLenght(getScrollY()) / 2.0f) {
                        isUpScroll = true;
                    } else {
                        isUpScroll = false;
                    }

                }

                slide(isUpScroll);

                break;
            case MotionEvent.ACTION_CANCEL:
                isFingerTouch=false;
                break;

        }

        return true;
    }

    private int getAllScrollY(){
        return pageHeight0+pageHeight1;
    }


    private int getNearestMinY(int scrollY) {
        if (scrollY < pageHeight0) {
            return 0;
        }else{
            return pageHeight0;
        }
    }

    private int getCurrentScrollLenght(int scrollY){
        int result;
        if (scrollY <= pageHeight0) {
            result= pageHeight0;
        }else{
            result= pageHeight1;
        }
        return result;
    }


    private int getCurrentMaxScrollY(int scrollY){
        int result;
        if(scrollY<=pageHeight0){
            result= pageHeight0;
        }else{
            result=pageHeight0+pageHeight1;
        }
        return result;
    }



    //true表示手指朝上滑动
    private void slide(boolean isUp) {
        Mlog.e("isUp:"+isUp+"mScroller.getCurrY():"+mScroller.getCurrY());
        if (isUp) {
            if (getScrollY() <pageHeight0) {
                int _currentScrollY=getCurrentScrollLenght(getScrollY());
                int slidey = getCurrentMaxScrollY(getScrollY()) - getScrollY();
                float time_delay=0;
                if(_currentScrollY!=0){
                    time_delay = (slidey * TIME / _currentScrollY);
                }
                mScroller.startScroll(0, getScrollY(), 0, slidey, (int) time_delay);
                invalidate();
            }
        } else {
            if (getScrollY() <= pageHeight0) {
                int _currentScrollY=getCurrentScrollLenght(getScrollY());
                int slidey = getScrollY() - getNearestMinY(getScrollY());
                float time_delay=0;
                if(_currentScrollY!=0){
                    time_delay = (slidey * TIME / _currentScrollY);
                }
                mScroller.startScroll(0, getScrollY(), 0, -slidey, (int) time_delay);
                invalidate();
            }
        }
    }

    private boolean isInit=false;
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int y = mScroller.getCurrY();
            if (isInit){
                onScrollListener.onScrollPercent((float) getScrollY()/(float) pageHeight0);
            }
            scrollTo(0, y);
            postInvalidate();
        }
    }


    public void scroll2Page(int pageIndex) {
        int y=0;
        if(pageIndex<0||pageIndex>2){
            pageIndex=0;
        }
        if(pageIndex==1){
        y=pageHeight0;
        }else if(pageIndex==2){
            y=pageHeight0+pageHeight1;
        }
        int currentScrollLength=getCurrentScrollLenght(getScrollY());
        float time_delay=100;
        if(currentScrollLength!=0){
            time_delay = Math.abs(getScrollY()-y) * TIME / currentScrollLength;
        }
        int deltaY=getScrollY()-y;
        mScroller.startScroll(0, getScrollY(), 0, -deltaY, (int) time_delay);
        Mlog.e("deltaY" + deltaY);
        invalidate();

    }

    private int nowY = 0;
    private int preY = 0;
    private boolean isFingerTouch = false; // 标示当前手指是否处于触摸状态





    public int getCurrentItem(){
        if(getScrollY()==0){
            return 0;
        }else{
            if(pageHeight1==0){
               if (getScrollY()==pageHeight0){
                   return 2;
               }else{
                   return 0;
               }
            }else{
                if(getScrollY()==pageHeight0){
                    return 1;
                }else if(getScrollY()==pageHeight0+pageHeight1){
                    return 2;
                }else{
                    return 0;
                }
            }
        }

    }

    public boolean isInnerCanScroll(){
        Mlog.e("isInnerCanScroll"+getScrollY()+"*"+pageHeight0+"*"+pageHeight1);
        return getScrollY()>=pageHeight0+pageHeight1;
    }


    private final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            return -(t) * (t - 2);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    };

    interface  OnScrollListener{
        void onScrollPercent(float percent);
    }
}

