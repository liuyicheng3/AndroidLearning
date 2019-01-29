package com.lyc.study.go001;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.lyc.common.MLog;


/**
 * Created by lyc on 2015/1/10.
 */
public class InnerTopScrollView extends ScrollView {
	public VerticalPagerView parentScrollView;
	int mTop = 10;
	int currentY;

	public InnerTopScrollView(Context context) {
		super(context);
	}

	public InnerTopScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerTopScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 
	 * @param flag
	 */
	private void setParentScrollAble(boolean flag) {
		MLog.e("flag" + flag);
		parentScrollView.requestDisallowInterceptTouchEvent(!flag);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (parentScrollView == null) {
			return super.onInterceptTouchEvent(ev);
		} else {
			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				currentY = (int) ev.getY();
				setParentScrollAble(false);
				return super.onInterceptTouchEvent(ev);
			} else if (ev.getAction() == MotionEvent.ACTION_UP) {
				setParentScrollAble(true);
			} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			}
		}
		return super.onInterceptTouchEvent(ev);

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		MLog.e("scrollView  ontouch  "+ev.getAction());
		View child = getChildAt(0);
		if (parentScrollView != null) {
			if (ev.getAction() == MotionEvent.ACTION_MOVE) {
				int height = child.getMeasuredHeight();
				height = height - getMeasuredHeight();
				int scrollY = getScrollY();
				int y = (int) ev.getY();
				if (currentY < y) {
					if (scrollY <= 0) {
						setParentScrollAble(true);
						return false;
					} else {
						setParentScrollAble(false);
					}
				} else if (currentY > y) {
					if (scrollY >= height) {
						setParentScrollAble(true);
						return false;
					} else {
						setParentScrollAble(false);
					}
				}
				currentY = y;
			}
		}

		return super.onTouchEvent(ev);
	}
}
