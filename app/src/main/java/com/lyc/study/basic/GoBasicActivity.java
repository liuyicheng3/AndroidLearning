package com.lyc.study.basic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.lyc.common.Mlog;
import com.lyc.study.R;

/**
 * Created by lyc on 17/12/3.
 */

public class GoBasicActivity extends FragmentActivity implements View.OnClickListener {


	private Button  btn_startA,btn_stopA,btn_bindA,btn_unbindA;
	private Button btn_replace_1,btn_replace_2;
    private Activity mActivity;
	private void initViews(){
		btn_startA = (Button)mActivity.findViewById(R.id.btn_startA);
		btn_startA.setOnClickListener(this);
		btn_stopA = (Button)mActivity.findViewById(R.id.btn_stopA);
		btn_stopA.setOnClickListener(this);
		btn_bindA = (Button)mActivity.findViewById(R.id.btn_bindA);
		btn_bindA.setOnClickListener(this);
		btn_unbindA = (Button)mActivity.findViewById(R.id.btn_unbindA);
		btn_unbindA.setOnClickListener(this);
		btn_replace_1 = (Button)mActivity.findViewById(R.id.btn_replace_1);
		btn_replace_1.setOnClickListener(this);
		btn_replace_2 = (Button)mActivity.findViewById(R.id.btn_replace_2);
		btn_replace_2.setOnClickListener(this);

		fragA = new FragmentA();
		fragB = new FragmentB();
	}

	FragmentA fragA;
	FragmentB fragB;

	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_startA:{
				mActivity.startService(new Intent(mActivity,ServiceA.class));

				break;
			}
			case R.id.btn_stopA:{
				mActivity.stopService(new Intent(mActivity,ServiceA.class));

				break;
			}
			case R.id.btn_bindA:{
				mActivity.bindService(new Intent(mActivity,ServiceA.class), coon, Context.BIND_AUTO_CREATE);

				break;
			}
			case R.id.btn_unbindA:{
				mActivity.unbindService(coon);
				break;
			}

			case R.id.btn_replace_1:{

				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().add(R.id.fl_container,fragA).commitAllowingStateLoss();
				break;
			}
			case R.id.btn_replace_2:{
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().remove(fragA).commitAllowingStateLoss();
				break;
			}
		}

	}


	ServiceConnection coon =new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ServiceA.ABinder aBinder = (ServiceA.ABinder) service;
			Mlog.e(aBinder.toString());
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};




    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
        mActivity = this;
        initViews();
    }


}
