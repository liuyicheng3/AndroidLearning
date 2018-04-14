package com.lyc.study.go022;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by lyc on 18/4/14.
 */

public class RemoteService2 extends Service {

    private  ICallback iCallback = null;

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    /**
     * 可以点进去 查看源码 {@link IaidlData.Stub}  需要格式化一下
     *
     * mBinder这个是一个binder 对象 在new的时候向 Service Manager进程注册服务
     * this.attachInterface(this, DESCRIPTOR);
     *
     */

    private final IaidlData.Stub mBinder = new IaidlData.Stub()
    {
        /**
         * 最终是通过onTransact  TRANSACTION_calResult来实现的
         * {@link Stub#onTransact(int, android.os.Parcel, android.os.Parcel, int)}
         * @param input
         * @return
         */
        @Override
        public String calResult(int input)
        {
            switch (input){
                case 1:{
                    return "AIDL 11";
                }
                case 2:{
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                iCallback.onResult("AIDL 延迟返回"+"22");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    },1000);
                    return "AIDL 22";
                }
                default:{
                    return "AIDL default";
                }
            }

        }

        @Override
        public void init(ICallback callBack) throws RemoteException {
            iCallback = callBack;

        }

    };

    private Handler mHandler = new Handler();

}
