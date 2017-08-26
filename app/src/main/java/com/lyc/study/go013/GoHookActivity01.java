package com.lyc.study.go013;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lyc.study.R;

import java.lang.reflect.Proxy;

/**
 * Created by lyc on 17/5/1.
 */

public class GoHookActivity01 extends Activity {

    TextView tv_proxy,tv_proxy2,tv_proxy3,tv_proxy4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hook_01);
        tv_proxy = (TextView) findViewById(R.id.tv_proxy);
        tv_proxy2 = (TextView) findViewById(R.id.tv_proxy2);
        tv_proxy3 = (TextView) findViewById(R.id.tv_proxy3);
        tv_proxy4 = (TextView) findViewById(R.id.tv_proxy4);

        findViewById(R.id.btn_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoHookActivity01.this, GoHookActivity02.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_proxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * IMMessage.class.getInterfaces() //不包括本接口的 定义的interface                *
                 * new Class<?>[]{ChatRoomMessage.class} // 可以是继承接口的接口
                 */

                ChatRoomStorageBean innerBean =new ChatRoomStorageBean();
                ChatRoomMessage proxyItem = (ChatRoomMessage) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{ChatRoomMessage.class}, new ChatRoomMessageInvocationHandler(innerBean));

                tv_proxy.setText(proxyItem.getContent());

            }
        });


        findViewById(R.id.btn_proxy2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHello proxyHello = (IHello) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class[] { IHello.class }, new HelloInvocationHandler(new InnerProxy()));
                tv_proxy2.setText(String.valueOf(proxyHello.hello2()));

            }
        });


        findViewById(R.id.btn_proxy3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> IActivityManagerIntercept = null;
                try {
                    IActivityManagerIntercept = Class.forName("com.lyc.study.go013.ChatRoomMessage");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                /**
                 * IMMessage.class.getInterfaces() //不包括本接口的 定义的interface
                 *
                 * new Class<?>[]{ChatRoomMessage.class} // 可以是继承接口的接口
                 */

                ChatRoomStorageBean innerBean =new ChatRoomStorageBean();

                ChatRoomMessage proxyItem = (ChatRoomMessage)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{IActivityManagerIntercept}, new ChatRoomMessageInvocationHandler(innerBean));

                tv_proxy3.setText(proxyItem.getContent());

            }
        });

        findViewById(R.id.btn_proxy4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * IMMessage.class.getInterfaces() //不包括本接口的 定义的interface
                 *
                 * new Class<?>[]{ChatRoomMessage.class} // 可以是继承接口的接口
                 */

                ChatRoomStorageBean innerBean =new ChatRoomStorageBean();


                IMMessage proxyItem = (IMMessage)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        ChatRoomMessage.class.getInterfaces(), new ChatRoomMessageInvocationHandler(innerBean));
                /**
                 * 所以 如果这样就会报错
                 * ChatRoomMessage proxyItem = (IMMessage)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                 * ChatRoomMessage.class.getInterfaces(), new ChatRoomMessageInvocationHandler(innerBean));
                 */

                tv_proxy4.setText(proxyItem.getSuperInfo());
            }
        });
    }
}
