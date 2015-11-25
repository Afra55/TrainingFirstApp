package com.magus.trainingfirstapp.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by yangshuai in the 10:57 of 2015.11.25 .
 */
public class DragViewTestGroup extends FrameLayout {

    private View mMeneView; // 菜单view
    private ViewDragHelper mViewDragHelper = null;
    private int mMenuWidth; // 菜单宽度

    public DragViewTestGroup(Context context) {
        super(context);
        init();
    }

    public DragViewTestGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragViewTestGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        /* 子 view 的最后一个作为菜单 */
        mMeneView = getChildAt(getChildCount() - 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /* 获取菜单的宽度 */
        mMenuWidth = mMeneView.getMeasuredWidth();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, callback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT); // 启用边缘追踪
    }


    /* 回调 */
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            /* 如果布局是菜单布局 */
            return mMeneView == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            /* 如果你拖动菜单的左边距大于0， 则禁止拖动*/
            if (left > 0) {
                return 0;
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            /* 禁止垂直拖动 */
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            /* 当拖动到宽度的三分之二时释放展开菜单，否则隐藏 */
            if (mMeneView.getRight() < mMenuWidth / 3 * 2) {
                mViewDragHelper.smoothSlideViewTo(mMeneView, -mMenuWidth + 1, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewTestGroup.this);
            } else {
                mViewDragHelper.smoothSlideViewTo(mMeneView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewTestGroup.this);
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);

            /* 检测是否是左边缘拖动 */
            if (edgeFlags == ViewDragHelper.EDGE_LEFT) {
                mViewDragHelper.captureChildView(mMeneView, pointerId);
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            /* 当位置大于0时移动到0 */
            if (changedView == mMeneView && left > 0) {
                mViewDragHelper.smoothSlideViewTo(mMeneView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewTestGroup.this);
            }
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
