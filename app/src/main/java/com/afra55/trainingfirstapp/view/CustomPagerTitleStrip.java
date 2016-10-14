package com.afra55.trainingfirstapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Victor Yang on 2016/10/14.
 * CustomPagerTitleStrip
 */

public class CustomPagerTitleStrip extends HorizontalScrollView {

    private final String TAG = CustomPagerTitleStrip.class.getSimpleName();

    private ViewPager mViewPager;

    private PageChangeListener mPageChangeListener = new PageChangeListener();

    private LinearLayout mContainer;

    private int mChildCount;

    private int mWidth;

    private int mContainerStartPadding;

    private LinearLayout.LayoutParams defaultChildLayoutParams;

    public CustomPagerTitleStrip(Context context) {
        super(context);
        init(context);
    }

    public CustomPagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomPagerTitleStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setFillViewport(true);
        setWillNotDraw(false);


        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT)
        );
        addView(mContainer);

        defaultChildLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        setStratAndEndPadding();
    }

    private void setStratAndEndPadding() {
        int mContainerChildCount = mContainer.getChildCount();
        if (mContainerChildCount <= 0) {
            return;
        }
        if (mContainerChildCount == 1) {
            mContainer.setGravity(Gravity.CENTER);
        } else {
            View childAtStart = mContainer.getChildAt(0);
            mContainerStartPadding = (mWidth - childAtStart.getMeasuredWidth()) / 2;

            View childAtEnd = mContainer.getChildAt(mContainerChildCount - 1);
            int mContainerEndPadding = (mWidth - childAtEnd.getMeasuredWidth()) / 2;
            mContainer.setPadding(mContainerStartPadding, 0, mContainerEndPadding, 0);

            Log.d(TAG, "mContainerStartPadding = " + mContainerStartPadding);
            Log.d(TAG, "mContainerEndPadding = " + mContainerEndPadding);
        }
    }


    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;

        if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        mViewPager.addOnPageChangeListener(mPageChangeListener);

        mChildCount = mViewPager.getAdapter().getCount();

        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (mViewPager == null || mChildCount <= 0) {
            return;
        }
        mContainer.removeAllViews();

        for (int i = 0; i < mChildCount; i++) {
            addChild(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }


    }

    private void addChild(final int position, String title) {
        TextView textView = new TextView(getContext());
//        textView.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                        , ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(50, 0, 50, 0);
        textView.setText(title);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });
        mContainer.addView(textView, position, defaultChildLayoutParams);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            View view = mContainer.getChildAt(position);
            int left = view.getLeft();
            smoothScrollTo((int) (left - mContainerStartPadding + positionOffset * view.getMeasuredWidth()), 0);
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
