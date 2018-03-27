package com.lyc.common;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.lyc.study.go013.GoHookActivity01;
import com.lyc.study.go013.HookUtil;
import com.lyc.study.go013.ProxyActivity;

import java.net.Proxy;

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
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(150000) // set connection timeout.
                        .readTimeout(150000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                ))
                .commit();

    }
}
