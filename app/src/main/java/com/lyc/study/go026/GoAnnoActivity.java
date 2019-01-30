package com.lyc.study.go026;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import com.lyc.study.R;

import wz.com.annotationlib.MyClass;

@MyClass.DIActivity
public class GoAnnoActivity extends Activity {


    @MyClass.BYView(R.id.textView1)
    public TextView textView1;

}

