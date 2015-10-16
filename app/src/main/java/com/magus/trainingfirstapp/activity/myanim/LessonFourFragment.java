package com.magus.trainingfirstapp.activity.myanim;


import android.animation.Animator;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.magus.trainingfirstapp.BaseFragment;
import com.magus.trainingfirstapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFourFragment extends BaseFragment {

    private Animator mCurrentAnimator;

    private ImageButton ibtOne;
    private ImageButton ibtTwo;

    /**
     * 隐藏的“放大版”的ImageView
     */
    private ImageView expandedImg;

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
        setFragmentTitleSting("Lesson 4 缩放View");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_four, container, false);
        ibtOne = (ImageButton) view.findViewById(R.id.lesson_four_img_one_ibt);
        ibtTwo = (ImageButton) view.findViewById(R.id.lesson_four_img_two_ibt);
        expandedImg = (ImageView) view.findViewById(R.id.lesson_for_expanded_img);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        ibtOne.setOnClickListener(this);
        ibtTwo.setOnClickListener(this);
        expandedImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lesson_four_img_one_ibt:

                break;
            case R.id.lesson_four_img_two_ibt:

                break;
            case R.id.lesson_for_expanded_img:

                break;
        }
        super.onClick(v);
    }

    /**
     * 缩放动画
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
     * @param thumbView
     * @param imageResId
     */
    private void zoomImageFromThumb(final View thumbView, int imageResId) {

        //先取消正在运行的动画
        if (mCurrentAnimator != null){
            mCurrentAnimator.cancel();
        }

        //为大图设置资源文件
        expandedImg.setImageResource(imageResId);



    }
}
