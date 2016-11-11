package com.afra55.trainingfirstapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.afra55.trainingfirstapp.R;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Victor Yang on 2016/11/10.
 * 录音效果动画。
 */

public class RandomWaveView extends View {

    private final String TAG = RandomWaveView.class.getName();

    private boolean isStartAction = false;

    private Random random;

    private float lineSpaceOneWidth = dip2px(2);

    private Drawable resetDrawable;
    private Drawable startDrawable;

    private Paint mColorRectPaint;
    private Paint mTimePaint;

    private int mCenterLineDashRectCount;
    private int mLineSpaceCount;
    private float mTimeTextWidth;

    private float mCurrentValue = 0f;
    private String mTimeStr = "01:00";

    public RandomWaveView(Context context) {
        this(context, null, 0);
    }

    public RandomWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandomWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RandomWaveView, defStyleAttr, defStyleAttr);

        int integralColor;
        float mLabelTextSize;
        int mTimeTextColor;
        try {
            integralColor = a.getColor(
                    R.styleable.RandomWaveView_integralColor
                    , getResources().getColor(R.color.color_yellow_fad17d));

            if (a.hasValue(R.styleable.RandomWaveView_restDrawable)) {
                resetDrawable = a.getDrawable(R.styleable.RandomWaveView_restDrawable);
            } else {
                resetDrawable = getContext().getResources()
                        .getDrawable(R.drawable.action_stop_btn);
            }

            if (a.hasValue(R.styleable.RandomWaveView_startDrawable)) {
                startDrawable = a.getDrawable(R.styleable.RandomWaveView_startDrawable);
            } else {
                startDrawable = getContext().getResources()
                        .getDrawable(R.drawable.action_start_btn);
            }

            mCenterLineDashRectCount = a.getInt(
                    R.styleable.RandomWaveView_centerLineDashRectCount
                    , 50);
            mLineSpaceCount = mCenterLineDashRectCount - 1;

            mLabelTextSize = a.getDimension(R.styleable.RandomWaveView_timeTextSize, dip2px(18));
            mTimeTextColor = a.getColor(R.styleable.RandomWaveView_timeTextColor, getContext().getResources().getColor(R.color.black));
        } finally {
            a.recycle();
        }

        mColorRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorRectPaint.setStyle(Paint.Style.FILL);
        mColorRectPaint.setColor(integralColor);

        mTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimePaint.setStyle(Paint.Style.STROKE);
        mTimePaint.setColor(mTimeTextColor);
        mTimePaint.setTextSize(mLabelTextSize);

        mTimeTextWidth = mTimePaint.measureText("00:00");
        float mTimeTextHeight = Math.abs(mTimePaint.getFontMetrics().top);

        random = new Random();
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = measureSize(widthMeasureSpec, dip2px(320));
        int parentHeight = measureSize(heightMeasureSpec, dip2px(240));
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

    public void updataVolValues(int value, String time) {

        isStartAction = true;
        mCurrentValue = (float) (value) / 45000;
        if (mCurrentValue > 1) {
            mCurrentValue = 1;
        }
        Log.d(TAG, "Value : " + value + " | mCurrentValue = " + mCurrentValue);
        mTimeStr = changetime(time);
        invalidate();
    }

    private String changetime(String time) {
        String[] times = time.split(":");
        int timevalue = Integer.valueOf(times[0]) * 60 + Integer.valueOf(times[1]);
        int sumtime = 1 * 60;
        int subtime = sumtime - timevalue;
        if (subtime < 0) {
            subtime = 0;
        }
        int min = subtime / 60;
        int second = subtime % 60;

        DecimalFormat df = new DecimalFormat("00.00");
        String ret = df.format(min + second * 0.01).replace(".", ":");
        return ret;
    }

    public void resetView() {
        isStartAction = false;
        invalidate();
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
        int centerX = contentWidth / 2;
        int centerY = contentHeight / 2;


        float rectWidth =
                (contentWidth - lineSpaceOneWidth * mLineSpaceCount)
                        / mCenterLineDashRectCount;

        if (isStartAction) {
            drawActionStateView(canvas, contentHeight, centerX, centerY, lineSpaceOneWidth, rectWidth);
        } else {
            drawResetStateView(canvas, centerX, centerY, lineSpaceOneWidth, rectWidth);
        }
    }

    private void drawActionStateView(Canvas canvas, int contentHeight, int centerX, int centerY, float lineSpaceOneWidth, float rectWidth) {
        drawCenterLine(canvas, centerY, lineSpaceOneWidth, rectWidth, rectWidth);
        int topRectMaxCount = (int) ((contentHeight - rectWidth) / 2 / (rectWidth + lineSpaceOneWidth));
        for (int rectIndex = 0; rectIndex < mCenterLineDashRectCount; rectIndex++) {
            int v = (int) (topRectMaxCount * mCurrentValue);
            int showTopRectCount = v + random.nextInt(3);
            for (int i = 0 ; i < showTopRectCount; i++) {
                float subWidth = rectWidth * ((float) i / topRectMaxCount) / 2;
                @SuppressLint("DrawAllocation")
                RectF topRectF = new RectF(
                        getRectDrawLeft(lineSpaceOneWidth, rectWidth, rectIndex) + subWidth
                        , getCenterLineRectTop(centerY, rectWidth)
                                - lineSpaceOneWidth - (i + 1) * rectWidth - i * lineSpaceOneWidth
                                + subWidth
                        , getRectDrawLeft(lineSpaceOneWidth, rectWidth, rectIndex) + rectWidth - subWidth
                        , getCenterLineRectTop(centerY, rectWidth)
                                - lineSpaceOneWidth - i * (rectWidth + lineSpaceOneWidth)
                                - subWidth);
                canvas.drawRect(topRectF, mColorRectPaint);

                @SuppressLint("DrawAllocation")
                RectF bottomRectF = new RectF(
                        getRectDrawLeft(lineSpaceOneWidth, rectWidth, rectIndex) + subWidth
                        , getCenterLineRectBottom(centerY, rectWidth)
                                + lineSpaceOneWidth + i * (rectWidth + lineSpaceOneWidth)
                                + subWidth
                        , getRectDrawLeft(lineSpaceOneWidth, rectWidth, rectIndex) + rectWidth - subWidth
                        , getCenterLineRectBottom(centerY, rectWidth)
                                + lineSpaceOneWidth + (i + 1) * rectWidth + i * lineSpaceOneWidth
                                - subWidth);
                canvas.drawRect(bottomRectF, mColorRectPaint);
            }
        }
        drawCenterIcon(canvas, startDrawable, centerX, centerY);

        int timeY = centerY +  dip2px(10);
        if (startDrawable != null) {
            timeY += startDrawable.getIntrinsicHeight() / 2;
        }
        canvas.drawText(mTimeStr, centerX - mTimeTextWidth / 2
                , timeY, mTimePaint);


    }

    private void drawResetStateView(Canvas canvas, int centerX, int centerY, float lineSpaceOneWidth, float rectWidth) {
        drawCenterLine(canvas, centerY, lineSpaceOneWidth, rectWidth, dip2px(2));
        drawCenterIcon(canvas, resetDrawable, centerX, centerY);
    }

    private void drawCenterLine(Canvas canvas, int centerY, float lineSpaceOneWidth, float rectWidth, float rectHeight) {
        for (int rectIndex = 0; rectIndex < mCenterLineDashRectCount; rectIndex ++) {
            @SuppressLint("DrawAllocation")
            RectF rectF = new RectF(
                    getRectDrawLeft(lineSpaceOneWidth, rectWidth, rectIndex)
                    , getCenterLineRectTop(centerY, rectHeight)
                    , getRectDrawLeft(lineSpaceOneWidth, rectWidth, rectIndex) + rectWidth
                    , getCenterLineRectBottom(centerY, rectHeight));
            canvas.drawRect(rectF, mColorRectPaint);
        }
    }

    private float getCenterLineRectBottom(int centerY, float rectHeight) {
        return centerY + rectHeight / 2;
    }

    private float getCenterLineRectTop(int centerY, float rectHeight) {
        return centerY - rectHeight / 2;
    }

    private float getRectDrawLeft(float lineSpaceOneWidth, float rectWidth, int rectIndex) {
        return rectIndex * (rectWidth + lineSpaceOneWidth);
    }

    private void drawCenterIcon(Canvas canvas, Drawable drawable, int centerX, int centerY) {
        if (drawable != null) {
            drawable.setBounds(
                    centerX - drawable.getIntrinsicWidth() / 2
                    , centerY - drawable.getIntrinsicHeight() / 2
                    , centerX + drawable.getIntrinsicWidth() / 2
                    , centerY + drawable.getIntrinsicHeight() / 2
            );
            drawable.draw(canvas);
        }
    }
}
