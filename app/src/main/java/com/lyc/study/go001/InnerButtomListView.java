package com.lyc.study.go001;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.lyc.common.Mlog;


/**
 * chenlong
 */
public class InnerButtomListView extends ListView {

	public VerticalPagerView parentScrollView;
    private int mTouchSlop = 0;

	public InnerButtomListView(Context context) {
		super(context);
        init();
	}

	public InnerButtomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
        init();
	}

	public InnerButtomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init();
	}

    private void init(){
        ViewConfiguration configuration = ViewConfiguration.get(this.getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

	/**
	 * 
	 * @param flag
	 */
	private void setParentScrollAble(boolean flag) {
		Mlog.e("flag" + flag);
		parentScrollView.requestDisallowInterceptTouchEvent(!flag);
	}


    float mInterceptLastMotionX = 0;
    float mInterceptLastMotionY = 0;

    @Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
        Mlog.e("onInterceptTouchEvent "+ev.getAction());

        float x = ev.getX();
        float y = ev.getY();

		if (parentScrollView == null) {
			return super.onInterceptTouchEvent(ev);
		} else {

            if (parentScrollView.getScrollY() == 0) {
                setParentScrollAble(true);
                return false;
            }

			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                mInterceptLastMotionX = ev.getX();
                mInterceptLastMotionY = ev.getY();
                setParentScrollAble(false);
			} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {

                final int xDiff = (int) Math.abs(x - mInterceptLastMotionX);
                final int yDiff = (int) Math.abs(y - mInterceptLastMotionY);
                boolean xMoved = (xDiff > mTouchSlop) && (xDiff > yDiff);
                boolean yMoved = (yDiff > mTouchSlop) && (yDiff > xDiff);

                if(xMoved){
                    setParentScrollAble(true);
                    return false;
                }

                mInterceptLastMotionX = x;
                mInterceptLastMotionY = y;

			} else if (ev.getAction() == MotionEvent.ACTION_UP) {
                setParentScrollAble(true);
            }
		}
		return super.onInterceptTouchEvent(ev);
	}


    float mTouchLastMotionX = 0;
    float mTouchLastMotionY = 0;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Mlog.e("ontouch "+ev.getAction());
		if (parentScrollView != null) {

            float x = ev.getX();
            float y = ev.getY();


            if (parentScrollView.getScrollY() == 0) {
                setParentScrollAble(true);
                return false;
            }

            if(ev.getAction() == MotionEvent.ACTION_DOWN){
                mTouchLastMotionX = x;
                mTouchLastMotionY = y;
            }

            else if (ev.getAction() == MotionEvent.ACTION_MOVE) {


                final int xDiff = (int) Math.abs(x - mTouchLastMotionX);
                final int yDiff = (int) Math.abs(y - mTouchLastMotionY);
                boolean xMoved = (xDiff > mTouchSlop) && (xDiff > yDiff);
                boolean yMoved = (yDiff > mTouchSlop) && (yDiff > xDiff);

                if(xMoved){
                    setParentScrollAble(true);
                    return super.onTouchEvent(ev);
                }

//				boolean isButtom=false;
				boolean isTop=false;

//				if (this.getLastVisiblePosition() == (this.getCount() - 1)) {
//					isButtom=true;
//				}else{
//					isButtom=false;
//				}

                if(this.getChildAt(0).getTop()>=0&&this.getFirstVisiblePosition()==0){
					isTop=true;
				}else{
					isTop=false;
				}
				if (mTouchLastMotionY < y) {
					if (isTop) {
						setParentScrollAble(true);
					} else {
						setParentScrollAble(false);
					}
				}
//                else if (mInterceptLastMotionY > y) {
//					if (isButtom) {
//						setParentScrollAble(true);
//					} else {
//						setParentScrollAble(false);
//					}
//				}
                mTouchLastMotionX = x;
                mTouchLastMotionY = y;
			}else if(ev.getAction() == MotionEvent.ACTION_CANCEL){

            }
		}

		return super.onTouchEvent(ev);
	}
	
}
