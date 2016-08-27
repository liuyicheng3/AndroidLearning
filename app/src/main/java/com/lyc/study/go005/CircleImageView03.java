package com.lyc.study.go005;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lyc.study.R;

/**
 * Created by lyc on 16/5/2.
 */
public class CircleImageView03 extends ImageView {
    public CircleImageView03(Context context) {
        super(context);
    }

    public CircleImageView03(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView03(Context context, AttributeSet attrs, int defStyle) {
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




        BitmapDrawable drawable= (BitmapDrawable) getResources().getDrawable(R.drawable.demo_lulu01);
        int mBitWidth=drawable.getBitmap().getWidth();
        int mBitHeight=drawable.getBitmap().getHeight();
        Rect mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        Rect mDestRect = new Rect(0, 0, getWidth(), getHeight());
        int radius = mDestRect.width()/2; //假设图片是正方形的

        Path path = new Path();
        path.addCircle(radius, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);   //裁剪区域
        canvas.drawBitmap(drawable.getBitmap(), 0, 0, null);//把图画上去</code>




    }
}
