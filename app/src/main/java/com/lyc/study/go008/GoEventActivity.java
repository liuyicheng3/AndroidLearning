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

    private View vg_0,vg_1;

    private CircleProgressView  cpv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initView();
    }

    private void initView() {
        vg_1= findViewById(R.id.vg_1);

        /*vg_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UtilsManager.toast(GoEventActivity.this,"click");
                Mlog.e("click");
            }
        });*/
        vg_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP)
                    Mlog.e("click");
                    return false;

            }
        });

        vg_1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction()==MotionEvent.ACTION_UP)
                Mlog.e("click");
            return false;
        });

        cpv = (CircleProgressView) findViewById(R.id.cpv);
        cpv.setProgress(10);
    }
}
