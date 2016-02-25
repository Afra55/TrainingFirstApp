package com.afra55.trainingfirstapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yangshuai in the 14:27 of 2015.11.07 .
 */
public class FlashTextView extends TextView {

    private int mViewWidth = 0;
    private Paint mPaint;
    private LinearGradient mLinearGragient;
    private Matrix mMatrix;
    private int mTranslate;

    public FlashTextView(Context context) {
        super(context);
    }

    public FlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0){
                mPaint = getPaint();
                mLinearGragient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{
                                Color.BLUE,
                                0xffffffff,
                                Color.BLUE
                        },
                        null,
                        Shader.TileMode.CLAMP
                );
                mPaint.setShader(mLinearGragient);
                mMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGragient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }
}
