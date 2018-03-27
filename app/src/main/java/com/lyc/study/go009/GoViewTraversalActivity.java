package com.lyc.study.go009;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

/**
 * Created by lyc on 17/1/29.
 */

public class GoViewTraversalActivity extends Activity {

    private View vg_top, vg_son, vg_inner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_go_view_tranversal);
        StringBuilder  sb =new StringBuilder();
        sb.append("200dp："+ UtilsManager.dip2px(this,200)+" ~ ");
        sb.append("100dp："+ UtilsManager.dip2px(this,100)+" ~ ");
        sb.append("50dp："+ UtilsManager.dip2px(this,50)+" ~ ");
        sb.append("20dp："+ UtilsManager.dip2px(this,20)+" ~ ");
        Mlog.e(sb.toString());
        vg_top= findViewById(R.id.vg_top);
        vg_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mlog.e("vg_top onClick");
                UtilsManager.toast(GoViewTraversalActivity.this,"top click");
            }
        });

       vg_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Mlog.e("vg_top onTouch");
                return true;
            }
        });

        vg_son= findViewById(R.id.vg_son);
        vg_son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mlog.e("vg_son onClick");
                UtilsManager.toast(GoViewTraversalActivity.this,"son click");
            }
        });
        vg_inner= findViewById(R.id.vg_inner);

    }
}
