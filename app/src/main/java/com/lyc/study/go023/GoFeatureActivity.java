package com.lyc.study.go023;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.lyc.common.BaseActivity;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

public class GoFeatureActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ViewGroup fl_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_go_feature);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new Adapter());
        android.app.Fragment mFragment = mActivity.getFragmentManager().findFragmentByTag("fm");
        if (mFragment == null) {
            mFragment = new FG();
            mActivity.getFragmentManager()
                    .beginTransaction()
//                    .add()
//                    .add(mFragment, "fm")
                    .commitAllowingStateLoss();
            mActivity.getFragmentManager().executePendingTransactions();
        }



    }

    class Adapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Button btn = new Button(mContext);
            btn.setBackgroundColor(Color.RED);
            btn.setTextColor(Color.BLACK);
            btn.setTextSize(UtilsManager.dip2px(mContext,20));
            return new VH(btn);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            vh.setText(position);
        }

        @Override
        public int getItemCount() {
            return 100;
        }


    }
    class VH extends RecyclerView.ViewHolder{

        Button btn;
        public VH(View itemView) {
            super(itemView);
            btn = (Button) itemView;
        }
        public void setText(int pos){
            btn.setText(pos+" 个元素");
        }
    }


}
