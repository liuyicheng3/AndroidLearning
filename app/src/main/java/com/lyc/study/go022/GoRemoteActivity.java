package com.lyc.study.go022;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyc.common.BaseActivity;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

/**
 * Created by lyc on 18/4/4.
 *
 * 讲AIDL binder 原理很好的文章https://blog.csdn.net/xiangzhihong8/article/details/79935473
 *https://www.jianshu.com/p/1eff5a13000d
 * 一定要格式化{@link IaidlData}这个类看源码
 */

public class GoRemoteActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_left_input,et_right_input;
    private Button btn_left,btn_right;
    private LinearLayout lv_left,lv_right;
    private TextView tv_left_out_put,tv_right_out_put;

    private Messenger remoteServiceMessager ;
    private Messenger remoteListenerMessager ;

    private IaidlData aidlInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_remote);
        initViews();
        bindService();
    }

    private void bindService(){

        bindService(new Intent(this,RemoteService1.class),coon,BIND_AUTO_CREATE);
        bindService(new Intent(this,RemoteService2.class),mSecondaryConnection,BIND_AUTO_CREATE);
    }

    private void unbindService(){

        unbindService(coon);
        unbindService(mSecondaryConnection);
    }

    private ServiceConnection coon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteServiceMessager = new Messenger(service);
            remoteListenerMessager= new Messenger(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteServiceMessager = null;
            remoteListenerMessager= null;

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (aidlInterface==null){
                return;
            }
            //远程服务意外关闭了
            aidlInterface.asBinder().unlinkToDeath(mDeathRecipient,0);
            aidlInterface  = null;
            // TODO: 18/4/14 这里需要重新绑定远程服务

        }
    };

    private ServiceConnection mSecondaryConnection = new ServiceConnection()
    {
        /**
         *一定要格式化{@link IaidlData}这个类看源码
         *
         * @param className
         * @param service  他就是RemoteService2里面new的binder 对象，这个binder里面通过
         *                 this.attachInterface(this, DESCRIPTOR)  注册了这个binder
         */
        public void onServiceConnected(ComponentName className, IBinder service)
        {
            /**
             *这个地方是通过
             * com.lyc.study.go022.IaidlData.Stub#asInterface(android.os.IBinder) 查询注册的binder对象的
             *
             * android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
             * 这里的obj 是binder对象
             *
             * 这里拿到的不是真的IaidlData而是一个代理 com.lyc.study.go022.IaidlData.Stub.Proxy(obj)
             *
             */
            aidlInterface = IaidlData.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                aidlInterface.init(new ICallback.Stub() {
                    @Override
                    public void onResult(final String result) throws RemoteException {
                        /**
                         * 这个地方需要post到主线程里面去
                         * 不然会抛出 Only the original thread that created a view hierarchy can touch its view
                         */
                        tv_right_out_put.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_right_out_put.setText( result);
                            }
                        });
                    }

                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className)
        {
            aidlInterface = null;
        }
    };

    private void initViews(){
        lv_left = (LinearLayout)mActivity.findViewById(R.id.lv_left);
        et_left_input = (EditText)mActivity.findViewById(R.id.et_left_input);
        btn_left = (Button)mActivity.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);
        tv_left_out_put = (TextView)mActivity.findViewById(R.id.tv_left_out_put);
        lv_right = (LinearLayout)mActivity.findViewById(R.id.lv_right);

        et_right_input = (EditText)mActivity.findViewById(R.id.et_right_input);
        btn_right = (Button)mActivity.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        tv_right_out_put = (TextView)mActivity.findViewById(R.id.tv_right_out_put);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:{
                String rawInput = et_left_input.getText().toString().trim();
                if (TextUtils.isEmpty(rawInput)){
                    UtilsManager.toast(mContext,"请输入数字1或2");
                    return;
                }
                int num =Integer.valueOf(rawInput);

                Message msg = Message.obtain(null,num);
                msg.replyTo = remoteListenerMessager;
                try {
                    remoteServiceMessager.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.btn_right:{
                String rawInput = et_right_input.getText().toString().trim();
                if (TextUtils.isEmpty(rawInput)){
                    UtilsManager.toast(mContext,"请输入数字1或2");
                    return;
                }
                int num =Integer.valueOf(rawInput);
                try {
                    tv_right_out_put.setText( aidlInterface.calResult(num));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 11:{
                    tv_left_out_put.setText(String.valueOf(msg.getData().getString("result")));
                    break;
                }
                case 22:{
                    /**
                     * 必须setclassloader  不然 android.os.BadParcelableException: ClassNotFoundException when unmarshalling
                     */
                    msg.getData().setClassLoader(WrapperResult.class.getClassLoader());
                    WrapperResult wrapperResult = msg.getData().getParcelable("wrapper");
                    tv_left_out_put.setText(wrapperResult.result);
                    break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }
}
