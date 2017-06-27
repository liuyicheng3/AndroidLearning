package com.lyc.study.go015;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyc.study.R;

/**
 * Created by lyc on 17/6/27.
 */

public class GoViewThreadActivity  extends Activity{

    TextView tv_origin;
    Button btn_add,btn_del;
    LinearLayout ll_thread;


    View threadView;

    Activity act;
    Context ctx;

    Button btn_thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_ui);
        act = this;
        ctx = act.getApplicationContext();
        tv_origin= (TextView) findViewById(R.id.tv_origin);
        btn_add= (Button) findViewById(R.id.btn_add);
        btn_del= (Button) findViewById(R.id.btn_del);
        ll_thread= (LinearLayout) findViewById(R.id.ll_thread);
        new Thread(){
            @Override
            public void run() {
                super.run();
                //但是只要这个View 一在界面渲染成功后就不能继续添加了
                tv_origin.setText("我在子线程里面修改内容了");


            }
        }.start();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        threadView = LayoutInflater.from(act).inflate(R.layout.view_thread,null);
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_thread.addView(threadView);
                                btn_thread = (Button) threadView.findViewById(R.id.btn_thread);
                                btn_thread.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //这样不可以的
                                        /*new Thread(){
                                            @Override
                                            public void run() {
                                                super.run();
                                                btn_thread.setText("modify  sth");
                                            }
                                        }.start();*/

                                        btn_thread.setText("modify  sth");

                                    }
                                });
                            }
                        });
                    }
                }.start();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_thread.getChildCount()>0){
                    ll_thread.removeView(ll_thread.getChildAt(ll_thread.getChildCount()-1));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
