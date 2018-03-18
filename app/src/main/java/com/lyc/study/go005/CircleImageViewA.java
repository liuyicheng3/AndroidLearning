package com.lyc.study.go005;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.lyc.study.R;

public class CircleImageViewA extends View {

    public CircleImageViewA(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CircleImageViewA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageViewA(Context context) {
        super(context);
    }

    private Bitmap bitmap;
    private Rect bitmapRect=new Rect();
    private PaintFlagsDrawFilter pdf=new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    private Paint paint = new Paint();
    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
    }
    private Path mPath=new Path();

    public void setImageBitmap(Bitmap bitmap)
    {
        this.bitmap=bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        bitmap =  ((BitmapDrawable) getResources().getDrawable(R.drawable.demo_lulu01)).getBitmap();

        if(null==bitmap)
        {
            return;
        }

        bitmapRect.set(0, 0, getWidth(), getHeight());

        canvas.save();
        canvas.setDrawFilter(pdf);
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        mPath.addCircle(getWidth()/2, getWidth()/2, getHeight()/2, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        canvas.drawBitmap(bitmap, null, bitmapRect, paint);
        canvas.restore();
    }


}