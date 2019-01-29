package com.lyc.study.go021;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyc on 18/5/17.
 *
 * a. BoringLayout (单行纯文字文本)
 b. Staticlayout (多行复杂文本）
 c. DynamicLayout (多行可编辑复杂文本）

 */

public class StaticLayoutView extends View {

    private Layout layout;

    private int width ;
    private int height;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StaticLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticLayoutView(Context context) {
        super(context);
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
        if (this.layout.getWidth() != width || this.layout.getHeight() != height) {
            width = this.layout.getWidth();
            height = this.layout.getHeight();
            requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (layout != null) {
            layout.draw(canvas, null, null, 0);
        }
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layout != null) {
            setMeasuredDimension(layout.getWidth(), layout.getHeight());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
