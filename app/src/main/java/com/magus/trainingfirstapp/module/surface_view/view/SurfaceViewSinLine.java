package com.magus.trainingfirstapp.module.surface_view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.method.HideReturnsTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by yangshuai in the 14:53 of 2015.12.03 .
 * SurfaceView 绘画我的名字
 */
public class SurfaceViewSinLine extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing; // 子线程标志位
    private Path mPath;
    private Paint mPaint;
    private float x;
    private float y;
    private int mParentWidth;
    private int mParentHeight;
    private float mFontSize;
    private boolean moveFlag = true;
    private boolean drawOval = false;
    private float speed = 5;
    private int drawNow = 0;

    /* 杨 */
    private float mYangCX;
    private float mYangCY;
    private float angle;

    public SurfaceViewSinLine(Context context) {
        super(context);
        init();
    }

    public SurfaceViewSinLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SurfaceViewSinLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        /* 画线必加 */
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mParentWidth = measureSize(widthMeasureSpec, 240);
        mParentHeight = measureSize(heightMeasureSpec, 320);
        mFontSize = mParentWidth / 2 - 5;
        mYangCX = mParentWidth / 4 - 5;
        mYangCY = mParentHeight / 2;
        setMeasuredDimension(mParentWidth, mParentHeight);
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
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        mPath.moveTo(0, mParentHeight / 2);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            Log.d("SurfaceViewSinLine", "drawNow:" + drawNow);
            draw();
            switch (drawNow) {
                case 0:
                    if (x < mYangCX) {
                        x += speed;
                        y = mYangCY;
                    } else drawNow++;
                    break;
                case 1:
                    if (moveFlag) {
                        moveFlag = false;
                        mPath.moveTo(x = mYangCX - mFontSize / 4, y = mYangCY - mFontSize / 2);
                    }
                    if (y < mYangCY + mFontSize / 2) {
                        y += speed;
                    } else {
                        endFlag();
                    }
                    break;
                case 2:
                    if (moveFlag) {
                        moveFlag = false;
                        mPath.moveTo(x = mYangCX - mFontSize / 4, y = mYangCY);
                    }
                    if (y < mYangCY + mFontSize / 2) {
                        y += speed;
                        x -= speed / 2;
                    } else {
                        endFlag();
                    }
                    break;
                case 3:
                    if (moveFlag) {
                        moveFlag = false;
                        mPath.moveTo(x = mYangCX - mFontSize / 4, y = mYangCY);
                    }
                    if (y < mYangCY + mFontSize / 2) {
                        y += speed;
                        x += speed / 2;
                    } else {
                        endFlag();
                    }
                    break;
                case 4:
                    if (moveFlag) {
                        moveFlag = false;
                        angle = 0;
                        drawOval = true;
                    }
                    if (angle <= 180) {
                        mPath.addArc(
                                new RectF(mYangCX - mFontSize / 4 + 20, mYangCY - mFontSize / 2, mYangCX + mFontSize / 4 + 20, mYangCY),
                                -90, angle);
                        angle += speed;
                    } else {
                        endFlag();
                    }
                    break;
                case 5:
                    if (moveFlag) {
                        moveFlag = false;
                        mPath.moveTo(x = mYangCX + 20, y = mYangCY);
                        drawOval = false;
                    }
                    if (x < mYangCX + mFontSize / 2) {
                        x += speed;
                    } else {
                        endFlag();
                    }
                    break;
                case 6:
                    if (moveFlag) {
                        moveFlag = false;
                        mPath.moveTo(x = mYangCX + mFontSize / 2 + speed, y = mYangCY);
                    }
                    if (y < mYangCY + mFontSize / 2) {
                        y += speed;
                    } else {
                        endFlag();
                    }
                    break;
                case 7:
                    if (moveFlag) {
                        moveFlag = false;
                        angle = 0;
                        drawOval = true;
                    }
                    if (angle <= 90) {
                        mPath.addArc(
                                new RectF(mYangCX + 20 -mFontSize / 2 / 3, mYangCY, mYangCX + 20 +mFontSize / 2 / 3, mYangCY + mFontSize / 2 / 3),
                                0, angle);
                        angle += speed;
                    } else {
                        endFlag();
                    }
                    break;
                case 8:
                    if (moveFlag) {
                        moveFlag = false;
                        angle = 0;
                        drawOval = true;
                    }
                    if (angle <= 90) {
                        mPath.addArc(
                                new RectF(mYangCX + 20 -mFontSize  / 3 , mYangCY, mYangCX + 20 +mFontSize  / 3, mYangCY + mFontSize  / 3),
                                0, angle);
                        angle += speed;
                    } else {
                        endFlag();
                    }
                    break;

                default:
                    mIsDrawing = false;
                    break;
            }
            if (!drawOval) {
                mPath.lineTo(x, y);
            }
            Log.d("SurfaceViewSinLine", "x=" + x + ", y=" + y);
        }
    }

    private void endFlag() {
        moveFlag = true;
        drawNow++;
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE); // 清屏
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }
}
