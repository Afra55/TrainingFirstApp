package com.magus.trainingfirstapp.module.myanim.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.magus.trainingfirstapp.base.BaseFragment;
import com.magus.trainingfirstapp.R;


public class LessonOnwFragment extends BaseFragment {

    //lesson one 渐变动画
    private ProgressBar animProgressBar;
    private TextView lessonOneTextView;
    private int lessonOneShortAnimDuration;

    /**
     * 使用这个工厂方法创建一个新的实例
     * 这个片段使用所提供的参数
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    public static LessonOnwFragment newInstance(String param1, String param2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        LessonOnwFragment fragment = new LessonOnwFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public  LessonOnwFragment(){
        setFragmentTitle("Lesson 1 渐变动画");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_lesson_onw, null);
        animProgressBar = (ProgressBar) view.findViewById(R.id.anim_progressBar);
        lessonOneTextView = (TextView) view.findViewById(R.id.anim_lesson_one_tv);
        lessonOneTextView.setVisibility(View.GONE);
        lessonOneShortAnimDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        return initLayoutView(inflater, container, view);
    }

    private void showProgressBar(){
        animProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        animProgressBar.setVisibility(View.GONE);
    }

    private void changeLessongOneTextViewState(){
        if (!lessonOneTextView.isShown()) {

            hideProgressBar();

            //设置textView透明
            lessonOneTextView.setAlpha(0);

            //设置可见
            lessonOneTextView.setVisibility(View.VISIBLE);

            //设置渐变动画从透明到不透明
            lessonOneTextView.animate().alpha(1f).setDuration(lessonOneShortAnimDuration).setListener(null);
        }else{
            lessonOneTextView.animate().alpha(0f).setDuration(lessonOneShortAnimDuration).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    lessonOneTextView.setVisibility(View.GONE);
                    showProgressBar();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_actionBar_right_btn:
                showToast("fragment_actionBar_right_btn");
                changeLessongOneTextViewState();
                break;
        }
        super.onClick(v);
    }
}
