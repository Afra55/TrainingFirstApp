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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

/**
 * 一个二维三角形在 OpenGL ES 2.0 里作为一个对象使用。
 * A two-dimensional triangle for use as a drawn object in OpenGL ES 2.0.
 */
public class Triangle {

    // 用来渲染形状顶点的OpenGL ES代码。
    private final String vertexShaderCode =
            // 这个矩阵成员变量使用顶点着色器提供了一个hook来操作坐标对象
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            // 矩阵 must be included as a modifier of gl_Position
            // Note that the uMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}";

    //使用颜色或纹理渲染形状表面的OpenGL ES代码。
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    private final FloatBuffer vertexBuffer;

    //一个OpenGL ES对象，包含了你希望用来绘制一个或更多图形所要用到的着色器。
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;

    /**
     * 用来访问和设置视图变换
     */
    private int mMVPMatrixHandle;

    // 每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            // 以逆时针顺序:
            0.0f,  0.622008459f, 0.0f,   // top
           -0.5f, -0.311004243f, 0.0f,   // bottom left
            0.5f, -0.311004243f, 0.0f    // bottom right
    };
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    /**
     * 设置颜色,红色,绿色,蓝色和α(不透明)值
     */
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

    /**
     * 设置绘图对象数据 for 在OpenGL ES上使用上下文.
     */
    public Triangle() {
        // 初始化顶点字节缓冲区形状坐标
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);

        // 使用设备硬件的本机字节顺序
        bb.order(ByteOrder.nativeOrder());

        // 创建一个浮点ByteBuffer的缓冲区
        vertexBuffer = bb.asFloatBuffer();

        // 添加FloatBuffer坐标
        vertexBuffer.put(triangleCoords);

        // 设置缓冲读取第一个坐标
        vertexBuffer.position(0);

        // 为了绘制图形，必须编译着色器代码，将它们添加至一个OpenGL ES Program对象中，然后执行链接。在绘制对象的构造函数里做这些事情。
        // 编译OpenGL ES着色器及链接操作对于CPU周期和处理时间而言，消耗是巨大的，所以应该避免重复执行这些事情。

        // 准备着色器和OpenGL程序
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // 创建空的OpenGL程序
        GLES20.glAttachShader(mProgram, vertexShader);   // 添加顶点着色器到程序
        GLES20.glAttachShader(mProgram, fragmentShader); // 添加片段着色器到程序
        GLES20.glLinkProgram(mProgram);                  // 创建OpenGL程序可执行文件

    }

    /**
     * 封装了OpenGL ES指令来画这个形状,
     * 为形状的顶点着色器和形状着色器设置了位置和颜色值，然后执行绘制函数,
     * 仅需要在渲染器的onDrawFrame()方法中调用draw()方法就可以画出要画的对象
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw this shape.
     */
    public void draw(float[] mvpMatrix) {
        // 添加程序OpenGL环境
        GLES20.glUseProgram(mProgram);

        // 得到顶点着色器的vPosition成员的处理
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 使三角形的顶点处理有效
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 准备三角形坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 得到片段着色器的 vColor 成员的处理
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // 得到形状的变换矩阵的处理
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // 应用投影与视图的转换
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // 画一个三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
