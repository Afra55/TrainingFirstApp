package com.magus.trainingfirstapp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.magus.trainingfirstapp.R;

/**
 * Created by yangshuai in the 14:51 of 2015.11.20 .
 */
public class MoveView extends View {

    private int oneBg;
    private int twoBg;
    private int threeBg;
    private Paint onePaint;
    private Paint twoPaint;
    private Paint threePaint;
    RectF rectFOne;
    RectF rectFTwo;
    RectF rectFThree;
    private Scroller mScroller;

    public MoveView(Context context) {
        super(context);
        init(null, 0);
    }

    public MoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MoveView, defStyleAttr, 0);
        oneBg = typedArray.getColor(R.styleable.MoveView_oneBg, Color.BLUE);
        twoBg = typedArray.getColor(R.styleable.MoveView_twoBg, Color.YELLOW);
        threeBg = typedArray.getColor(R.styleable.MoveView_threeBg, Color.RED);
        typedArray.recycle();

        onePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        twoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        threePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        onePaint.setColor(oneBg);
        twoPaint.setColor(twoBg);
        threePaint.setColor(threeBg);

        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("CusstomScrollView", "onMeasure");

        /* 先对父控件进行测量 */
        int parentWidth = measerSize(widthMeasureSpec, dip2px(320));
        int parentHeight = measerSize(heightMeasureSpec, dip2px(240));
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectFOne = new RectF(0, 0, w, h);
        rectFTwo = new RectF(w / 6, h / 6, w / 6 * 5, h / 6 * 5);
        rectFThree = new RectF(w / 6 * 2, h / 6 * 2, w / 6 * 4, h / 6 * 4);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(rectFOne, onePaint);


        canvas.drawOval(rectFTwo, twoPaint);


        canvas.drawOval(rectFThree, threePaint);

        int tempBg = oneBg;
        oneBg = twoBg;
        twoBg = threeBg;
        threeBg = tempBg;

        onePaint.setColor(oneBg);
        twoPaint.setColor(twoBg);
        threePaint.setColor(threeBg);

        postInvalidateDelayed(500);
    }

    private float lastPointX;
    private float lastPointY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                lastPointX = event.getX();
                lastPointY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) (pointX - lastPointX);
                int dy = (int) (pointY - lastPointY);

                /* 使用layout方法 */
//                layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);

                /* 使用scrollBy方法 */
                View parent = (View) getParent();
                parent.scrollBy(-dx, -dy);
                break;

            case MotionEvent.ACTION_UP:
                View viewParent = (View) getParent();
                mScroller.startScroll(
                        viewParent.getScrollX(),
                        viewParent.getScrollY(),
                        -viewParent.getScrollX(),
                        -viewParent.getScrollY(), 3000);
                break;
        }
        return true;
    }

    /* 使用scoller */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            postInvalidate();
        }
    }
}
