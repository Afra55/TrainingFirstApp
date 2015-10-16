package com.magus.trainingfirstapp.activity.myanim;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magus.trainingfirstapp.BaseFragment;
import com.magus.trainingfirstapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFourFragment extends BaseFragment {

    /**
     * 创建实例
     * @param param1
     * @param param2
     * @return
     */
    public static LessonFourFragment newInstance(String param1, String param2){
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        LessonFourFragment instance = new LessonFourFragment();
        instance.setArguments(args);
        return instance;
    }


    public LessonFourFragment() {
        setFragmentTitleSting("Lesson 4 缩放View");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_four, container, false);

        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {

    }
}
