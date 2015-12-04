package com.magus.trainingfirstapp.module.commont_animation.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseFragment;
import com.magus.trainingfirstapp.module.commont_animation.anim.CusstomAnim;

/**
 * PropertyAnimatorFragment
 */
public class PropertyAnimatorFragment extends BaseFragment {

    public static PropertyAnimatorFragment newInstance(String param1, String param2) {
        PropertyAnimatorFragment fragment = new PropertyAnimatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_object_animator, container, false);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        hideFragmentTitleBar();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_anim_hellow:
                animMethod(v);
                break;
            case R.id.fragment_anim_cusstom:
                CusstomAnim anim = new CusstomAnim();
                anim.setRotateY(30);
                v.startAnimation(anim);
                break;
        }
    }

    private void animMethod(final View v) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                v,
                "translationX",
                300
        );
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(v, "translationY", 300f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(v, "rotation", 0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(10000);
        animatorSet.playTogether(animator1, animator2, animator3);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                if (v instanceof Button) {
                    ((Button) v).setText("Start");
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (v instanceof Button) {
                    ((Button) v).setText("End");
                }
            }
        });
    }
}
