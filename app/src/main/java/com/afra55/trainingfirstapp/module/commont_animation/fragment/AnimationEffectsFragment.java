package com.afra55.trainingfirstapp.module.commont_animation.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 动画特效
 */
public class AnimationEffectsFragment extends BaseFragment {

    private final int[] images = {R.id.imageView_a, R.id.imageView_b, R.id.imageView_c,
            R.id.imageView_d, R.id.imageView_e};
    private List<ImageView> mImageViews = new ArrayList<>();
    private boolean mFlag = true;
    private TextView timerTv;
    private LinearLayout hideView;

    public static AnimationEffectsFragment newInstance(String arg1, String arg2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, arg1);
        args.putString(ARG_PARAM2, arg2);
        AnimationEffectsFragment fragment = new AnimationEffectsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AnimationEffectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation_effects, container, false);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = (ImageView) view.findViewById(images[i]);
            imageView.setOnClickListener(this);
            mImageViews.add(imageView);
        }
        timerTv = (TextView) view.findViewById(R.id.tv_timer);
        timerTv.setOnClickListener(this);
        hideView = (LinearLayout) view.findViewById(R.id.hidden_view);
        hideView.setOnClickListener(this);
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
            case R.id.imageView_a:
            case R.id.imageView_b:
            case R.id.imageView_c:
            case R.id.imageView_d:
            case R.id.imageView_e:
                if (mFlag) {
                    startMenuAnim();
                    showDropView(hideView);
                } else {
                    closeMenuAnim();
                    hideDropView(hideView);
                }
                break;
            case R.id.tv_timer:
                timerAnim();
                break;
            case R.id.hidden_view:
                closeMenuAnim();
                hideDropView(hideView);
                break;
        }
    }

    private void showDropView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = dropAnim(view, 0, DisplayUtil.dip2px(getContext(), 40));
        valueAnimator.start();
    }

    private void hideDropView(final View view) {
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        ValueAnimator valueAnimator = dropAnim(view, DisplayUtil.dip2px(getContext(), 40), 0);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });

        valueAnimator.start();
    }

    private ValueAnimator dropAnim(final View view, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return valueAnimator;
    }

    private void timerAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                timerTv.setText("寿命+" + (Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(100000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    private void startMenuAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                mImageViews.get(0),
                "alpha",
                1f,
                0.5f
        );
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                mImageViews.get(1),
                "translationY",
                200f
        );
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                mImageViews.get(2),
                "translationX",
                200f
        );
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                mImageViews.get(3),
                "translationY",
                -200f
        );
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                mImageViews.get(4),
                "translationX",
                -200f
        );
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.playTogether(
                animator0,
                animator1,
                animator2,
                animator3,
                animator4
        );
        animatorSet.start();
        mFlag = false;
    }

    private void closeMenuAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                mImageViews.get(0),
                "alpha",
                0.5f,
                1f
        );
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                mImageViews.get(1),
                "translationY",
                0
        );
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                mImageViews.get(2),
                "translationX",
                0
        );
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                mImageViews.get(3),
                "translationY",
                0
        );
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                mImageViews.get(4),
                "translationX",
                0
        );
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.playTogether(
                animator0,
                animator1,
                animator2,
                animator3,
                animator4
        );
        animatorSet.start();
        mFlag = true;
    }
}
