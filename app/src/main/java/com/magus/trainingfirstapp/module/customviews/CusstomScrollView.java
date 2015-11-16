package com.magus.trainingfirstapp.module.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by yangshuai in the 8:54 of 2015.11.16 .
 */
public class CusstomScrollView extends ViewGroup {

    private Scroller mScroller;
    private android.content.Context context;
    private int mScreenHeight;
    private int mLastY;
    private int mStartY;
    private int mEndY;

    public CusstomScrollView(Context context) {
        super(context);

        init(context);
    }

    public CusstomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CusstomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mScroller = new Scroller(context);

        /* 获取屏幕高度 */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("CusstomScrollView", "onMeasure");

        /* 先对父控件进行测量 */
        int parentWidth = measerSize(widthMeasureSpec, dip2px(320));
        int parentHeight = measerSize(heightMeasureSpec, dip2px(240));
        setMeasuredDimension(parentWidth, parentHeight);

        /* 测量子控件 */
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), parentWidth, parentHeight);
        }

    }

    /**
     * 测量
     *
     * @param sizeMeasureSpec
     * @param defaultSize
     * @return
     */
    private int measerSize(int sizeMeasureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(sizeMeasureSpec);
        int result = defaultSize;

        switch (mode) {

            /* 精确值模式，即给控件宽高指定明确的数值，或者match_parent */
            case MeasureSpec.EXACTLY:
                result = sizeMeasureSpec;
                break;

            /* 最大值模式，即给控件宽高属性设置为wrap_content时 */
            case MeasureSpec.AT_MOST:
                result = Math.min(result, sizeMeasureSpec);
                break;

            /* 不指定大小测量模式，view想多大就多大，通常情况下自定义view的时候才会使用 */
            case MeasureSpec.UNSPECIFIED:
                break;

        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("CusstomScrollView", "onLayout");

        int childCount = getChildCount();

        /* 设置 viewgroup 高度 */
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = mScreenHeight * childCount;
        setLayoutParams(marginLayoutParams);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;

                /* 记录触摸起点 */
                mStartY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;

                /* 滑到顶部，不能再滑动 */
                if (getScrollY() < 0) {
                    dy = 0;
                }

                /* 滑动底部，不能再滑动 */
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:

                /* 记录触摸终点 */
                mEndY = getScrollY();
                int dScrollY = mEndY - mStartY;

                    /* 滑动小于屏幕的三分之一，就滚回去 */
                    if (Math.abs(dScrollY) < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else if (dScrollY > 0) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, mScreenHeight - dScrollY
                        );
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -(mScreenHeight + dScrollY));
                    }

                break;
        }
        postInvalidate();

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }


    /**
     * dip 转 px
     *
     * @param dip
     * @return
     */
    public final int dip2px(float dip) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) (displayMetrics.density * dip);
    }
}
