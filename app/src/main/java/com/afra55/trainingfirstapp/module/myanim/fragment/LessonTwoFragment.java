package com.afra55.trainingfirstapp.module.myanim.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.module.myanim.transformer.DepthPageTransformer;
import com.afra55.trainingfirstapp.module.myanim.transformer.ZoomOutPageTransformer;

public class LessonTwoFragment extends BaseFragment {

    private ViewPager content;
    private FragmentStatePagerAdapter adapter;
    private BaseFragment[] fragments;

    public static LessonTwoFragment newInstance(String param1, String param2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        LessonTwoFragment fragment = new LessonTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LessonTwoFragment(){
        setFragmentTitle("Lesson 2 ViewPager");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_two, null);
        content = (ViewPager) view.findViewById(R.id.fragment_lesson_two_viewpager);
        return initLayoutView(inflater, container, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void initData() {

        setRightFragmentTitleBtnText("变换动画");

        setFragmentBarToMiddelTv("viewPager Anim");

        fragments = new BaseFragment[]{LessonTwoTestFragment.newInstance("1", "viewpager"), LessonTwoTestFragment.newInstance("2", "viewpager"), LessonTwoTestFragment.newInstance("3", "viewpager"), LessonTwoTestFragment.newInstance("4", "viewpager")};

        adapter = new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };

        content.setAdapter(adapter);

        content.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public boolean onBackPressed() {
        if (content.getCurrentItem() == 0) {
            return super.onBackPressed();
        }else{
            content.setCurrentItem(content.getCurrentItem() - 1);
            return false;
        }

    }

    private boolean changeAnimFlag = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_actionBar_right_btn:
                if (changeAnimFlag){
                    content.setPageTransformer(true, new DepthPageTransformer());
                }else {
                    content.setPageTransformer(true, new ZoomOutPageTransformer());
                }

                break;
        }
        super.onClick(v);
    }
}
