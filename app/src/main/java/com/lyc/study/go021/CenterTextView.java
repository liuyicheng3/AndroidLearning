package com.lyc.study.go021;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.lyc.common.MLog;

/**
 * Created by lyc on 18/5/16.
 */

public class CenterTextView extends View {
    private Paint mPaint;
    Paint.FontMetricsInt fontMetrics;
    private int mWidth,mHeight;

    public CenterTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }



    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setColor(Color.GRAY);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        fontMetrics = mPaint.getFontMetricsInt();

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
//        canvas.clipRect(0,0,100,100, Region.Op.UNION);
        canvas.drawText("ABCDEFG", 0, mHeight / 2 - (fontMetrics.bottom - fontMetrics.top)
                / 2 - fontMetrics.top, mPaint);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mWidth = w;
            mHeight = h;
            MLog.e("mWidth: " + mWidth + "mHeight: " + mHeight );
        }
    }


}