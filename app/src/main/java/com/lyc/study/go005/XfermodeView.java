package com.lyc.study.go005;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lyc.study.R;

/**
 * Created by lyc on 16/5/2.
 */
public class XfermodeView extends ImageView {
    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * http://www.cnblogs.com/rayray/p/3670120.html
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
        canvas.save();

        //拿到圆形的bitmap
        Bitmap bitcircle = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvascircle = new Canvas(bitcircle);
        Paint paintcircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintcircle.setColor(0xFF66AAFF);
        canvascircle.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, paintcircle);

        //采用saveLayer，让后续canvas的绘制在自动创建的bitmap上
        int cnt = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Paint paintA = new Paint();
        paintA.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth()*1/2,getHeight()*1/2,paintA);
        paintA.setColor(Color.RED);
        canvas.drawRect(getWidth()*1/2,0,getWidth(),getHeight()*1/2,paintA);

        paintA = new Paint();
        paintA.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitcircle, 0, 0, paintA);
        canvas.restoreToCount(cnt);
    }
}
