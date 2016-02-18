package com.magus.trainingfirstapp.utils.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by yangshuai in the 9:13 of 2016.02.18 .
 * 围绕 y 轴旋转，并且同时沿着 z 轴平移从而实现一种类似于3D的效果。
 */
public class Rotate3dAnimation extends Animation {

    private final float mFromDegrees;
    private final boolean mReverse;
    private final float mDepthZ;
    private float mCenterY;
    private float mCenterX;
    private final float mToDegrees;
    private Camera mCamera;

    /**
     * 当动画开始时，在z轴的深度移动会开始，同时移动要及时的返回到原位置。
     * @param fromDegrees   开始角度
     * @param toDegrees 结束角度
     * @param depthZ    z 轴深度
     * @param reverse   true表示正常动画流程，false表示相反的动画流程
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees
            , float depthZ, boolean reverse) {
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;
        this.mDepthZ = depthZ;
        this.mReverse = reverse;
    }

    /* 初始化 */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(1000);
        mCamera = new Camera();
        mCenterX = width / 2;
        mCenterY = height / 2;
    }

    /* 矩阵变换 */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + (mToDegrees - fromDegrees) * interpolatedTime;

        float centerX = mCenterX;
        float centerY = mCenterY;
        Camera camera = mCamera;

        Matrix matrix = t.getMatrix();

        camera.save();

        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }

        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        /* 通过pre方法设置矩阵作用前的偏移量来改变旋转中心 */
        matrix.preTranslate(-centerX, -centerY); //
        matrix.postTranslate(centerX, centerY);
    }
}
