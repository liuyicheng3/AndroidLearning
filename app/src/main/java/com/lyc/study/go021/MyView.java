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

/**
 * Created by lyc on 18/5/17.
 */

public class MyView extends View {

    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.clipRect(0,0,100,100, Region.Op.UNION);
        TextPaint tp = new TextPaint();
        tp.setColor(Color.BLUE);
        tp.setStyle(Paint.Style.FILL);
        tp.setTextSize(50);
        String message = "paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p";
        StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
        TextPaint tp2 = new TextPaint();
        tp2.setColor(Color.RED);

        canvas.drawRect(0,0,100,100,tp2);

    }
}