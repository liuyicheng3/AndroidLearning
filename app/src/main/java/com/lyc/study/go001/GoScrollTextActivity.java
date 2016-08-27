package com.lyc.study.go001;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.lyc.study.R;

public class GoScrollTextActivity extends Activity {
    ScrollView scrollView1;

    InnerScrollView innerScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inner_scroll);
        scrollView1 = (ScrollView)findViewById(R.id.scroll_1);
        innerScrollView = (InnerScrollView)findViewById(R.id.scroll_2);
        innerScrollView.parentScrollView = scrollView1;
        final Button button = (Button)innerScrollView.findViewById(R.id.scroll_button2);
        final View content = findViewById(R.id.scroll_content);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (content.getVisibility() == View.VISIBLE) {
                    innerScrollView.resume();
                    content.setVisibility(View.GONE);
                } else {
                    innerScrollView.scrollTo(v);
                    content.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
