/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afra55.trainingfirstapp.module.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

/**
 * 提供一个GLSurfaceView对象的绘画指令. 这个类必须覆盖OpenGL ES生命周期方法:
 * <ul>
 *   <li>{@link GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";

    /**
     * 三角形
     */
    private Triangle mTriangle;

    /**
     * 正方形
     */
    private Square   mSquare;

    // mMVPMatrix是“模型-视图-投影矩阵”的缩写
    private final float[] mMVPMatrix = new float[16];

    /**
     * 投影（Projection）：这个变换会基于显示它们的GLSurfaceView的长和宽，来调整绘图对象的坐标。
     * 如果没有该计算，那么用OpenGL ES绘制的对象会由于其长宽比例和View窗口比例的不一致而发生形变。
     * 一个投影变换一般仅当OpenGL View的比例在渲染器的onSurfaceChanged()方法中建立或发生变化时才被计算。
     */
    private final float[] mProjectionMatrix = new float[16];

    /**
     * 相机视角（Camera View）：这个变换会基于一个虚拟相机位置改变绘图对象的坐标。
     * 注意到OpenGL ES并没有定义一个实际的相机对象，取而代之的，它提供了一些辅助方法，通过对绘图对象的变换来模拟相机视角。
     * 一个相机视角变换可能仅在建立你的GLSurfaceView时计算一次，也可能根据用户的行为或者你的应用的功能进行动态调整。
     */
    private final float[] mViewMatrix = new float[16];

    //旋转矩阵
    private final float[] mRotationMatrix = new float[16];

    /**
     * 旋转角度
     */
    private float mAngle;

    /**
     * 调用一次，用来配置View的OpenGL ES环境。
     * @param unused
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // 设置背景帧的颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare   = new Square();
    }

    /**
     * 每次重新绘制View时被调用。
     * @param unused
     */
    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        // 绘制背景颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //使用Matrix.setLookAtM()方法来计算相机视角变换，然后与之前计算的投影矩阵结合起来，结合后的变换矩阵传递给绘制图像

        // 设置相机的位置(视图矩阵)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // 计算投影与视图的转换
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        mSquare.draw(mMVPMatrix);

        // 创建一个旋转的三角形

        // 使用下面的代码来生成不断旋转
        // 当使用TouchEvents注释掉下面的代码.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

        // 结合旋转矩阵投影和相机视图
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
    }

    /**
     * 如果View的几何形态发生变化时会被调用，例如当设备的屏幕方向发生改变时。
     * @param unused
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // 基于几何形状的变化,调整视窗
        // 比如屏幕旋转
        GLES20.glViewport(0, 0, width, height);

        //首先接收GLSurfaceView的高和宽，然后利用它并使用Matrix.frustumM()方法来填充一个投影变换矩阵（Projection Transformation Matrix）
        float ratio = (float) width / height;

        // 这个投影矩阵应用于onDrawFrame()方法中的对象坐标
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    /**
     * 着色器包含了OpenGL Shading Language（GLSL）代码，它必须先被编译然后才能在OpenGL环境中使用。
     * 要编译这些代码，需要在渲染器类中创建一个辅助方法.
     *
     * <p><strong>Note:</strong> 当开发着色器,使用checkGlError()方法来调试着色编码错误。</p>
     *
     * @param type - 顶点或片段着色器类型.
     * @param shaderCode - 字符串包含着色器代码.
     * @return - 返回一个着色器的id.
     */
    public static int loadShader(int type, String shaderCode){

        // 创建一个顶点着色器类型 (GLES20.GL_VERTEX_SHADER)
        // 或一个片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // 将源代码添加到着色器并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
    * 为调试OpenGL调用的方法. Provide the name of the call just after making it:
    *
    * <pre>
    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * 如果操作不成功,检查抛出一个错误
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * 返回的三角形形状的旋转角度(mTriangle)。
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    /**
     * 设置三角形形状的旋转角度(mTriangle)。
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }

}