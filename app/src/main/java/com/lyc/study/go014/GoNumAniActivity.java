package com.lyc.study.go014;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lyc.study.R;
import com.lyc.study.go014.ticker.TickerView;

import java.util.concurrent.locks.ReadWriteLock;


public class GoNumAniActivity extends Activity {

    private TickerView ticker1, ticker2;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_num_ani);

        ticker1 = (TickerView) findViewById(R.id.ticker1);
        ticker1.setText("0000.00000人民币",false);
        ticker1.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_content.setVisibility(View.INVISIBLE);


        ticker1.post(new Runnable() {
            @Override
            public void run() {
                ticker1.setText("1234.56789人民币",true);
                ll_content.setVisibility(View.VISIBLE);
            }
        });
    }


}
