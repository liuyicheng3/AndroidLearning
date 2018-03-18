package com.lyc.study.go005;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
public class XfermodeView1 extends ImageView {
    public XfermodeView1(Context context) {
        super(context);
    }

    public XfermodeView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodeView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * http://www.cnblogs.com/rayray/p/3670120.html
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        int defaultWidth = getWidth(); //xml里view的宽度是85dp
        int defaultdHeight = getHeight(); //xml里view的高度是85dp
        Drawable drawable = getResources().getDrawable(R.drawable.demo_lulu01);

        if (canvas.getHeight() == defaultWidth && canvas.getHeight() == defaultdHeight && drawable instanceof BitmapDrawable) {
            //setBackgroundColor(Color.TRANSPARENT);
            //拿到原图的bitmap
            Bitmap bitimg = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasimg = new Canvas(bitimg);
            Matrix matrix = new Matrix();
            matrix.setScale(defaultWidth * 1.0f / drawable.getIntrinsicWidth(), defaultdHeight * 1.0f / drawable.getIntrinsicHeight());
            Paint paintimg = new Paint(Paint.ANTI_ALIAS_FLAG);
            canvasimg.drawBitmap(((BitmapDrawable)drawable).getBitmap(), matrix, paintimg);

            //拿到圆形的bitmap
            Bitmap bitcircle = Bitmap.createBitmap(defaultWidth, defaultdHeight, Bitmap.Config.ARGB_8888);
            Canvas canvascircle = new Canvas(bitcircle);
            Paint paintcircle = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintcircle.setColor(0xFF66AAFF);
            canvascircle.drawCircle(defaultWidth/2, defaultdHeight/2, defaultWidth/2, paintcircle);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

            //采用saveLayer，让后续canvas的绘制在自动创建的bitmap上
            int cnt = canvas.saveLayer(0, 0, defaultWidth, defaultdHeight, null, Canvas.ALL_SAVE_FLAG);
            //先画原图，原图是dest
            canvas.drawBitmap(bitimg, 0, 0, paint);
            Paint p_0 = new Paint();
            p_0.setColor(Color.RED);
            canvas.drawRect(getWidth()*1/2,getHeight()*1/2,getWidth(),getHeight(),p_0);
            paint.setXfermode(xfermode);

//            canvas.drawRect(getWidth()*1/3,getHeight()*1/3,getWidth(),getHeight(),paint);

            //后画圆形，圆形是src
            canvas.drawBitmap(bitcircle, 0, 0, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(cnt);
        } else {
            super.onDraw(canvas);
        }
    }
}
