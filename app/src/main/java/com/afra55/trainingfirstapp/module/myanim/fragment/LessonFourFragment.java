package com.afra55.trainingfirstapp.module.myanim.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.utils.BitmapUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFourFragment extends BaseFragment {

    private Animator mCurrentAnimator;

    private FrameLayout container;

    private ImageButton ibtOne;
    private ImageButton ibtTwo;

    /**
     * 隐藏的“放大版”的ImageView
     */
    private ImageView expandedImg1;
    private ImageView expandedImg2;

    private int mShortAnimationDuration;

    /**
     * 创建实例
     *
     * @param param1
     * @param param2
     * @return
     */
    public static LessonFourFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        LessonFourFragment instance = new LessonFourFragment();
        instance.setArguments(args);
        return instance;
    }


    public LessonFourFragment() {
        setFragmentTitle("Lesson 4 缩放View");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_four, container, false);
        ibtOne = (ImageButton) view.findViewById(R.id.lesson_four_img_one_ibt);
        ibtTwo = (ImageButton) view.findViewById(R.id.lesson_four_img_two_ibt);
        expandedImg1 = (ImageView) view.findViewById(R.id.lesson_four_expanded_img_1);
        expandedImg2 = (ImageView) view.findViewById(R.id.lesson_four_expanded_img_2);
        this.container = (FrameLayout) view.findViewById(R.id.lesson_four_container);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        ibtOne.setOnClickListener(this);
        ibtTwo.setOnClickListener(this);

        // 为大图设置资源文件
        BitmapUtils.loadBitmap(getContext(), R.drawable.image2, expandedImg2);
        BitmapUtils.loadBitmap(getContext(), R.drawable.image1, expandedImg1);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lesson_four_img_one_ibt:
                LessonFoutZoomImageFromThumb(container, expandedImg1, ibtOne, R.drawable.image1);
                break;
            case R.id.lesson_four_img_two_ibt:
                LessonFoutZoomImageFromThumb(container, expandedImg2, ibtTwo, R.drawable.image2);
                break;
        }
        super.onClick(v);
    }

    private AnimatorSet mLessonFourCurrentAnimator;

    /**
     * leson 4 缩放动画
     * <p/>
     * 1 把高清图像资源设置到已经被隐藏的“放大版”的ImageView中。
     * 需要在一个单独的线程中来加载以免阻塞 UI 线程，然后再回到 UI 线程中设置。理想状况下，图片不要大过屏幕尺寸。
     * <p/>
     * 2 计算ImageView开始和结束时的边界。
     * <p/>
     * 3 从起始边到结束边同步地动态改变四个位置和大小属性X，Y（SCALE_X 和 SCALE_Y）。这四个动画被加入到了AnimatorSet，让它们一起开始。
     * <p/>
     * 4 缩小则运行相同的动画，但是是在用户点击屏幕放大时的逆向效果。可以在ImageView中添加一个View.OnClickListener来实现它。
     * 当点击时，ImageView缩回到原来缩略图的大小，然后设置它的visibility为GONE来隐藏。
     *
     */
    private void LessonFoutZoomImageFromThumb(FrameLayout container, final ImageView expandedImg,final View thumbView, int imageResId) {

        // 先取消正在运行的动画
        if (mLessonFourCurrentAnimator != null){
            mLessonFourCurrentAnimator.cancel();
        }


        // 计算放大图像的起始和结束范围， 涉及了大量的数学，在哪里数学都很有用啊
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // 获取开始坐标
        thumbView.getGlobalVisibleRect(startBounds);

        //一下的offset设置请看源码注释
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // 为了使图片按比例在中间显示需要对开始的边界调整
        float startScale;
        if ((float)finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()){
            startScale = (float)startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth -startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        }else{
            startScale = (float) startBounds.width() / finalBounds.height();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0);
        expandedImg.setVisibility(View.VISIBLE);

        expandedImg.setPivotX(0);
        expandedImg.setPivotY(0);

        // 构建和运行四个并行动画的移动和缩放属性(X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImg, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImg, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImg, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImg, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mLessonFourCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mLessonFourCurrentAnimator = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
        mLessonFourCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLessonFourCurrentAnimator != null) {
                    mLessonFourCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImg, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImg,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImg,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImg,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImg.setVisibility(View.GONE);
                        mLessonFourCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImg.setVisibility(View.GONE);
                        mLessonFourCurrentAnimator = null;
                    }
                });
                set.start();
                mLessonFourCurrentAnimator = set;
            }
        });


    }

}
