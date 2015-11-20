package com.magus.trainingfirstapp.module.customviews.charting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.utils.CommontUtils;

/**
 * Created by yangshuai in the 12:49 of 2015.11.07 .
 */
public class BorderTextview extends TextView {

    private Paint mContentPaint;
    private Paint mBorderPaint;

    public BorderTextview(Context context) {
        super(context);
        init();
    }

    public BorderTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BorderTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /* 初始化内部画笔 */
        mContentPaint = new Paint();
        mContentPaint.setColor(getResources().getColor(R.color.blue));
        mContentPaint.setStyle(Paint.Style.FILL);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(getResources().getColor(R.color.red));
        mBorderPaint.setStyle(Paint.Style.FILL);

        float textSize = getTextSize();
        int textLength = getText().length();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float textSize = getTextSize();
        int textWidth = (int)getPaint().measureText(getText().toString());
        setMeasuredDimension(
                measureSize(widthMeasureSpec, CommontUtils.dip2px(textWidth - 30)),
                measureSize(heightMeasureSpec, CommontUtils.dip2px(textSize)));
    }

    private int measureSize(int specMeasureSize, int minSize) {
        int result = 0;
        int specMode = MeasureSpec.getMode(specMeasureSize);
        int specSize = MeasureSpec.getSize(specMeasureSize);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = minSize;
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mBorderPaint);
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mContentPaint);
        canvas.save();
        canvas.translate(10, 10);
        super.onDraw(canvas);
        canvas.restore();
    }
}
