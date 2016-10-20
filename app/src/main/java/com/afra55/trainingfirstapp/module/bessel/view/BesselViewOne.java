package com.afra55.trainingfirstapp.module.bessel.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Victor Yang on 2016/10/19.
 * 一阶贝塞尔曲线
 */

public class BesselViewOne extends View {

    private Paint mPaint;
    private Paint mGridPaint;
    private Paint mBluePaint;
    private Paint mRedPaint;
    private Path mOnePath;
    private Path mTwoPath;
    private Path mOneBesselPath;
    private RectF mCircleRectF;
    private PointF mTwoLevelBesselStartPoint, mTwoLevelBesselMovePoint, mTwoLevelBesselEndPoint;

    public BesselViewOne(Context context) {
        super(context);
        init(null, 0);
    }

    public BesselViewOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BesselViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 创建画笔
        mPaint = new Paint();
        mGridPaint = new Paint();
        mBluePaint = new Paint();
        mRedPaint = new Paint();

        initPaint(mPaint, Paint.Style.STROKE, Color.BLACK, 10);
        initPaint(mGridPaint, Paint.Style.STROKE, Color.RED, 1);
        initPaint(mBluePaint, Paint.Style.STROKE, Color.BLUE, 1);
        initPaint(mRedPaint, Paint.Style.STROKE, Color.RED, 1);

        mOnePath = new Path();
        mTwoPath = new Path();
        mOneBesselPath = new Path();
        mCircleRectF = new RectF(-100, -100, 100, 100);

        mTwoLevelBesselStartPoint = new PointF(0, 0);
        mTwoLevelBesselMovePoint = new PointF(0, 0);
        mTwoLevelBesselEndPoint = new PointF(0, 0);
    }

    private void initPaint(Paint paint, Paint.Style style, int color, int width) {
        paint.setStyle(style);
        paint.setColor(color);
        paint.setStrokeWidth(width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTwoLevelBesselStartPoint.set(getWidth() / 4 * 3 - 100, getHeight() / 2);
        mTwoLevelBesselEndPoint.set(mTwoLevelBesselStartPoint.x + 200, mTwoLevelBesselStartPoint.y);
        mTwoLevelBesselMovePoint.set((mTwoLevelBesselStartPoint.x + mTwoLevelBesselEndPoint.x) / 2, mTwoLevelBesselStartPoint.y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (eventX >= getWidth() / 2 && eventY >= getHeight() / 4 && eventY <= getHeight() / 4 * 3) {
                    mTwoLevelBesselMovePoint.set(eventX, eventY);
                } else {
                    mTwoLevelBesselMovePoint.set((mTwoLevelBesselStartPoint.x + mTwoLevelBesselEndPoint.x) / 2, mTwoLevelBesselStartPoint.y);
                }
                break;
            case MotionEvent.ACTION_UP:
                mTwoLevelBesselMovePoint.set((mTwoLevelBesselStartPoint.x + mTwoLevelBesselEndPoint.x) / 2, mTwoLevelBesselStartPoint.y);
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int one = canvas.save();
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mGridPaint);
        canvas.drawLine(0, getHeight() / 4, getWidth(), getHeight() / 4, mGridPaint);
        canvas.drawLine(0, getHeight() / 4 * 3, getWidth(), getHeight() / 4 * 3, mGridPaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mGridPaint);
        canvas.drawLine(getWidth() / 4, 0, getWidth() / 4, getHeight(), mGridPaint);
        canvas.drawLine(getWidth() / 4 * 3, 0, getWidth() / 4 * 3, getHeight(), mGridPaint);
        canvas.restoreToCount(one);

        int two = canvas.save();
        canvas.translate(getWidth() / 4, getHeight() / 4);
        mPaint.setColor(Color.BLACK);
        mOnePath.reset();
        mOnePath.lineTo(200, 0);  // lineTo
        mOnePath.lineTo(200, 200);
        // mOnePath.moveTo(-100, 0);
        mOnePath.setLastPoint(-200, -200); // 重改上一个点
        mOnePath.lineTo(-20, 111);
        mOnePath.close(); // 连接第一个点和最后一个点，如果路径无法封闭，则不连接。
        canvas.drawPath(mOnePath, mPaint);   // 绘制Path
        canvas.restoreToCount(two);

        int three = canvas.save();
        canvas.translate(getWidth() / 4 * 3, getHeight() / 4);
        mPaint.setColor(Color.CYAN);
        mOnePath.reset();

        // Path.Direction.CCW 的绘制顺序是 从左上角 左下角，右下，右上，即逆时针绘制。
        // Path.Direction.CW 的绘制顺序是 从左上角  ，右上 右下，左下角， 即顺时针绘制。
        mOnePath.addRect(-100, -100, 100, 100, Path.Direction.CW);
        mOnePath.setLastPoint(300, -300);
        canvas.drawPath(mOnePath, mPaint);
        canvas.restoreToCount(three);

        int four = canvas.save();
        canvas.translate(getWidth() / 4, getHeight() / 2);
        mOnePath.reset();
        mOnePath.lineTo(0, -50);
        mOnePath.arcTo(mCircleRectF, 0, -180, false); // false 不连接上个点到 弧线的起点， true 连接。
        mOnePath.offset(0, 110, mTwoPath);  //  mTwoPath 用于存储 mOnePath 移动后的状态
        canvas.drawPath(mOnePath, mPaint);
        mPaint.setColor(Color.CYAN - 100);
        canvas.drawPath(mTwoPath, mPaint);
        canvas.restoreToCount(four);

        int five = canvas.save();
        mBluePaint.setAlpha(125);
        mBluePaint.setStrokeWidth(2);
        canvas.drawLine(mTwoLevelBesselMovePoint.x, mTwoLevelBesselMovePoint.y, mTwoLevelBesselStartPoint.x, mTwoLevelBesselStartPoint.y, mBluePaint);
        canvas.drawLine(mTwoLevelBesselMovePoint.x, mTwoLevelBesselMovePoint.y, mTwoLevelBesselEndPoint.x, mTwoLevelBesselEndPoint.y, mBluePaint);
        mBluePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBluePaint.setAlpha(255);
        canvas.drawCircle(mTwoLevelBesselStartPoint.x, mTwoLevelBesselStartPoint.y, 10, mBluePaint);
        canvas.drawCircle(mTwoLevelBesselMovePoint.x, mTwoLevelBesselMovePoint.y, 10, mBluePaint);
        canvas.drawCircle(mTwoLevelBesselEndPoint.x, mTwoLevelBesselEndPoint.y, 10, mBluePaint);
        mBluePaint.setColor(Color.BLUE - 100);
        mBluePaint.setStyle(Paint.Style.STROKE);
        mOneBesselPath.reset();

        // 二阶贝塞尔曲线
        mOneBesselPath.moveTo(mTwoLevelBesselStartPoint.x, mTwoLevelBesselStartPoint.y);
        mOneBesselPath.quadTo(mTwoLevelBesselMovePoint.x, mTwoLevelBesselMovePoint.y, mTwoLevelBesselEndPoint.x, mTwoLevelBesselEndPoint.y);
        canvas.drawPath(mOneBesselPath, mBluePaint);
        canvas.restoreToCount(five);

    }
}