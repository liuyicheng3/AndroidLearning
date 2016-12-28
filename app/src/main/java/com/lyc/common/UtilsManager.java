package com.lyc.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lyc on 2015/12/31.
 */
public class UtilsManager {

    public static  void toast(Context ctx,String str){
        Toast.makeText(ctx,str,Toast.LENGTH_SHORT).show();
    }
    public static  void toast(Context ctx,int strRes){
        Toast.makeText(ctx,strRes,Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
