package com.lyc.study.go003;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lyc.study.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GoPhoneActivity extends Activity {
    private Activity act;
    private ListView lv;

    ArrayList<ContactBean> contacts=new ArrayList<ContactBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_phone);
        act= this;

        lv= (ListView) findViewById(R.id.lv_recent);

        new ReadAndSortTask().execute();
    }

    private void setDate2View(){
        ListAdapter lvAdapter= new BaseAdapter() {
            @Override
            public int getCount() {
                return contacts.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView tv;
                if (view!=null){
                    tv= (TextView) view;
                }else {
                    tv= new TextView(act);
                }
                tv.setText(contacts.get(i).desplayName+" "+contacts.get(i).phoneNumbers);
                return tv;
            }
        };
        lv.setAdapter(lvAdapter);
    }

    class ReadAndSortTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            HashMap<String ,Double> weight= new HashMap<String, Double>();
            ContactUtil.getCallsInPhone(act,weight);
            ContactUtil.getSmsInPhone(act,weight);
            ArrayList<ContactBean> result=ContactUtil.getContact(act,weight);
            Collections.sort(result, new Comparator());
            contacts.clear();
            contacts.addAll(result);
            for (ContactBean i:contacts){
                Log.e("go003",i.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            setDate2View();
        }
    }

}
