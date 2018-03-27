package com.lyc.study.go010;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lyc.study.R;

import java.util.ArrayList;

/**
 * Created by lyc on 17/3/25.
 */

public class GoDBActivity  extends Activity {

    private Activity act;
    private Context ctx;

    private EditText et_msg_id,et_msg_content;
    private Button btn_save;

    private ListView  lv_data;
    private DBAdapter adapter;

    private ArrayList<Item> listData =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_godb);
        act  =this;
        ctx = getApplicationContext();

        initView();
        readAllData();
    }

    private void initView() {

        lv_data = (ListView) findViewById(R.id.lv_data);
        adapter =new DBAdapter();
        lv_data.setAdapter(adapter);

        et_msg_id = (EditText) findViewById(R.id.et_msg_id);
        et_msg_content = (EditText) findViewById(R.id.et_msg_content);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MessageTable.save(GODB.getDbInstance(ctx).getWritableDatabase(),et_msg_id.getText().toString(),et_msg_content.getText().toString());
                readAllData();
                et_msg_id.setText("");
                et_msg_content.setText("");
            }
        });
    }
    ArrayList<Item> tempList=new ArrayList<>();

    private void readAllData(){
        new Thread(){
            @Override
            public void run() {
                tempList.clear();
                Cursor c= MessageTable.read(GODB.getDbInstance(ctx).getWritableDatabase());
                if (c!=null){
                    while (c.moveToNext()){
                        Item item=new Item();
                        item.dataId=c.getInt(c.getColumnIndex(MessageTable.Columns.column_id));
                        item.msg_id=c.getString(c.getColumnIndex(MessageTable.Columns.column_msg_id));
                        item.content=c.getString(c.getColumnIndex(MessageTable.Columns.column_msg_content));
                        tempList.add(item);
                    }
                    c.close();
                }
                handler.sendEmptyMessage(0);
            }
        }.start();



    }

    public  class  DBAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Holder  holder;
            if (convertView ==null){
                convertView = LayoutInflater.from(act).inflate(R.layout.adpater_view_db,null);
                holder=new Holder();
                holder.et_msg_id= (EditText) convertView.findViewById(R.id.et_msg_id);

                holder.et_msg_content= (EditText) convertView.findViewById(R.id.et_msg_content);

                holder.btn_modify = (Button) convertView.findViewById(R.id.btn_modify);

                holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);

                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }
            final Item  bean = listData.get(position);
            holder.et_msg_id.setText(bean.msg_id);
            holder.et_msg_content.setText(bean.content);
            holder.btn_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bean.msg_id =holder.et_msg_id.getText().toString();
                    bean.content =holder.et_msg_content.getText().toString();

                    MessageTable.update(GODB.getDbInstance(ctx).getWritableDatabase(),bean.dataId,bean.msg_id,bean.content);
                    readAllData();
                }
            });

            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageTable.delete(GODB.getDbInstance(ctx).getWritableDatabase(),bean.dataId);
                    readAllData();
                }
            });
            return convertView;
        }


    }

    public class  Holder {
        private EditText et_msg_id,et_msg_content;
        private Button btn_modify,btn_delete;

    }


    private class Item{
        public int dataId;
        public String msg_id;
        public String content;
    }


    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:{
                    listData.clear();
                    listData.addAll(tempList);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };
}
