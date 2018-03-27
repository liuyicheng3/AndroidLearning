package com.lyc.study.go008;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

/**
 * Created by lyc on 16/11/25.
 */

public class GoEventActivity  extends Activity {

    private View vg_0,vg_1,cv;

    private CircleProgressView  cpv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initView();
    }

    private void initView() {
        vg_0= findViewById(R.id.vg_0);
        vg_0.invalidate();



        vg_1= findViewById(R.id.vg_1);
        cv = findViewById(R.id.cv);
        /*vg_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UtilsManager.toast(GoEventActivity.this,"click");
                Mlog.e("click");
            }
        });*/
        vg_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mlog.e("trigger");
                UtilsManager.toast(GoEventActivity.this,"trigger");
            }
        });

        /*vg_1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction()==MotionEvent.ACTION_UP)
                Mlog.e("click");
            return false;
        });*/
        vg_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_UP)
                    Mlog.e("click");
                return false;
            }
        });

        cpv = (CircleProgressView) findViewById(R.id.cpv);
        cpv.setProgress(10);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Mlog.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Mlog.e("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Mlog.e("onDestroy");
    }
}
