package com.afra55.trainingfirstapp.module.image_handle.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;

public class ShaderTestFragment extends BaseFragment {

    public static ShaderTestFragment newInstance(String arg1, String arg2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, arg1);
        args.putString(ARG_PARAM2, arg2);
        ShaderTestFragment fragment = new ShaderTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShaderTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shader_test, container, false);
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitleBar();
    }
}
