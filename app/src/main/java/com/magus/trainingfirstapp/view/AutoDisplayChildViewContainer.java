package com.magus.trainingfirstapp.view;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.magus.trainingfirstapp.utils.DisplayUtil;

/**
 * Created by yangshuai in the 17:25 of 2015.12.02 .
 */
public class AutoDisplayChildViewContainer extends ViewGroup {

    private int parentWidth;
    private int totaleft = 0;
    private int totalTop = 0;
    private int margin = 10;
    private int maxChildHeight = 0;
    private int totalRight = 0;

    public AutoDisplayChildViewContainer(Context context) {
        super(context);
    }

    public AutoDisplayChildViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoDisplayChildViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /* 测量父布局 */
        parentWidth = measureSize(widthMeasureSpec, DisplayUtil.dip2px(getContext(), 240));

        int count = getChildCount();
        int tempMaxChildHeight = 0;
        int tempTotalHeight = 0;
        int tempTotalRight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {

                if (child.getMeasuredWidth() > parentWidth) {
                    child.measure(parentWidth, child.getMeasuredHeight());
                } else
                    child.measure(child.getMeasuredWidth(), child.getMeasuredHeight());

                tempMaxChildHeight = Math.max(tempMaxChildHeight, child.getMeasuredHeight());
                tempTotalRight += child.getMeasuredWidth();
                if (tempTotalRight > parentWidth) {
                    tempTotalHeight += tempMaxChildHeight;
                    tempMaxChildHeight = child.getMeasuredHeight();
                    tempTotalRight = child.getMeasuredWidth();
                }
            }
        }

        int parentHeight = tempTotalHeight + tempMaxChildHeight + margin;
        setMeasuredDimension(parentWidth, parentHeight);
    }

    private int measureSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = defaultSize;

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.max(size, result);
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        int lineViewCount = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {

                maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight());
                if (i != 0) {
                    totaleft += getChildAt(i - 1).getMeasuredWidth() + margin;
                } else {
                    totaleft = 0;
                    totalTop = 0;
                }
                totalRight = totaleft + child.getMeasuredWidth();

                if (totalRight > parentWidth) {

                    adjustLine(lineViewCount, i);
                    lineViewCount = 0;
                    totalTop += maxChildHeight;
                    totaleft = 0;
                    maxChildHeight = child.getMeasuredHeight();
                    totalRight = child.getMeasuredWidth();
                }

                child.layout(
                        totaleft,
                        totalTop,
                        totalRight,
                        totalTop + child.getMeasuredHeight()
                );
                lineViewCount++;
            }
        }

        totaleft= totalRight + margin;
        adjustLine(lineViewCount, count);

    }

    private void adjustLine(int lineViewCount, int i) {
        totaleft = (parentWidth - totaleft) / 2;
        for (int lineViewNumber = lineViewCount; lineViewNumber > 0; lineViewNumber--) {
            View lineViewChild = getChildAt(i - lineViewNumber);
            totalRight = totaleft + lineViewChild.getMeasuredWidth();
            lineViewChild.layout(totaleft, totalTop, totalRight, totalTop + lineViewChild.getMeasuredHeight());
            totaleft += lineViewChild.getMeasuredWidth() + margin;
        }
    }

}
