package com.afra55.trainingfirstapp.module.bessel.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Victor Yang on 2016/10/19.
 * 一阶贝塞尔曲线
 */

public class BesselViewOne extends View {

    private Paint mPaint;
    private Paint mGridPaint;
    private Paint mBluePaint;
    private Path mOnePath;
    private Path mTwoPath;
    private RectF mCricleRectF;

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

        // 画笔颜色 - 黑色
        mPaint.setColor(Color.BLACK);
        mGridPaint.setColor(Color.RED);
        mBluePaint.setColor(Color.BLUE);

        // 填充模式 - 描边
        mPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mBluePaint.setStyle(Paint.Style.STROKE);

        // 边框宽度 - 10
        mPaint.setStrokeWidth(10);
        mGridPaint.setStrokeWidth(1);
        mBluePaint.setStrokeWidth(1);

        mOnePath = new Path();
        mTwoPath = new Path();
        mCricleRectF = new RectF(-100, -100, 100, 100);
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
        canvas.translate(getWidth() /4 * 3, getHeight() / 4);
        mPaint.setColor(Color.CYAN);
        mOnePath.reset();

        // Path.Direction.CCW 的绘制顺序是 从左上角 左下角，右下，右上，即逆时针绘制。
        // Path.Direction.CW 的绘制顺序是 从左上角  ，右上 右下，左下角， 即顺时针绘制。
        mOnePath.addRect(-100, -100, 100, 100, Path.Direction.CW);
        mOnePath.setLastPoint(300,-300);
        canvas.drawPath(mOnePath, mPaint);
        canvas.restoreToCount(three);

        int four = canvas.save();
        canvas.translate(getWidth() / 4, getHeight() / 2);
        mOnePath.reset();
        mOnePath.lineTo(0, -50);
        mOnePath.arcTo(mCricleRectF, 0, -180, false); // false 不连接上个点到 弧线的起点， true 连接。
        mOnePath.offset(0, 110, mTwoPath);  //  mTwoPath 用于存储 mOnePath 移动后的状态
        canvas.drawPath(mOnePath, mPaint);
        mPaint.setColor(Color.CYAN - 100);
        canvas.drawPath(mTwoPath, mPaint);
        canvas.restoreToCount(four);

    }
}
