package com.afra55.trainingfirstapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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

    private Paint mPaint;

    private LinearLayout.LayoutParams defaultChildLayoutParams;

    private Path mTrianglePath = new Path();

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

        setHorizontalScrollBarEnabled(false);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);

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
        setViewPager(viewPager, 0);
    }

    public void setViewPager(ViewPager viewPager, int currentNum) {
        mViewPager = viewPager;

        if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        mViewPager.addOnPageChangeListener(mPageChangeListener);

        mChildCount = mViewPager.getAdapter().getCount();

        if (currentNum < 0 || currentNum >= mChildCount) {
            throw new IllegalStateException("Current page num must bigger than 0 and small than child count.");
        }

        notifyDataSetChanged(currentNum);
    }

    public void notifyDataSetChanged(int currentNum) {
        if (mViewPager == null || mChildCount <= 0) {
            return;
        }
        mContainer.removeAllViews();

        for (int i = 0; i < mChildCount; i++) {
            addChild(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }

        if (currentNum > 0 && currentNum < mChildCount) {
            mViewPager.setCurrentItem(currentNum, false);
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

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            View view = mContainer.getChildAt(position);
            int left = view.getLeft();
            int childWidth = view.getMeasuredWidth();
            int moveLength = childWidth;
            if (position + 1 < mChildCount) {
                moveLength += mContainer.getChildAt(position + 1).getMeasuredWidth();
            }
            moveLength /= 2;
            scrollTo((int) (left - (mWidth - childWidth) / 2 + positionOffset * moveLength), 0);
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MotionEvent tempMotionEvent = ev;
        tempMotionEvent.setLocation(ev.getX(), 0);
        mViewPager.dispatchTouchEvent(tempMotionEvent);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        MotionEvent tempMotionEvent = ev;
        tempMotionEvent.setLocation(ev.getX(), 0);
        mViewPager.onInterceptTouchEvent(tempMotionEvent);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        MotionEvent tempMotionEvent = ev;
        tempMotionEvent.setLocation(ev.getX(), 0);
        mViewPager.onTouchEvent(tempMotionEvent);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTrianglePath.reset();
        mTrianglePath.moveTo(getScrollX() + getMeasuredWidth() / 2 - 10, 0);
        mTrianglePath.lineTo(getScrollX() + getMeasuredWidth() / 2 + 10, 0);
        mTrianglePath.lineTo(getScrollX() + getMeasuredWidth() / 2, 20);
        canvas.drawPath(mTrianglePath, mPaint);
    }
}
