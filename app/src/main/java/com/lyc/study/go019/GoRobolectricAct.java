package com.lyc.study.go019;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lyc.study.R;

public class GoRobolectricAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_robolectric_01);

        TextView textView = (TextView)findViewById(R.id.textView1);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoRobolectricAct.this, RobolectricSecondActivity.class));
            }
        });
    }

}
