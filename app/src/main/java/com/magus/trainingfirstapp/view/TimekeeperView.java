package com.magus.trainingfirstapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.magus.trainingfirstapp.utils.DisplayUtil;

/**
 * Created by yangshuai in the 11:35 of 2015.11.30 .
 */
public class TimekeeperView extends View {

    private int mHeight;
    private int mWidth;
    private int mRadius;
    private Paint mCirclePaint;
    private Paint mDegreePaint;
    private int mCenterX;
    private int mCenterY;
    private Paint mHourPaint;
    private Paint mMinutePaint;
    private int mNowHour;
    private int mNowMinute;

    public TimekeeperView(Context context) {
        super(context);
        init();
    }

    public TimekeeperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimekeeperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(5);

        mDegreePaint = new Paint();

        mHourPaint = new Paint();
        mHourPaint.setStrokeWidth(20);

        mMinutePaint = new Paint();
        mMinutePaint.setStrokeWidth(10);

        mNowHour = 0;
        mNowMinute = -1;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = measerSize(widthMeasureSpec, DisplayUtil.dip2px(getContext(), 240f));
        int parentHeight = measerSize(heightMeasureSpec, DisplayUtil.dip2px(getContext(), 240f));
        setMeasuredDimension(parentWidth, parentHeight);

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mRadius = mHeight > mWidth ? mWidth / 2 : mHeight / 2;
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
        int size = MeasureSpec.getSize(sizeMeasureSpec);
        int result = defaultSize;

        /* 精确值模式，即给控件宽高指定明确的数值，或者match_parent */
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {

             /* 最大值模式，即给控件宽高属性设置为wrap_content时 */
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mNowMinute++;
        if (mNowMinute == 24) {
            mNowHour++;
            mNowMinute = 0;
        }
        if (mNowHour == 24) {
            mNowHour = 0;
        }

        /* 画外圆 */
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);

        /* 画刻度 */
        for (int i = 0; i < 24; i++) {
            String degree = String.valueOf(i);
            int lineStopY;
            int textY;
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                mDegreePaint.setStrokeWidth(5);
                mDegreePaint.setTextSize(30);
                lineStopY = 60;
                textY = 90;
            } else {
                mDegreePaint.setStrokeWidth(3);
                mDegreePaint.setTextSize(15);
                lineStopY = 30;
                textY = 60;
            }
            canvas.drawLine(mCenterX, mCenterY - mRadius, mCenterX, mCenterY - mRadius + lineStopY, mDegreePaint);
            canvas.drawText(degree, mCenterX - mDegreePaint.measureText(degree) / 2, mCenterY - mRadius + textY, mDegreePaint);

            canvas.rotate(15, mCenterX, mCenterY);
        }

        /* 画时针 */
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        canvas.rotate(mNowHour * 15);
        canvas.drawLine(0, 0, 0, -(mRadius / 3), mHourPaint);
        canvas.restore();

        /* 画分针 */
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        canvas.rotate(mNowMinute * 15);
        canvas.drawLine(0, 0, 0, -(mRadius / 3 * 2), mMinutePaint);
        canvas.restore();


        postInvalidateDelayed(1000);
    }
}
