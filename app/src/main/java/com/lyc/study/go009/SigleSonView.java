package com.lyc.study.go009;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by lyc on 18/3/20.
 */

public class SigleSonView extends TextView {

    public SigleSonView(Context context) {
        super(context);
    }

    public SigleSonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SigleSonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
