package com.lyc.common;

import android.app.Application;

import com.lyc.study.go013.GoHookActivity01;
import com.lyc.study.go013.HookUtil;
import com.lyc.study.go013.ProxyActivity;

/**
 * Created by lyc on 17/3/28.
 */

public class ApplicationManger extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HookUtil hookUtil=new HookUtil(ProxyActivity.class, this);
        hookUtil.hookSystemHandler();
        hookUtil.hookAms();
    }
}
