package com.lyc.common;

import android.util.Log;

/**
 * Created by lyc on 2015/12/31.
 */
public class Mlog {

    public static void e(String str){
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final int i = 1;
        final StackTraceElement ste = stack[i];

        Log.e("lyc",String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), str.toString()));
    }
}
