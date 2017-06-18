package com.lyc.study.go009;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

/**
 * Created by lyc on 17/6/4.
 */

public class GoListViewTouchActivity  extends Activity {

    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv =new ListView(this);
        lv.setBackgroundColor(Color.WHITE);
        setContentView(lv);
        lv.setAdapter(new Adapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UtilsManager.toast(GoListViewTouchActivity.this,"Item");

            }
        });
    }


    private class Adapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 8;
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
            final Holder holder;
            if (convertView ==null){
                holder =new Holder();
                convertView = LayoutInflater.from(GoListViewTouchActivity.this).inflate(R.layout.adapter_list_item,null);
                holder.item = convertView;
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mlog.e("item click");
                    }
                });
                holder.parent = (ViewGroup) convertView.findViewById(R.id.parent);
                /*holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mlog.e("parent click");

                    }
                });
*/
                /*holder.parent.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Mlog.e("on TOuch");
                        return false;
                    }
                });*/
                convertView.findViewById(R.id.btn_inner).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilsManager.toast(GoListViewTouchActivity.this,"btn_inner");
                    }
                });
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }

            return convertView;
        }



    }
    private  class  Holder {
        View  item;
        ViewGroup  parent;
        View  son;
    }



}
