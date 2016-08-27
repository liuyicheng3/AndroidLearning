package com.lyc.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lyc on 2015/12/31.
 */
public class UtilsManager {

    public static  void toast(Context ctx,String str){
        Toast.makeText(ctx,str,Toast.LENGTH_SHORT);
    }
    public static  void toast(Context ctx,int strRes){
        Toast.makeText(ctx,strRes,Toast.LENGTH_SHORT);
    }
}
