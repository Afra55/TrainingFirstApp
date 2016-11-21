package com.afra55.trainingfirstapp.module.my_anim.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;

public class LessonThreeFragment extends BaseFragment {

    private FrameLayout container;
    private boolean mShowingBack = false;


    public LessonThreeFragment() {
        setFragmentTitle("lesson 3 卡片翻转");
    }

    public static LessonThreeFragment newInstance(String arg1, String arg2){
        LessonThreeFragment instance = new LessonThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, arg1);
        args.putString(ARG_PARAM2, arg2);
        instance.setArguments(args);
        return instance;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_three, container, false);
        container = (ViewGroup) view.findViewById(R.id.lesson_three_card_container_flt);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        setRightFragmentTitleBtnText("翻转");
        if (mListener != null) mListener.onFragmentInteraction("");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_actionBar_right_btn){
            if (mListener != null) mListener.onFragmentChildViewOnClick(v);
        }
    }



}
