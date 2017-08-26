package com.lyc.study.go018;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.lyc.common.Mlog;
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
                Mlog.e(path);
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
    }
}
