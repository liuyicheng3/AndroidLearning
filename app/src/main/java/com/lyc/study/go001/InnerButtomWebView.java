package com.lyc.study.go001;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lyc.common.Mlog;


/**
 * lyc
 */
public class InnerButtomWebView extends WebView {

	public VerticalPagerView parentScrollView;
	private WebViewClient myWebclient = null;
    private int mTouchSlop = 0;

	public InnerButtomWebView(Context context) {
		super(context);
        init();
	}

	public InnerButtomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
        init();
	}

	public InnerButtomWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init();
	}

	@Override
	public void setWebViewClient(WebViewClient mywebclient) {
		this.myWebclient = mywebclient;
	}
	
    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(this.getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        this.setLongClickable(false);
        super.setWebViewClient(webClient);
    }

    public ScrollInterface mScrollInterface;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollInterface.onSChanged(l, t, oldl, oldt);
    }

    public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {
        this.mScrollInterface = scrollInterface;
    }

    public interface ScrollInterface {
        public void onSChanged(int l, int t, int oldl, int oldt);
    }


    WebViewClient webClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (myWebclient != null) {
                myWebclient.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (myWebclient != null) {
                return myWebclient.shouldOverrideUrlLoading(view, url);
            } else {
                return false;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (myWebclient != null) {
                myWebclient.onPageFinished(view, url);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

        }

    };

	/**
	 * 
	 * @param flag
	 */
	private void setParentScrollAble(boolean flag) {
		Mlog.e("flag" + flag);
		parentScrollView.requestDisallowInterceptTouchEvent(!flag);
	}


    private float mInterceptLastMotionX = 0;
    private float mInterceptLastMotionY = 0;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

        Mlog.e("onInterceptTouchEvent  "+ev.getAction());
        float x = ev.getX();
        float y = ev.getY();

        if (parentScrollView == null) {
			return super.onInterceptTouchEvent(ev);
		} else {

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

    private float mTouchLastMotionX = 0;
    private float mTouchLastMotionY = 0;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Mlog.e("ontouch  "+ev.getAction());
		if (parentScrollView != null){

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

				boolean isTop=false;
				boolean isButtom=false;

                if((int)(this.getContentHeight()* this.getScale()) -( this.getHeight()+ this.getScrollY())<=10){
                    Mlog.e("remain"+(getContentHeight()* this.getScale() -( this.getHeight()+ this.getScrollY())));
					isButtom=true;
				}else{
                    Mlog.e("remain"+(getContentHeight()* this.getScale() -( this.getHeight()+ this.getScrollY())));
                    isButtom=false;
				}
				if(this.getScrollY()==0){
					isTop=true;
				}else{
					isTop=false;
				}
				if (mTouchLastMotionY < y) {//手指从上往下拉
					if (isTop) {
						setParentScrollAble(true);
					} else {
						setParentScrollAble(false);
					}
				}
                else if (mTouchLastMotionY > y) {//手指从下往上推
					if (isButtom) {
						setParentScrollAble(true);
					} else {
						setParentScrollAble(false);
					}
				}
                mTouchLastMotionX = x;
                mTouchLastMotionY = y;
			}else if(ev.getAction() == MotionEvent.ACTION_CANCEL){

			}
		}

		return super.onTouchEvent(ev);
	}
}
