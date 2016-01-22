package com.magus.trainingfirstapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.magus.trainingfirstapp.utils.DisplayUtil;
import com.magus.trainingfirstapp.utils.Log;

/**
 * 监听手势的 View
 */
public class GestureView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private final static String TAG = GestureView.class.getSimpleName();

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private VelocityTracker velocityTracker; // 速度监听
    private GestureDetector gestureDetector; // 手势监听

    public GestureView(Context context) {
        super(context);
        init(null, 0);
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        //        final TypedArray a = getContext().obtainStyledAttributes(
        //                attrs, R.styleable.GestureView, defStyle, 0);
        //
        //
        //        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        velocityTracker = VelocityTracker.obtain();

        gestureDetector = new GestureDetector(getContext(), this);
        gestureDetector.setIsLongpressEnabled(false); // 解决长按屏幕后无法拖动的现象
        gestureDetector.setOnDoubleTapListener(this);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(
                measureSize(widthMeasureSpec, DisplayUtil.dp2px(getContext(), 100)),
                measureSize(heightMeasureSpec, DisplayUtil.dp2px(getContext(), 100))
        );

    }

    private int measureSize(int measureSpec, int defaultSize) {

        int result = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.max(result, size);
        }

        return result;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        obtainXYVelocity(event);
        gestureDetector.onTouchEvent(event);

        return true;
    }

    private void obtainXYVelocity(MotionEvent event) {
        velocityTracker.clear();
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000);
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();
        Log.d("滑动速度", "xv=" + xVelocity + ", yv=" + yVelocity);
    }

    public void releaseView() {
        /* 不需要速度检测的时候需要进行回收 */
        if (velocityTracker != null) {
            velocityTracker.clear();
            velocityTracker.recycle();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        releaseView();
        super.onDetachedFromWindow();
    }

    /* 手指触摸屏幕的一瞬间 */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown");
        return false;
    }

    /* 手指触摸屏幕，还没有松开或拖动 */
    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress");
    }

    /* 手指松开 */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp");
        return false;
    }

    /* 手指按下屏幕并拖动 */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, "onScroll");
        return false;
    }

    /* 手指长按 */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress");
    }

    /* 手指接触屏幕后，快速滑动后松开 */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "onFling");
        return false;
    }


    /* 严格的单机行为 */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(TAG, "onSingleTapConfirmed");
        return false;
    }

    /* 双击 */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap");
        return false;
    }

    /* 发生了双击行为，在双击期间 */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent");
        return false;
    }
}
