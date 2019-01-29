package com.lyc.study.go018;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.lyc.common.MLog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

import java.io.File;

/**
 * Created by lyc on 17/8/22.
 */

public class GoFileDownLoadActivity  extends Activity{

    Button btn_start;
    Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        ctx= getApplicationContext();
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "tmpdir1" + File.separator ;
                MLog.e(path);
//                FileDownloader.getImpl().create("http://test.ssyun.cn/ljg/project/zhwnl/android/BiBiChat_release.apk")
//                        .setPath(Environment.getExternalStorageDirectory().getAbsolutePath()
                BaseDownloadTask task=FileDownloader.getImpl().create("http://test.ssyun.cn/ljg/project/zhwnl/android/BiBiChat_release.apk")
                                        .setPath(path, true)
                                        .setCallbackProgressTimes(300)
                                        .setMinIntervalUpdateSpeed(400)
                                        .setTag("afsadfsa")
                                        .setListener(new FileDownloadSampleListener() {

                                            @Override
                                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                super.pending(task, soFarBytes, totalBytes);
                                            }

                                            @Override
                                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                super.progress(task, soFarBytes, totalBytes);
                                            }

                                            @Override
                                            protected void error(BaseDownloadTask task, Throwable e) {
                                                super.error(task, e);
                                            }

                                            @Override
                                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                                super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                                            }

                                            @Override
                                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                super.paused(task, soFarBytes, totalBytes);
                                            }

                                            @Override
                                            protected void completed(BaseDownloadTask task) {
                                                super.completed(task);
                                            }

                                            @Override
                                            protected void warn(BaseDownloadTask task) {
                                                super.warn(task);
                                            }
                                        });
                task.start();
            }
        });

        WebView  wv= (WebView) findViewById(R.id.wv);
        wv.setWebChromeClient(new WebChromeClient(){

        });

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MLog.e("shouldOverrideUrlLoading:"+url);
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                MLog.e("onPageStarted:"+url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                MLog.e("onPageFinished:"+url);
                super.onPageFinished(view, url);
            }
        });
        WebSettings settings = wv.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        wv.loadUrl("https://developer.android.com/guide/webapps/migrating.html#URLs");
//        wv.loadUrl("http://192.168.10.188:8090/#/index");
        wv.loadUrl(page1);
    }

    String page1="http://www.niubichat.com/university/index.html#/";
    String page2="http://www.niubichat.com/university/index.html#/rule";

}
