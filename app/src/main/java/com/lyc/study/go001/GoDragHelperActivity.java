package com.lyc.study.go001;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lyc.common.Mlog;
import com.lyc.study.R;

/**
 * Created by lyc on 17/9/29.
 */

public class GoDragHelperActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_drag);
        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mlog.e("click");
            }
        });


    }




}
