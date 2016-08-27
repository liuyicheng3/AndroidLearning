package com.lyc.study.go006;

import android.app.Activity;
import android.os.Bundle;

import com.lyc.study.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;

public class GoTextExpandActivity extends Activity implements OnClickListener{

    private static final int MAX = 1;//初始maxLine大小
    private static final int TIME = 20;//间隔时间
    private int maxLines;
    private TextView textView;
    private boolean hasMesure = false;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_text_expand);
        initView();
    }

    private void initView(){

        //获取ViewTreeObserver View观察者，并注册一个监听事件，这个时间是在View还未绘制的时候执行的，也就是在onDraw之前
        //textView默认是没有maxLine限制的，这样我就可以计算到完全显示的maxLine
        textView = (TextView) findViewById(R.id.tv_src);
        ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                //只需要获取一次就可以了
                if(!hasMesure){
                    //这里获取到完全展示的maxLine
                    maxLines = textView.getLineCount();
                    //设置maxLine的默认值，这样用户看到View就是限制了maxLine的TextView
                    textView.setMaxLines(MAX);
                    hasMesure = true;
                }

                return true;
            }
        });

        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        toggle();
    }

    /**
     * 打开TextView
     */
    @SuppressLint("HandlerLeak")
    private void toggle(){

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int lines = msg.what;
                //这里接受到消息，让后更新TextView设置他的maxLine就行了
                textView.setMaxLines(lines);
                textView.postInvalidate();
            }
        };
        if(thread != null)
            handler.removeCallbacks(thread);

        thread = new Thread(){
            @Override
            public void run() {
                int count = MAX;
                while(count++ <= maxLines){
                    //每隔20mms发送消息
                    Message message = new Message();
                    message.what = count;
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }
        };
        thread.start();
    }

}