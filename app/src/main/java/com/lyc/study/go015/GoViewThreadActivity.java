package com.lyc.study.go015;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyc.study.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lyc on 17/6/27.
 */

public class GoViewThreadActivity  extends Activity{

    TextView tv_origin;
    Button btn_add,btn_del;
    LinearLayout ll_thread;


    View threadView;

    Activity act;
    Context ctx;

    Button btn_thread;

    EditText editText;
    private Button button;
    private ArrayList<BookEntity> mList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_ui);
        act = this;
        ctx = act.getApplicationContext();
        tv_origin= (TextView) findViewById(R.id.tv_origin);
        btn_add= (Button) findViewById(R.id.btn_add);
        btn_del= (Button) findViewById(R.id.btn_del);
        ll_thread= (LinearLayout) findViewById(R.id.ll_thread);
        new Thread(){
            @Override
            public void run() {
                super.run();
                //但是只要这个View 一在界面渲染成功后就不能继续添加了
                tv_origin.setText("我在子线程里面修改内容了");


            }
        }.start();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        threadView = LayoutInflater.from(act).inflate(R.layout.view_thread,null);
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_thread.addView(threadView);
                                btn_thread = (Button) threadView.findViewById(R.id.btn_thread);
                                btn_thread.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //这样不可以的
                                        /*new Thread(){
                                            @Override
                                            public void run() {
                                                super.run();
                                                btn_thread.setText("modify  sth");
                                            }
                                        }.start();*/

                                        btn_thread.setText("modify  sth");

                                    }
                                });
                            }
                        });
                    }
                }.start();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_thread.getChildCount()>0){
                    ll_thread.removeView(ll_thread.getChildAt(ll_thread.getChildCount()-1));
                }
            }
        });
        initView();
    }



    private  void initView(){
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextInt = new Random().nextInt(100);
                String str = "#测试测试" + nextInt + "# ";
                editText.setText(editText.getText());
                editText.append(str);
                editText.setSelection(editText.getText().toString().length());
                mList.add(new BookEntity(str, String.valueOf(nextInt)));
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) { //当为删除键并且是按下动作时执行
                    int selectionStart = editText.getSelectionStart();
                    int lastPos = 0;
                    for (int i = 0; i < mList.size(); i++) { //循环遍历整个输入框的所有字符
                        if ((lastPos = editText.getText().toString().indexOf(mList.get(i).getBookName(), lastPos)) != -1) {
                            if (selectionStart != 0 && selectionStart >= lastPos && selectionStart <= (lastPos + mList.get(i).getBookName().length())) {
                                String sss = editText.getText().toString();
                                editText.setText(sss.substring(0, lastPos) + sss.substring(lastPos + mList.get(i).getBookName().length())); //字符串替换，删掉符合条件的字符串
                                mList.remove(i); //删除对应实体
                                editText.setSelection(lastPos); //设置光标位置
                                return true;
                            }
                        } else {
                            lastPos += ("#" + mList.get(i).getBookName() + "#").length();
                        }
                    }
                }
                return false;
            }
        });
    }

    public class BookEntity implements Serializable {

        private static final long serialVersionUID = 1L;
        private String bookId;
        private String bookName;
        public BookEntity(String bookName, String bookId) {
            super();
            this.bookName = bookName;
            this.bookId = bookId;
        }
        public String getBookId() {
            return bookId;
        }
        public void setBookId(String bookId) {
            this.bookId = bookId;
        }
        public String getBookName() {
            return bookName;
        }
        public void setBookName(String bookName) {
            this.bookName = bookName;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
