package com.lyc.study.go009;

import android.app.Activity;
import android.os.Bundle;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

/**
 * Created by lyc on 17/1/29.
 */

public class GoViewTraversalActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_go_view_tranversal);
        StringBuilder  sb =new StringBuilder();
        sb.append("200dp："+ UtilsManager.dip2px(this,200)+" ~ ");
        sb.append("100dp："+ UtilsManager.dip2px(this,100)+" ~ ");
        sb.append("50dp："+ UtilsManager.dip2px(this,50)+" ~ ");
        sb.append("20dp："+ UtilsManager.dip2px(this,20)+" ~ ");
        Mlog.e(sb.toString());

    }
}
