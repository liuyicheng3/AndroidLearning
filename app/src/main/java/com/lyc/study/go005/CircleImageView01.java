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
public class CircleImageView01  extends ImageView {
    public CircleImageView01(Context context) {
        super(context);
    }

    public CircleImageView01(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView01(Context context, AttributeSet attrs, int defStyle) {
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

        //创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas drawCanvas=new Canvas(bitmap);


        BitmapDrawable drawable= (BitmapDrawable) getResources().getDrawable(R.drawable.demo_lulu01);
        int mBitWidth=drawable.getBitmap().getWidth();
        int mBitHeight=drawable.getBitmap().getHeight();
        Rect mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        Rect mDestRect = new Rect(0, 0, getWidth(), getHeight());


        drawCanvas.drawBitmap(drawable.getBitmap(),mSrcRect,mDestRect,null);

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));  //因为我们先画了图所以DST_IN

        int radius = mDestRect.width()/2; //假设图片是正方形的
        canvas.drawBitmap(bitmap,0,0,new Paint());
        canvas.drawCircle(radius, radius, 200, p); //r=radius, 圆心(r,r)</code>)


    }
}
