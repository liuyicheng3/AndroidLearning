package com.lyc.study.go001;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyc.study.R;

import java.util.ArrayList;

/**
 * Created by lyc on 2015/12/31.
 */
public class GoScrollWebViewActivity extends Activity {
    private VerticalPagerView verticalPager;

    private InnerButtomWebView innerWebView;
    private InnerTopScrollView innerScrollView;
    private InnerButtomListView innerListView;
    private TextView tv_may_like;
    private RelativeLayout rl_recommend_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_three_vertical_pager);

        verticalPager = (VerticalPagerView) findViewById(R.id.verticalPager);

        innerWebView = (InnerButtomWebView) findViewById(R.id.innerWebView);
        innerWebView.parentScrollView = verticalPager;
        innerWebView.loadUrl("http://wap.ifeng.com");

        innerScrollView = (InnerTopScrollView) findViewById(R.id.innnerScrollView);
        innerScrollView.parentScrollView = verticalPager;

        rl_recommend_tip = (RelativeLayout) findViewById(R.id.rl_recommend_tip);
        innerListView = (InnerButtomListView) findViewById(R.id.innerListView);
        tv_may_like = (TextView) findViewById(R.id.tv_may_like);

        innerWebView.setOnCustomScroolChangeListener(new InnerButtomWebView.ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                //WebView的总高度
                float webViewContentHeight = innerWebView.getContentHeight() * innerWebView.getScale();
                //WebView的现高度
                float webViewCurrentHeight = (innerWebView.getHeight() + innerWebView.getScrollY());
                if ((webViewContentHeight - webViewCurrentHeight) <= 3) {
                    rl_recommend_tip.setVisibility(View.VISIBLE);
                    System.out.println("WebView滑动到了底端");
                } else {
                    rl_recommend_tip.setVisibility(View.GONE);

                }
            }
        });

        innerListView = (InnerButtomListView) findViewById(R.id.innerListView);
        innerListView.parentScrollView = verticalPager;
        innerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));


        verticalPager.setScroolAble(true);
        verticalPager.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int page = verticalPager.getCurrentItem();
                    if (page == 2) {
                        tv_may_like.setText("continue_pull_see_detail");
                    }
                    if (page == 1) {
                        tv_may_like.setText("continue_pull_you_may_like");
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private ArrayList<String> getData() {
        ArrayList<String> mArrayList = new ArrayList<String>();
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        return mArrayList;
    }
}
