package com.lyc.study.go007;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

import com.lyc.common.MLog;
import com.lyc.study.R;

import java.security.InvalidParameterException;



/**
 * 横向滚动的时间区间选择器
 * Created by ring
 * on 16/4/13.
 */
public class TimeWheelView extends View implements GestureDetector.OnGestureListener {

    private OnTimeSelectedListener onTimeSelectedListener = null;
    private OverScroller mScroller;
    private GestureDetectorCompat mGestureDetectorCompat;
    private boolean mIsFling;

    private Paint mBackgoundPaint;
    private Paint mBoundLinePaint;
    private Paint mStartLinePaint;
    private Paint mEndLinePaint;
    private Paint mCalibrationPaint; //刻度Paint
    private Paint mCenterTextPaint; //居中文字Paint
    private Paint mTimeAreaPaint; //选择时间Paint
    private Paint mInvalidAreaPaint; //无效时间区域遮罩Paint
    private Paint.FontMetricsInt mCenterTextFontMetricsInt;

    private String[] dayText;

    //所有大小都是 DIP
    private int mHeight;
    private int mWidth;
    private int mHoursPerScreen = 6; //每屏显示6h
    private int mCalibrationUnit = 15; //每刻度表示15min
    private float mCalibrationHeight = 9; //刻度高
    private float mCalibrationHeightHigher = 16; //小时的刻度高
    private float mBoundLineWidth = 0; //边框线宽
    private float mCenterTextMargin = 10; //文字上下 margin
    private float mCalibrationDistance; //刻度间距
    private float mCenterTextSize = 18; //文字大小
    private int mStartEndTimeDistance = 60; //min
    private float mMaxOverScrollDistance; //最大 OverScroll距离

    private float mCalibrationWidth = 2; //刻度宽 px

    //数据
    private boolean mIsSelectStart = true; //true:正在选择开始时间
    private boolean mHasEndChoose = false; //默认是否画结束时间的标识
    private int mStartTime; //分钟
    private int mEndTime;
    private float mLeftBound; //滑动的左右边界
    private float mRightBound;
    private int mRealCenterTime = 0;
    private float density;
    private GradientDrawable topLinearGradient;
    private GradientDrawable btmLinearGradient;

    public TimeWheelView(Context context) {
        this(context, null);
    }

    public TimeWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dayText = getResources().getStringArray(R.array.day_text);
        //尺寸设置
        density = getResources().getDisplayMetrics().density;
        mCenterTextMargin = density * mCenterTextMargin;
        mCenterTextSize = density * mCenterTextSize;
        mCalibrationHeight = density * mCalibrationHeight;
        mCalibrationHeightHigher = density * mCalibrationHeightHigher;
//        mCalibrationWidth = density * mCalibrationWidth;
        mBoundLineWidth = density * mBoundLineWidth;

        //辅助
        mScroller = new OverScroller(getContext(), new DecelerateInterpolator(4f));
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), this);

        //paint 设置
        topLinearGradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0x11000000, 0x00000000});
        btmLinearGradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0x00000000, 0x11000000});

        mBackgoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgoundPaint.setStyle(Paint.Style.FILL);
        mBackgoundPaint.setColor(0xFF4ABEA7);

        mBoundLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundLinePaint.setStrokeWidth(mBoundLineWidth);
        mBoundLinePaint.setColor(0xFF888888);

        //开始时间线
        mStartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStartLinePaint.setStrokeWidth(density * 3);//2dp

        //结束时间线
        mEndLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEndLinePaint.setStrokeWidth(density * 2); //1dp
        setTimeSelectLineColor(Color.RED);//默认颜色

        //刻度线
        mCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationPaint.setStrokeWidth(mCalibrationWidth);
        mCalibrationPaint.setColor(0xFF86E2D0);

        //文字
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);
        mCenterTextPaint.setTextSize(mCenterTextSize);
        mCenterTextPaint.setColor(0xFF95F3E0);
        mCenterTextFontMetricsInt = mCenterTextPaint.getFontMetricsInt();

        //时间选择区域
        mTimeAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeAreaPaint.setColor(0xFF1C967E);
        mTimeAreaPaint.setAlpha((int) (255 * 0.5));

        mInvalidAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInvalidAreaPaint.setColor(0xBBFFFFFF);

        mStartTime = 0;
        mEndTime = mStartTime + mStartEndTimeDistance;
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
        int result = (int) (mCalibrationHeight * 2 + mCenterTextSize + mCenterTextMargin * 2);
        switch (measureMode) {
            case MeasureSpec.EXACTLY:
                result = Math.max(result, measureSize);
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, measureSize);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mWidth = w;
            mHeight = h;
            mMaxOverScrollDistance = w / 4f;
            mCalibrationDistance = w / (mHoursPerScreen * 60 / mCalibrationUnit);
//            mCalibrationDistance = 10 * density;
            mLeftBound = 0;
            mRightBound = 24 * 60 / mCalibrationUnit * mCalibrationDistance;

            MLog.e( "mWidth: " + mWidth + "mHeight: " + mHeight+ "mMaxOverScrollDistance: " + mMaxOverScrollDistance);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int scrollX = getScrollX();

        //背景,内阴影和上下边框
        canvas.drawRect(scrollX, 0, scrollX + mWidth, mHeight, mBackgoundPaint);
        topLinearGradient.setBounds(scrollX, 0, scrollX + mWidth, (int) (mCalibrationHeight * 2 / 3));
        topLinearGradient.draw(canvas);

        btmLinearGradient.setBounds(scrollX, mHeight - (int) (mCalibrationHeight * 2 / 3), scrollX + mWidth, mHeight);
        btmLinearGradient.draw(canvas);
//        canvas.drawLine(scrollX, mBoundLineWidth / 2, scrollX + mWidth, mBoundLineWidth / 2, mBoundLinePaint);
//        canvas.drawLine(scrollX, mHeight - mBoundLineWidth / 2, scrollX + mWidth, mHeight - mBoundLineWidth / 2, mBoundLinePaint);

        canvas.save();
//        canvas.translate(mWidth / 2, 0);
        //MLog.d("scrollX: " + scrollX);
        double floor = Math.floor(scrollX / mCalibrationDistance);
        int mCenterPos = mWidth/2+scrollX;
//        canvas.drawColor(Color.WHITE);
        //画小刻度
//        int calibrationCount = mHoursPerScreen / 2 * 60 / mCalibrationUnit + 1; //每侧画多少个刻度
        int calibrationCount = (int) (mWidth / 2 / mCalibrationDistance) + 1; //每侧画多少个刻度
        for (int i = 0; i < 5; i++) {
            float offsetX = i * mCalibrationDistance;
            float tempCalibrationHeight;
            //右边的刻度
            if ((i + floor) % (60 / mCalibrationUnit) == 0) {
                //整小时的刻度长一点
                tempCalibrationHeight = mCalibrationHeightHigher;
            } else {
                tempCalibrationHeight = mCalibrationHeight;
            }
            canvas.drawLine(mCenterPos + offsetX, mBoundLineWidth, mCenterPos + offsetX, mBoundLineWidth + tempCalibrationHeight, mCalibrationPaint);
            canvas.drawLine(mCenterPos + offsetX, mHeight - mBoundLineWidth * 2 - tempCalibrationHeight, mCenterPos + offsetX, mHeight - mBoundLineWidth, mCalibrationPaint);


            //左边的刻度
            if ((i - floor) % (60 / mCalibrationUnit) == 0) {
                //整小时的刻度长一点
                tempCalibrationHeight = mCalibrationHeightHigher;
            } else {
                tempCalibrationHeight = mCalibrationHeight;
            }
            canvas.drawLine(mCenterPos - offsetX, mBoundLineWidth, mCenterPos + -offsetX, mBoundLineWidth + tempCalibrationHeight, mCalibrationPaint);
            canvas.drawLine(mCenterPos - offsetX, mHeight - mBoundLineWidth * 2 - tempCalibrationHeight, mCenterPos - offsetX, mHeight - mBoundLineWidth, mCalibrationPaint);

        }

        //text
        int textPosY = (mHeight - mCenterTextFontMetricsInt.bottom - mCenterTextFontMetricsInt.top) / 2;
        canvas.drawText(dayText[0], mRightBound / 8, textPosY, mCenterTextPaint);
        canvas.drawText(dayText[1], 3 * mRightBound / 8, textPosY, mCenterTextPaint);
        canvas.drawText(dayText[2], 5 * mRightBound / 8, textPosY, mCenterTextPaint);
        canvas.drawText(dayText[3], 7 * mRightBound / 8, textPosY, mCenterTextPaint);


        canvas.restore();

//        //无效区域遮罩
//        if (scrollX < mWidth / 2) {
//            canvas.drawRect(scrollX, 0, mWidth / 2, mHeight, mInvalidAreaPaint);
//        }
//        if (scrollX > mRightBound - mWidth / 2) {
//            canvas.drawRect(mRightBound + mWidth / 2, 0, scrollX + mWidth, mHeight, mInvalidAreaPaint);
//        }

        canvas.translate(mWidth / 2 + scrollX, 0);
        //中间线
        canvas.drawLine(0, mBoundLineWidth, 0, mHeight - mBoundLineWidth, mIsSelectStart ? mStartLinePaint : mEndLinePaint);

        //没有选择过结束时间,只画中间一条线
        if (mHasEndChoose) { //选择 EndTime, 还需要画起始时间线

            int areaOffset = 0;
            if (mIsSelectStart) {
                if (mEndTime == 24 * 60) {
                    areaOffset = convetTimeToScrollX(mEndTime - mRealCenterTime);

                } else {
                    areaOffset = convetTimeToScrollX(mEndTime - mStartTime);
                }
            } else {
                areaOffset = convetTimeToScrollX(mStartTime - mRealCenterTime);
            }

            //MLog.d("mRealCenterTime: " + mRealCenterTime + " areaOffset: " + areaOffset);
            if (Math.abs(areaOffset) < mWidth / 2) {//在屏幕内
                canvas.drawLine(areaOffset, mBoundLineWidth, areaOffset, mHeight - mBoundLineWidth, mIsSelectStart ? mEndLinePaint : mStartLinePaint);

                if (areaOffset > 0) {
                    canvas.drawRect(0, mBoundLineWidth, areaOffset, mHeight - mBoundLineWidth, mTimeAreaPaint);
                } else {
                    canvas.drawRect(areaOffset, mBoundLineWidth, 0, mHeight - mBoundLineWidth, mTimeAreaPaint);
                }
            } else {
                if (areaOffset > 0) {
                    canvas.drawRect(0, mBoundLineWidth, mWidth / 2, mHeight - mBoundLineWidth, mTimeAreaPaint);
                } else {
                    canvas.drawRect(-mWidth / 2, mBoundLineWidth, 0, mHeight - mBoundLineWidth, mTimeAreaPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = mGestureDetectorCompat.onTouchEvent(event);
        if (!mIsFling && MotionEvent.ACTION_UP == event.getAction()) {
            fixCenterPostion();
            ret = true;
        }
        return ret || super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            refreshCenter();
            invalidate();
        } else {
            if (mIsFling) {
                mIsFling = false;
                fixCenterPostion();
            }
        }
    }

    public void fling(int velocityX, int velocityY) {
        mScroller.fling(getScrollX(), getScrollY(),
                velocityX, velocityY,
                (int) mLeftBound, (int) (mRightBound),
                0, 0,
                (int) mMaxOverScrollDistance, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void fixCenterPostion() {
        int scrollX = getScrollX();
        if (scrollX < mLeftBound) {
            mScroller.startScroll(scrollX, 0, (int) (mLeftBound - scrollX), 0);
        } else if (scrollX > mRightBound) {
            mScroller.startScroll(scrollX, 0, (int) (mRightBound - scrollX), 0);
        } else {
            float dx = (Math.round(scrollX / mCalibrationDistance) * mCalibrationDistance);
            mScroller.startScroll(scrollX, 0, (int) dx - scrollX, 0);
        }
        postInvalidate();
    }

    private void refreshCenter() {
        refreshCenter(getScrollX());
    }

    private void refreshCenter(int scrollX) {
        int calibrationCount = Math.round(scrollX / mCalibrationDistance);
        int oldStartTime = mStartTime;
        int oldEndTime = mEndTime;
        mRealCenterTime = (int) (scrollX / (mCalibrationDistance / mCalibrationUnit));
//        MLog.d("calibrationCount: " + calibrationCount);
        if (mIsSelectStart) {
            mStartTime = validTime(calibrationCount * mCalibrationUnit);
            mEndTime = validTime(mStartTime + mStartEndTimeDistance);
        } else {
            mEndTime = validTime(calibrationCount * mCalibrationUnit);
            mStartEndTimeDistance = mEndTime - mStartTime;
        }

        if (oldStartTime != mStartTime || oldEndTime != mEndTime) {
            if (onTimeSelectedListener != null) {
                onTimeSelectedListener.onTimeSelected(mStartTime, 0, mEndTime, 0);
            }
        }


//        MLog.d("time: " + minToTimeString(mStartTime));

    }

    /**
     * 滚到中间位置显示的时间
     *
     * @param min
     */
    private void scrollToTime(int min) {
        int scrollX = getScrollX();
        mScroller.startScroll(scrollX, 0, convetTimeToScrollX(min) - scrollX, 0);
        postInvalidate();
    }

    /**
     * GestureDetector.OnGestureListener 实现
     */
    @Override
    public boolean onDown(MotionEvent e) {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        mIsFling = false;
        if (null != getParent()) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float dis = distanceX;
        float scrollX = getScrollX();
//
//        MLog.d("onScroll", "distanceX: " + distanceX);
//        MLog.d("onScroll", "getScrollX: " + scrollX);

        //模拟阻尼效果
        if (scrollX < mLeftBound - mMaxOverScrollDistance * 2) {
            dis = 0;
        } else if (scrollX < mLeftBound - mMaxOverScrollDistance) {
            dis = distanceX / 4f;
        } else if (scrollX < mLeftBound - mMaxOverScrollDistance / 2) {
            dis = distanceX / 2f;
        } else if (scrollX > mRightBound + mMaxOverScrollDistance * 2) {
            dis = 0;
        } else if (scrollX > mRightBound + mMaxOverScrollDistance) {
            dis = distanceX / 4f;
        } else if (scrollX > mRightBound + mMaxOverScrollDistance / 2) {
            dis = distanceX / 2f;
        }
        scrollBy((int) dis, 0);
        refreshCenter();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float scrollX = getScrollX();
        if (scrollX < mLeftBound || scrollX > mRightBound) {
            return false;
        } else {
            mIsFling = true;
            fling((int) -velocityX, 0);
            return true;
        }
    }

    /**
     * 切换选择开始还是结束
     *
     * @param isSelectStart
     */
    public void switchStartOREnd(boolean isSelectStart) {
        this.mIsSelectStart = isSelectStart;
        mLeftBound = isSelectStart ? 0 : convetTimeToScrollX(mStartTime);
        if (isSelectStart) {
            scrollToTime(mStartTime);
        } else {
            scrollToTime(mEndTime);
        }
    }

    private int validTime(int min) {
        if (min < 0) {
            return 0;
        }
        if (min > 24 * 60) {
            return 24 * 60;
        }
        int calibrationNum = Math.round(min / mCalibrationUnit);
        return calibrationNum * mCalibrationUnit;
    }

    private int convetTimeToScrollX(float min) {
        return (int) (min / mCalibrationUnit * mCalibrationDistance);
    }

//
//    private int convertScrollXToTime(float scrollX) {
//
//    }


    /**
     * 对外接口及回调
     */

    public interface OnTimeSelectedListener {
        void onTimeSelected(int startTimeInMin, int startDayOffset, int endTimeInMin, int endDayOffset);
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener listener) {
        this.onTimeSelectedListener = listener;
    }

    public int getStartTime() {
        return mStartTime;
    }

    public int getEndTime() {
        return mEndTime;
    }

    /**
     * 根据 mStartEndTimeDistance
     * 重置到最开始的状态(只显示开始时间的竖线)
     *
     * @param sHour
     * @param sMin
     */
    public void setTime(
            @IntRange(from = 0, to = 24) int sHour,
            @IntRange(from = 0, to = 59) int sMin) {

        mHasEndChoose = false;
        mStartTime = validTime((sHour * 60 + sMin));
        mEndTime = mStartTime + mStartEndTimeDistance;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                switchStartOREnd(true);
                if (onTimeSelectedListener != null) {
                    onTimeSelectedListener.onTimeSelected(mStartTime, 0, mEndTime, 0);
                }
            }
        }, 100);
    }

    public void setTime(
            @IntRange(from = 0, to = 24) int sHour,
            @IntRange(from = 0, to = 59) int sMin,
            @IntRange(from = 0, to = 24) int eHour,
            @IntRange(from = 0, to = 59) int eMin) {

        mHasEndChoose = false;
        mStartTime = validTime((sHour * 60 + sMin));
        mEndTime = validTime((eHour * 60 + eMin));
        if (mEndTime < mStartTime) {
            throw new InvalidParameterException("参数错误");
        }
        mStartEndTimeDistance = mEndTime - mStartTime;
        mHasEndChoose = true; //确定输入结束时间, 则会画出选择区间
        postDelayed(new Runnable() {
            @Override
            public void run() {
                switchStartOREnd(true);
                if (onTimeSelectedListener != null) {
                    onTimeSelectedListener.onTimeSelected(mStartTime, 0, mEndTime, 0);
                }
            }
        }, 100);
    }

    /**
     * 选择开始时间
     */
    public void selectStart() {
        switchStartOREnd(true);
    }

    /**
     * 选择结束时间
     */
    public void selectEnd() {
        switchStartOREnd(false);
        mHasEndChoose = true;
    }

    /**
     * 设置选择时间线的颜色, 和优先级颜色对应
     *
     * @param color
     */
    public void setTimeSelectLineColor(@ColorInt int color) {
        mStartLinePaint.setColor(color);
        mEndLinePaint.setColor(color);
        invalidate();
    }
}
