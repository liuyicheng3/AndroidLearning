package com.lyc.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.lyc.study.R;

/**
 * Created by lyc on 17/6/29.
 */

public class CustomDialog extends Dialog {
    TextView tv;
    public CustomDialog(@NonNull Context context) {
        super(context, R.style.no_background_dialog);
        tv =new TextView(context);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(10);
        setContentView(tv);
    }

    public void show(String str){
        tv.setText(str);
        show();

    }
}
