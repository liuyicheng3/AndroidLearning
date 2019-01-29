package com.lyc.study.go021;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lyc.common.MLog;
import com.lyc.study.R;
import com.lyc.study.go020.InjectView;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by lyc on 18/3/13.
 */

public class GoAnnotationActivity extends Activity {
    Activity mActivity;
    @InjectView(id = R.id.et_input)
    private EditText  et_input;
    @InjectView(id = R.id.btn_dialog)
    private Button btn_dialog;
    @InjectView(id = R.id.stv)
    private StaticLayoutView  stv;
    private ExecutorService executorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.act_annotation);
        analyseInjectView();
        et_input.setText("After init");
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dialog dialog = new Dialog(mActivity);
//                dialog.show();
//                setByCustomStr(Constant.ReadyStatus.OK);
                MLog.e("添加10个任务");
                for (int i = 0; i < 10; i++) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MLog.e(Thread.currentThread().getName());
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });
        RejectedExecutionHandler rejectHandler  =new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                MLog.e("rejectIng");

            }
        };

        executorService= new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2),rejectHandler);
        TextPaint tp = new TextPaint();
        tp.setColor(Color.BLUE);
        tp.setStyle(Paint.Style.FILL);
        stv.setLayout(new StaticLayout("123123",tp,30, Layout.Alignment.ALIGN_CENTER,0,0,false));
    }


    private void setByStr(@StringRes int  readyResId){
        et_input.setText(getString(readyResId));
    }

    private void setByCustomStr(@Constant.ReadyStatus.ReadyType String  readyState){


        et_input.setText(readyState);
    }

    /**根据注解自动解析控件*/
    private void analyseInjectView(){
        try {
            Class clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                InjectView injectView = field.getAnnotation(InjectView.class);
                if (injectView != null){
                    int id = injectView.id();
                    Log.e("lyc", "id->"+id);
                    if (id > 0){
                        field.setAccessible(true);
                        field.set(this, findViewById(id));
                    }
                }
            }
        }catch (Exception e){}
    }

    private void initViews(){
        et_input = (EditText)mActivity.findViewById(R.id.et_input);
        btn_dialog = (Button)mActivity.findViewById(R.id.btn_dialog);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MLog.e("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MLog.e("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLog.e("onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        MLog.e("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        MLog.e("onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.e("onDestroy");
    }
}
