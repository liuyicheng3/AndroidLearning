package com.lyc.study.go004;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.lyc.common.Mlog;
import com.lyc.study.R;

/**
 * Created by lyc on 2016/3/3.
 */
public class GoWecalAddTskActivity extends Activity {

    private com.lyc.study.go004.VerticalPagerView verticalPager;

    private InnerScrollView innerScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_add);
        verticalPager = (com.lyc.study.go004.VerticalPagerView) findViewById(R.id.verticalPager);

        innerScrollView = (InnerScrollView) findViewById(R.id.innnerScrollView);
        innerScrollView.parentScrollView = verticalPager;

        verticalPager.setOnScrollListener(new VerticalPagerView.OnScrollListener() {
            @Override
            public void onScrollPercent(float percent) {
                Mlog.e("onScrollPercent"+percent);
                if (percent<0.05){
                    GoWecalAddTskActivity.this.finish();
                }
            }});

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verticalPager.showUP();
            }
        },50);
    }
}
