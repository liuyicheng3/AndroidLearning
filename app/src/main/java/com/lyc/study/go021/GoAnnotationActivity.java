package com.lyc.study.go021;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lyc.common.Mlog;
import com.lyc.study.R;
import com.lyc.study.go020.InjectView;

import java.lang.reflect.Field;

/**
 * Created by lyc on 18/3/13.
 */

public class GoAnnotationActivity extends Activity {
    Activity mActivity;
    @InjectView(id = R.id.et_input)
    private EditText  et_input;
    @InjectView(id = R.id.btn_dialog)
    private Button btn_dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.act_annotation);
        analyseInjectView();
        et_input.setText("After init");
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dialog dialog = new Dialog(mActivity);
//                dialog.show();
                setByCustomStr(Constant.ReadyStatus.OK);
            }
        });
    }


    private void setByStr(@StringRes int  readyResId){
        et_input.setText(getString(readyResId));
    }

    private void setByCustomStr(@Constant.ReadyStatus.ReadyType String  readyState){


        et_input.setText(readyState);
    }

    /**根据注解自动解析控件*/
    private void analyseInjectView(){
        try {
            Class clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                InjectView injectView = field.getAnnotation(InjectView.class);
                if (injectView != null){
                    int id = injectView.id();
                    Log.e("lyc", "id->"+id);
                    if (id > 0){
                        field.setAccessible(true);
                        field.set(this, findViewById(id));
                    }
                }
            }
        }catch (Exception e){}
    }

    private void initViews(){
        et_input = (EditText)mActivity.findViewById(R.id.et_input);
        btn_dialog = (Button)mActivity.findViewById(R.id.btn_dialog);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Mlog.e("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Mlog.e("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Mlog.e("onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Mlog.e("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Mlog.e("onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Mlog.e("onDestroy");
    }
}
