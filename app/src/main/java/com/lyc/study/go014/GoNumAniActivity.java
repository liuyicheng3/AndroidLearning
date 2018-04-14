package com.lyc.study.go014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyc.study.R;
import com.lyc.study.go014.ticker.TickerView;


public class GoNumAniActivity extends Activity {

    private TickerView ticker1, ticker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_num_ani);

        ticker1 = (TickerView) findViewById(R.id.ticker1);
        ticker1.setText("我们1231.45123");
//        ticker2 = (TickerView) findViewById(R.id.ticker2);
//        ticker2.setText("我们99.45");


        findViewById(R.id.perfBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticker1.setText("FH99999");
            }
        });
    }


}
