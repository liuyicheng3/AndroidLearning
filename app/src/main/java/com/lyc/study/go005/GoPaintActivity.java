package com.lyc.study.go005;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lyc.study.R;

/**
 * Created by lyc on 16/5/2.
 */
public class GoPaintActivity  extends Activity {
    private ImageView iv_circle1,iv_circle2,iv_circle3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        initView();



    }


    private void initView() {
        iv_circle1 = (ImageView) findViewById(R.id.iv_circle1);
        iv_circle2 = (ImageView) findViewById(R.id.iv_circle2);
        iv_circle3 = (ImageView) findViewById(R.id.iv_circle3);
    }
}
