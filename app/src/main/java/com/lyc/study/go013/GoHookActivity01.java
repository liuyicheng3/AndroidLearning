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

    TextView tv_proxy,tv_proxy2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hook_01);
        tv_proxy= (TextView) findViewById(R.id.tv_proxy);
        tv_proxy2= (TextView) findViewById(R.id.tv_proxy2);
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
/*                Class<?> IActivityManagerIntercept = null;
                try {
                    IActivityManagerIntercept = Class.forName("com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
                /**
                 * IMMessage.class.getInterfaces() //必须是初始的接口  连继承接口的接口都不能
                 *
                 * new Class<?>[]{ChatRoomMessage.class} // 可以是继承接口的接口
                 */



                ChatRoomStorageBean innerBean =new ChatRoomStorageBean();
                ChatRoomMessage proxyItem = (ChatRoomMessage) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{ChatRoomMessage.class}, new ChatRoomMessageInvocationHandler(innerBean));





/*
                ChatRoomStorageBean innerBean =new ChatRoomStorageBean();
                AbstractChatRoomMessage proxyItem = (AbstractChatRoomMessage) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{AbstractChatRoomMessage.class}, new ChatRoomMessageInvocationHandler(innerBean));

*/

                tv_proxy.setText(proxyItem.getContent());
                /*
                ChatRoomMessage proxyItem = (ChatRoomMessage)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{IActivityManagerIntercept}, new ChatRoomMessageInvocationHandler(innerBean));*/

            }
        });


        findViewById(R.id.btn_proxy2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHello proxyHello = (IHello) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class[] { IHello.class }, new HelloInvocationHandler(new InnerProxy()));
                tv_proxy2.setText(proxyHello.hello2());
//                tv_proxy2.setText(proxyItem.getContent());

            }
        });
    }
}
