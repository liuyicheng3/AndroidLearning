package com.lyc.study.go022;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by lyc on 18/4/14.
 */

public class RemoteService1 extends Service {

    /**
     * 通知activity的Messager
     */
    private Messenger replyMessager = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    replyMessager = msg.replyTo;
                    Message tobeSend = Message.obtain(null, 11);
                    Bundle bundle = new Bundle();
                    bundle.putString("result","输入了数字"+msg.what);
                    tobeSend.setData(bundle);
                    try {
                        /**
                         * 跨进程时候  不能直接使用 Message tobeSend = Message.obtain(null, 11,"输入了数字"+msg.what);
                         * 不然会报错java.lang.RuntimeException: Can’t marshal non-Parcelable objects across processes.
                         * 一般是使用bundle
                         */
                        replyMessager.send(tobeSend);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    replyMessager = msg.replyTo;
                    try {
                        Message tobeSend = Message.obtain(null, 22);
                        Bundle bundle = new Bundle();
                        WrapperResult wrapperResult = new WrapperResult("输入了数字"+msg.what);
                        bundle.putParcelable("wrapper",wrapperResult);
                        tobeSend.setData(bundle);

                        replyMessager.send(tobeSend);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Message tobeSend = Message.obtain(null, 22);
                                Bundle bundle = new Bundle();
                                WrapperResult wrapperResult = new WrapperResult("延迟返回"+msg.what);
                                bundle.putParcelable("wrapper",wrapperResult);
                                tobeSend.setData(bundle);
                                replyMessager.send(tobeSend);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 1000);

                    break;
                }

            }
        }
    };
    private Messenger messenger = new Messenger(mHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }



}
