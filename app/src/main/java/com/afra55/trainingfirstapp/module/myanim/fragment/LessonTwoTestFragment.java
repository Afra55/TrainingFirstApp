package com.afra55.trainingfirstapp.module.myanim.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.R;

public class LessonTwoTestFragment extends BaseFragment {


    private FrameLayout content;

    public static LessonTwoTestFragment newInstance(String param1, String param2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        LessonTwoTestFragment fragment = new LessonTwoTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LessonTwoTestFragment() {
        setFragmentTitle("lesson two test");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_two_test, null);
        content = (FrameLayout) view.findViewById(R.id.lesson_two_test_content_flt);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        hideFragmentTitleBar();
        switch (Integer.parseInt(mParam1)){
            case 1:
                content.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case 2:
                content.setBackgroundColor(getResources().getColor(R.color.dark_red));
                break;
            case 3:
                content.setBackgroundColor(getResources().getColor(R.color.purple));
                break;
            case 4:
                content.setBackgroundColor(getResources().getColor(R.color.gray));
                break;
        }
    }
}
