package com.lyc.study;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity {

    private Activity act;
    private Context ctx;

    private ListView mListView;
    private ActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = this;
        ctx = getApplicationContext();

        mListView = new ListView(this);
        mAdapter = new ActivityAdapter(queryManifestActivities());

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestInfo info = (TestInfo) mAdapter.getItem(position);
                startActivity(info.intent);
            }
        });

        UtilsManager.toast(this, mAdapter.getCount() + " Activities");
        setContentView(mListView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    protected List<TestInfo> queryManifestActivities() {

        PackageManager pm = getPackageManager();
        ArrayList<TestInfo> samples = new ArrayList<TestInfo>();
        /**
         * 这个地方不要一次性 find出 所有 package  已经 它各自的 activitys  这样 会超出binder 的数据传输限制
         *
         * 应该  限制住 getPackageManager().getPackageInfo(packageInfo.packageName, PackageManager.GET_ACTIVITIES)
         */
        List<PackageInfo> pInfos = pm.getInstalledPackages(0);


        try {
            for (PackageInfo packageInfo : pInfos) {
                Mlog.e("PackageInfo:" + packageInfo.packageName);
                if (packageInfo.packageName.equals(getPackageName())) {
                    ActivityInfo[] activities = getPackageManager().getPackageInfo(packageInfo.packageName, PackageManager.GET_ACTIVITIES).activities;
                    if (activities != null) {
                        for (ActivityInfo info : activities) {

                            String name = info.name;
                            if (name.contains("MainActivity")) {
                                continue;
                            }
                            name = name.substring(name.lastIndexOf("."));
                            if (!name.startsWith(".Go")){
                                continue;
                            }
                            Intent target = new Intent();
                            target.setClassName(info.applicationInfo.packageName, info.name);
                            TestInfo sample = new TestInfo(name, target);
                            samples.add(sample);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Mlog.e(e.toString());
        }
        return samples;
    }

    class TestInfo {
        String name;
        Intent intent;

        TestInfo(String name, Intent intent) {
            this.name = name;
            this.intent = intent;
        }
    }

    class ActivityAdapter extends BaseAdapter {
        private List<TestInfo> mItems;
        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public ActivityAdapter(List<TestInfo> items) {
            mItems = items;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(act);
                convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
            }
            TextView tv = (TextView) convertView;
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setText((position + 1) + ":" + mItems.get(position).name);
            return convertView;
        }

        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition() {
            return mSelection.keySet();
        }

        public void removeSelection(int position) {
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection() {
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

    }
}
