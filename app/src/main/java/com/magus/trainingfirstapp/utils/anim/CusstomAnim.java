package com.magus.trainingfirstapp.utils.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * Created by yangshuai in the 15:40 of 2015.12.04 .
 */
public class CusstomAnim extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
    private float mRotateY = 0;
    private Camera mCamera = new Camera();

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(10000);
        setFillAfter(true);
        setInterpolator(new BounceInterpolator());
        mCenterHeight = height / 2;
        mCenterWidth = width / 2;
    }

    // 暴露接口-设置旋转角度
    public void setRotateY(float rotateY) {
        mRotateY = rotateY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        mCamera.save();
        mCamera.rotateY(mRotateY * interpolatedTime);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        /* 通过pre方法设置矩阵作用前的偏移量来改变旋转中心 */
        matrix.preTranslate(mCenterWidth, mCenterHeight);
        matrix.postTranslate(-mCenterWidth, -mCenterHeight);
    }
}
