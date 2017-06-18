package com.lyc.study.go011;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import com.lyc.common.UtilsManager;
import com.lyc.study.R;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;

/**
 *
 * 通过扩展 SimpleTextCacheStuffer 或 SpannedCacheStuffer 个性化弹幕样式
 */

public class BackgroundCacheStuffer extends SpannedCacheStuffer {

    private Context mContext;

    public BackgroundCacheStuffer(Context context) {
        this.mContext = context;
    }

    private Paint paint;

    @Override
    public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
        super.measure(danmaku, paint, fromWorkerThread);
    }

    @Override
    public void drawText(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, TextPaint paint, boolean fromWorkerThread) {
        super.drawText(danmaku, lineText, canvas, left, top, paint, fromWorkerThread);
    }

    @Override
    protected void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
        final int padding = UtilsManager.dip2px(mContext, 5);
        if (paint == null){
            paint = new Paint();
        }
        paint.setColor(mContext.getResources().getColor(R.color.color_f1f1f1));
        RectF rect = new RectF(left, top+padding, left + danmaku.paintWidth, top + danmaku.paintHeight-padding);
        canvas.drawRoundRect(rect, danmaku.paintHeight/2, danmaku.paintHeight/2, paint);
        super.drawBackground(danmaku, canvas, left, top);
    }
}