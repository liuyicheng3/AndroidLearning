package com.lyc.study.go020;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.storage.StorageManager;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lyc.common.MLog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by lyc on 17/11/17.
 */

public class GoCalMd5Activity extends Activity implements View.OnClickListener {

    private Activity mActivity;
	private Button btn_cal_0,btn_cal_1,btn_check;
	private TextView tv_result_0,tv_result_1;
    private MarqueeView marqueeView1,marqueeView2;
    private MarqueeTextView marqueeView;

    private String apkPath= Environment.getExternalStorageDirectory().getPath()+"/BiBiChat_release.apk";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_md5);
        mActivity = this;
        initViews();
        MLog.e(apkPath);
        tv_result_0.setText(calMd5_0());
        tv_result_1.setText(calMd5_1());


        final TextView  tv_target = (TextView) findViewById(R.id.tv_target);
        tv_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_target.setText("Disconnected from the target VM, address: 'localhost:8609', transport: 'socket'");
                tv_target.post(new Runnable() {
                    @Override
                    public void run() {
                        startHoriAni(tv_target);
                    }
                });
                tv_target.setClickable(false);
                UtilsManager.toast(mActivity,"start");
            }
        });
    }

    private void  startHoriAni(TextView textView){
        float width = textView.getPaint().measureText(textView.getText().toString());
        float containerSize = findViewById(R.id.fl_container).getMeasuredWidth();
        if (containerSize<=0){
            MLog.e("还没测出结果");
            return;
        }
        MLog.e("textWidth:"+width+" maxWidth:"+containerSize);
        if (width>containerSize){
            ObjectAnimator xAni = ObjectAnimator.ofFloat(textView,"x",0,containerSize-width);
            xAni.setDuration(2000);
            xAni.start();
        }else {
            textView.setX(0);
        }
    }

    int index =0;
    private void initViews(){
        marqueeView = (MarqueeTextView) mActivity.findViewById(R.id.marqueeView);
        marqueeView.setCurrentItem(index+"、MarqueeView开源项目");
        Button btn_add_1 = (Button) findViewById(R.id.btn_add_1);

        btn_add_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                String newIndex = index+"、MarqueeView开源项目";
                marqueeView.setNextItem(newIndex);
            }
        });

        marqueeView1 = (MarqueeView) mActivity.findViewById(R.id.marqueeView1);
        SpannableString s = new SpannableString(index+"、MarqueeView开源项目");
        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        marqueeView1.startWithText(getString(R.string.marquee_texts), R.anim.anim_top_in, R.anim.anim_bottom_out);
        marqueeView1.startWithText(s.toString(), R.anim.anim_top_in, R.anim.anim_bottom_out);
        marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {


            }
        });




        List<CharSequence> list = new ArrayList<>();
        SpannableString ss1 = new SpannableString("1、MarqueeView开源项目");
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 2, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss1);
        SpannableString ss2 = new SpannableString("2、GitHub：sfsheng0322");
        ss2.setSpan(new ForegroundColorSpan(Color.GREEN), 9, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss2);
        SpannableString ss3 = new SpannableString("3、个人博客：sunfusheng.com");
        ss3.setSpan(new URLSpan("http://sunfusheng.com/"), 7, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss3);
        list.add("4、新浪微博：@孙福生微博");

        marqueeView2 = (MarqueeView) mActivity.findViewById(R.id.marqueeView2);
        marqueeView2.startWithList(list);
        marqueeView2.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                UtilsManager.toast(mActivity,textView.getText().toString());
            }
        });
        btn_cal_0 = (Button)mActivity.findViewById(R.id.btn_cal_0);
		btn_cal_0.setOnClickListener(this);
		tv_result_0 = (TextView)mActivity.findViewById(R.id.tv_result_0);
		btn_cal_1 = (Button)mActivity.findViewById(R.id.btn_cal_1);
		btn_cal_1.setOnClickListener(this);
		tv_result_1 = (TextView)mActivity.findViewById(R.id.tv_result_1);
		btn_check = (Button)mActivity.findViewById(R.id.btn_check);
		btn_check.setOnClickListener(this);
	}

    /**
     * 转换成字节
     * @param fis
     * @return
     */
    public static byte[] file2byte(FileInputStream fis) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    /** 计算给定 byte [] 串的 MD5 */
    private static byte[] MD5(byte[] input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(input);
            return md.digest();
        } else
            return null;
    }

    /**
     * Converts a byte array into a String hexidecimal characters
     *
     * null returns null
     */
    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null)
            return null;
        String table = "0123456789abcdef";
        StringBuilder ret = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            int b;
            b = 0x0f & (bytes[i] >> 4);
            ret.append(table.charAt(b));
            b = 0x0f & bytes[i];
            ret.append(table.charAt(b));
        }
        return ret.toString();
    }

    private String calMd5_0(){
        try {
            FileInputStream fis = new FileInputStream(apkPath);
            return bytesToHexString(MD5(file2byte(fis)));//之前这里用的是阿里里面的一个方法，这样需要测试一下，加了一个自己写的方法
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        UtilsManager.toast(mActivity,"Met error");
        return "";
    }

    private String calMd5_1(){
        try {
            return bytesToHexString(MD5(file2byte2(apkPath)));//之前这里用的是阿里里面的一个方法，这样需要测试一下，加了一个自己写的方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        UtilsManager.toast(mActivity,"Met error");
        return "";
    }

    public  byte[] file2byte2(String filePath){
        byte[] bytes = null;
        File file = new File(filePath);
        if (file.exists()){
            long length = file.length();
            if (length > Integer.MAX_VALUE){
                MLog.e("file2byte2 文件太大");
                return null;
            }
            try {
                InputStream is = new FileInputStream(file);
                bytes = new byte[(int) length];

                int offset = 0;
                int numRead = 0;

                while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset))>=0){
                    offset += numRead;
                }
                is.close();

                if (offset < bytes.length){
                    MLog.e("file2byte2 file is error");
                    return null;
                }

            } catch (Exception e) {
                MLog.e("file2byte2 异常->"+e.toString());
                e.printStackTrace();
            }

        }

        return bytes;

    }

	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_cal_0:{
				tv_result_0.setText(calMd5_0());
				break;
			}
			case R.id.btn_cal_1:{
                tv_result_1.setText(calMd5_1());
                break;
			}
			case R.id.btn_check:{
                if ((tv_result_0.getText().toString().trim()).equals((tv_result_1.getText().toString().trim()))){
                    UtilsManager.toast(mActivity,"Md5相等");
                }else{
                    UtilsManager.toast(mActivity,"Md5不等");
                }
				
				break;
			}
		}

	}


}
