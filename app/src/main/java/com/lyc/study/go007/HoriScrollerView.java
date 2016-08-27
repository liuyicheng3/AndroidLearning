package com.lyc.study.go007;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.lyc.common.Mlog;

/**
 *  Created by  lyc on 16-7-16.
 */
public class HoriScrollerView extends View implements GestureDetector.OnGestureListener {
    private Context ctx;

    private GestureDetectorCompat mGestureDetectorCompat;

    private Scroller mScroller;

    Paint textValuePaint, textSelectPaint, valueBgPaint;
    private float mCenterTextSize = 60; //文字大小
    private int centerCircleRadius = 60;
    private int mWidth, mHeight, mLeftBound, mRightBound;
    Paint.FontMetricsInt fontMetrics;

    private int spacing;
    private float mMaxOverScrollDistance; //最大 OverScroll距离

    private int valueStart=1,valueEnd=12;

    boolean isInit=false;
    int extra=0;

    public HoriScrollerView(Context context) {
        super(context);
        init(context);
    }

    public HoriScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HoriScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context mContext) {
        this.ctx = mContext;

        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), this);
        mScroller = new Scroller(getContext(), new DecelerateInterpolator(4f));

        this.textValuePaint = new Paint();
        textValuePaint.setColor(Color.GRAY);
        textValuePaint.setTextSize(mCenterTextSize);
        textValuePaint.setTextAlign(Paint.Align.CENTER);
        textValuePaint.setStrokeWidth(3);


        this.textSelectPaint = new Paint();
        textSelectPaint.setColor(Color.WHITE);
        textSelectPaint.setTextSize(mCenterTextSize);
        textSelectPaint.setTextAlign(Paint.Align.CENTER);

        fontMetrics = textValuePaint.getFontMetricsInt();

        this.valueBgPaint = new Paint();
        valueBgPaint.setColor(0xFF1C967E);
        valueBgPaint.setAntiAlias(true);
        valueBgPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = MeasureSpec.getSize(widthMeasureSpec);
        int result = getSuggestedMinimumWidth();
        switch (measureMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = measureSize;
                break;
            default:
                break;
        }
        return result;
    }

    private int measureHeight(int heightMeasure) {
        int measureMode = MeasureSpec.getMode(heightMeasure);
        int measureSize = MeasureSpec.getSize(heightMeasure);
        int result = getSuggestedMinimumHeight();
        switch (measureMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = measureSize;
                break;
            default:
                break;
        }
        return result;
    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mWidth = w;
            mHeight = h;
            mLeftBound = 0;
            mRightBound = 2 * mWidth;
            mMaxOverScrollDistance = w / 4f;
            spacing = mWidth / 5;

            isInit =true;
            Mlog.e("mWidth: " + mWidth + "mHeight: " + mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int scrollerX = getScrollX();

        canvas.drawCircle(mWidth / 2 + scrollerX, mHeight / 2, centerCircleRadius, valueBgPaint);


        if (isInit){
            isInit =false;
            float mid =(mWidth/2)/Float.valueOf(spacing);
            extra = (int) (spacing*((mWidth/2)/spacing+1-mid));
        }
        for (int j = valueStart; j < valueEnd; j++) {
            int itemX=(j-valueStart) * spacing+extra;

            canvas.drawText(String.valueOf(j), itemX, mHeight / 2 + Math.abs(fontMetrics.bottom - fontMetrics.top) / 2, getPaintWithDrawPosition(itemX, mWidth / 2 + scrollerX));
//            canvas.drawText(String.valueOf(midValue + j), mWidth / 2 + j * spacing, mHeight / 2 + Math.abs(fontMetrics.bottom - fontMetrics.top) / 2, getPaintWithDrawPosition(mWidth / 2 + j * spacing, mWidth / 2 + scrollerX));
        }

    }


    private Paint getPaintWithDrawPosition(int x, int currentMid) {
        if (Math.abs(currentMid - x) < 30) {
            return textSelectPaint;
        } else {
            return textValuePaint;
        }

//        return textValuePaint;

    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float dis = distanceX;

        scrollBy((int) dis, 0);

        Mlog.e("onScroll"+dis);
        return true;
    }


    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = mGestureDetectorCompat.onTouchEvent(event);
        if (MotionEvent.ACTION_UP == event.getAction()) {
            fixCenterPostion();
            ret = true;
        }
        return ret || super.onTouchEvent(event);
    }

    private void fixCenterPostion() {
        int scrollX = getScrollX();
        if (spacing != 0) {
            int targetX = Math.round(scrollX / spacing)*spacing-scrollX;

            mScroller.startScroll(scrollX, 0,targetX, 0,targetX*1200/spacing);

        }
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            scrollTo(x, 0);
            postInvalidate();
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
