package com.lyc.study.go013;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lyc.study.R;

/**
 * Created by lyc on 17/5/1.
 */

public class GoHookActivity01 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_01);
        findViewById(R.id.btn_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoHookActivity01.this, GoHookActivity02.class);
                startActivity(intent);
            }
        });
    }
}
