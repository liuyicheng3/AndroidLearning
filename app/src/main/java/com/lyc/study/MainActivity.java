package com.lyc.study;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lyc.common.Mlog;
import com.lyc.common.UtilsManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity {

    private Activity act;
    private Context ctx;

    private ListView mListView;
    private ActivityAdapter mAdapter;

    /** 设置状态栏可变颜色 */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setTranslucentStatus() {
        /** 5.0及以上设置状态栏透明*/
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);/**6.0以上新加的属性，用户浅色主题时将状态栏设为深色字体*/
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            /**由于哔哔背景为浅色，并且6.0以上才支持将状态栏颜色修改为黑色所以6.0以下的给状态栏设置一个半透明黑色背景*/
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                window.setStatusBarColor(Color.TRANSPARENT);
                if(Build.MANUFACTURER.toLowerCase().contains("xiaomi")) {
                    setMiuiStatusBarDarkMode(true);
                }
            }else{
//                window.setStatusBarColor(getDefaultStatusBarColor());
            }
        }
    }

    /**米UI上设置状态栏字体颜色为深色的方法
     * 由于MIUI 6修改过所以系统方法无法设置状态栏字体颜色无效，需要使用该方法设置*/
    public void setMiuiStatusBarDarkMode(boolean isdarkmode) {
        Class<? extends Window> clazz = this.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(this.getWindow(), isdarkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus();
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
//                Mlog.e("PackageInfo:" + packageInfo.packageName);
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
