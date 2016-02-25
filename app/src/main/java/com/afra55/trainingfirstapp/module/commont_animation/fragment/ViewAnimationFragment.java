package com.afra55.trainingfirstapp.module.commont_animation.fragment;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.utils.anim.Rotate3dAnimation;

/**
 * 视图动画
 */
public class ViewAnimationFragment extends BaseFragment {

    public static ViewAnimationFragment newInstance(String arg1, String arg2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, arg1);
        args.putString(ARG_PARAM2, arg2);

        ViewAnimationFragment fragment = new ViewAnimationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ViewAnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_animation, container, false);
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
            case R.id.btnAlpha:
                btnAlpha(v);
                break;
            case R.id.btnRotate:
                btnRotate(v);
                break;
            case R.id.btnRotateSelf:
                btnRotateSelf(v);
                break;
            case R.id.btnTranslate:
                btnTranslate(v);
                break;
            case R.id.btnScale:
                btnScale(v);
                break;
            case R.id.btnScaleSelf:
                btnScaleSelf(v);
                break;
            case R.id.btnSet:
                btnSet(v);
                break;
            case R.id.cusstomAnim:
                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 360, 10, true);
                v.startAnimation(rotate3dAnimation);
                break;
            case R.id.backgroundAnim:
                ValueAnimator colorAnim = ObjectAnimator.ofInt(v, "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
                break;
            case R.id.togetherAnim:
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(v, "rotationX", 0, 360),
                        ObjectAnimator.ofFloat(v, "rotationY", 0, 180),
//                        ObjectAnimator.ofFloat(v, "rotation", 0, -90),
                        ObjectAnimator.ofFloat(v, "translationX", 0, 90),
//                        ObjectAnimator.ofFloat(v, "translationY", 0, 90),
                        ObjectAnimator.ofFloat(v, "scaleX", 1, 1.5f),
                        ObjectAnimator.ofFloat(v, "scaleY", 1, 0.5f),
                        ObjectAnimator.ofFloat(v, "alpha", 1, 0.25f, 1)
                );
                set.setDuration(5 * 1000).start();

                break;
        }
    }

    public void btnAlpha(View view) {
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        view.startAnimation(aa);
    }

    public void btnRotate(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360, 100, 100);
        ra.setDuration(1000);
        view.startAnimation(ra);
    }

    public void btnRotateSelf(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        ra.setDuration(1000);
        view.startAnimation(ra);
    }

    public void btnTranslate(View view) {
        TranslateAnimation ta = new TranslateAnimation(0, 200, 0, 300);
        ta.setDuration(1000);
        view.startAnimation(ta);
    }

    public void btnScale(View view) {
        ScaleAnimation sa = new ScaleAnimation(0, 2, 0, 2);
        sa.setDuration(1000);
        view.startAnimation(sa);
    }

    public void btnScaleSelf(View view) {
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);
        sa.setDuration(1000);
        view.startAnimation(sa);
    }

    public void btnSet(View view) {
        AnimationSet as = new AnimationSet(true);
        as.setDuration(1000);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        as.addAnimation(aa);

        TranslateAnimation ta = new TranslateAnimation(0, 100, 0, -200);
        ta.setDuration(1000);
        as.addAnimation(ta);

        view.startAnimation(as);
    }
}
