package com.lyc.study.basic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.lyc.common.MLog;

/**
 * Created by lyc on 17/12/3.
 */

public class ServiceA extends Service {
    ABinder aBinder =new ABinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MLog.e("onBind"+this);
        return aBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        MLog.e("onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        MLog.e("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLog.e("onStartCommand");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MLog.e("auto stop Service after 4000");
                ServiceA.this.stopService(new Intent(ServiceA.this,ServiceA.class));

            }
        },4000);

        return super.onStartCommand(intent, flags, startId);

    }

    Handler handler = new Handler();

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        MLog.e("onStart"+this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        MLog.e("onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MLog.e("onDestroy :"+this);
    }

    class ABinder extends Binder{

    }
}
