package com.magus.trainingfirstapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.magus.trainingfirstapp.utils.DisplayUtil;

/**
 * Created by yangshuai in the 10:30 of 2015.12.02 .
 */
public class TwoViewALineLinearLayout extends LinearLayout {

    private int parentWidth;
    private int parentHeight;
    private int childHeight;
    private int childWidth;
    private int margin;

    public TwoViewALineLinearLayout(Context context) {
        super(context);
    }

    public TwoViewALineLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoViewALineLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        margin = DisplayUtil.dip2px(getContext(), 10);

        /* 测量父控件 */
        parentWidth = measureSize(widthMeasureSpec, DisplayUtil.dip2px(getContext(), 320f));
        childHeight = parentWidth / 4;
        childWidth = parentWidth / 2 - margin / 2;
        parentHeight = measureSize(heightMeasureSpec, getChildCount() * childHeight / 2 + childHeight);
        setMeasuredDimension(parentWidth, parentHeight);

        /* 测量子控件 */
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(childWidth, childHeight);
        }
    }

    private int measureSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = defaultSize;

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.max(result, size);
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(
                        (i % 2) * childWidth + (i % 2) * margin,
                        (i / 2) * childHeight + margin,
                        (i % 2 + 1) * childWidth,
                        (i / 2 + 1) * childHeight + margin);
            }
        }
    }
}
