package com.lyc.study.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyc.study.R;

/**
 * Created by lyc on 18/1/24.
 */

public class FragmentB extends Fragment{

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView ==null){
            rootView = inflater.inflate(R.layout.frag_b,null);
        }else {
            if (rootView.getParent()!=null){
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;

    }
}
