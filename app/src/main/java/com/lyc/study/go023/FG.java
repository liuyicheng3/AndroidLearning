package com.lyc.study.go023;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyc.common.MLog;
import com.lyc.study.R;

public class FG extends Fragment {

    @Override
    public void onAttach(Context context) {
        MLog.e("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MLog.e("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = LayoutInflater.from(this.getActivity()).inflate(R.layout.act_go_io,null);
        MLog.e("onCreateView");
        // 这个地方如果返回null的话就可以不走onViewCreated
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MLog.e("onViewCreated");

    }
}
