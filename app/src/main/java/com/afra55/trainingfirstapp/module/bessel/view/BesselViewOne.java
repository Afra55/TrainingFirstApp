package com.afra55.trainingfirstapp.module.bessel.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Victor Yang on 2016/10/19.
 * 一阶贝塞尔曲线
 */

public class BesselViewOne extends View {

    private Paint mPaint;
    private Paint mRedPaint;
    private Paint mBluePaint;

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
        mRedPaint = new Paint();
        mBluePaint = new Paint();

        // 画笔颜色 - 黑色
        mPaint.setColor(Color.BLACK);
        mRedPaint.setColor(Color.RED);
        mBluePaint.setColor(Color.BLUE);

        // 填充模式 - 描边
        mPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setStyle(Paint.Style.STROKE);
        mBluePaint.setStyle(Paint.Style.STROKE);

        // 边框宽度 - 10
        mPaint.setStrokeWidth(10);
        mRedPaint.setStrokeWidth(1);
        mBluePaint.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int one = canvas.save();
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mRedPaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mRedPaint);
        canvas.restoreToCount(one);

        canvas.translate(getWidth() / 2, getHeight() / 2);

        Path path = new Path();

        path.lineTo(200, 0);                      // lineTo
        path.lineTo(200,200);

        canvas.drawPath(path, mPaint);              // 绘制Path
    }
}
