package com.lyc.study.go021;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyc on 18/5/17.
 */

public class MutiView extends View {

    TextPaint tp = new TextPaint();
    TextPaint bp = new TextPaint();
    public MutiView(Context context) {
        super(context);
        init(context);
    }

    public MutiView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        tp.setColor(Color.BLUE);
        tp.setStyle(Paint.Style.FILL);
        tp.setTextSize(50);

        bp.setColor(Color.BLACK);
        bp.setStyle(Paint.Style.FILL);
        bp.setTextSize(50);
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        String message = "A";
        StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
    }
}