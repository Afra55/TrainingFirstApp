package com.afra55.trainingfirstapp.module.my_anim.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.utils.CommontUtils;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFiveFragment extends BaseFragment {

    private LinearLayout mContainer;
    private RelativeLayout subView;

    public static LessonFiveFragment newInstance(String params1, String params2){
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params1);
        args.putString(ARG_PARAM2, params2);

        LessonFiveFragment instance = new LessonFiveFragment();
        instance.setArguments(args);
        return instance;
    }


    public LessonFiveFragment() {
        setFragmentTitle("leson 5 布局变更动画");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_five, container, false);

        mContainer = (LinearLayout) view.findViewById(R.id.lesson_five_container);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        setRightFragmentTitleBtnText("添加子布局");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_actionBar_right_btn:
                addSubView();
                break;
            case R.id.lesson_five_sub_del_ibt:
                mContainer.removeView((View) v.getTag(R.integer.lesson_five_sub_view));
                mContainer.removeView((View) v.getTag(R.integer.lesson_five_sub_line));
                if (mContainer.getChildAt(0) instanceof TextView){
                    mContainer.removeViewAt(0);
                }
                break;
        }
    }

    private void addSubView() {
        subView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.lesson_five_sub_view, null);
        ImageButton deleIbt = (ImageButton) subView.findViewById(R.id.lesson_five_sub_del_ibt);
        deleIbt.setTag(R.integer.lesson_five_sub_view, subView);
        deleIbt.setOnClickListener(this);
        if (mContainer.getChildCount() != 0){
            TextView line = new TextView(getContext());
            line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommontUtils.dip2px(1)));
            line.setBackgroundColor(getResources().getColor(R.color.black));
            mContainer.addView(line);
            deleIbt.setTag(R.integer.lesson_five_sub_line, line);
        }
        Random random = new Random();
        TextView name = (TextView) subView.findViewById(R.id.lesson_five_sub_tv);
        if (random.nextBoolean()){
            name.setText("Afra55");
        }else {
            name.setText("wuguanjun");

        }
        mContainer.addView(subView);
    }
}
