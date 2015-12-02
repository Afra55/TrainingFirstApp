package com.magus.trainingfirstapp.module.image_handle.fragment;

/**
 * Created by yangshuai in the 13:58 of 2015.12.02 .
 */
public class ColorMatrixUtils {

    /* 灰度 */
    public static float[] grayColorMatrix() {
        float[] color = new float[]{
                0.33f, 0.59f, 0.11f, 0, 0,
                0.33f, 0.59f, 0.11f, 0, 0,
                0.33f, 0.59f, 0.11f, 0, 0,
                0,     0,     0,     1, 0
        };
        return color;
    }

    /* 反转 */
    public static float[] reversalMatrix() {
        float[] color = new float[]{
               -1,  0,  0,  1,  1,
                0, -1,  0,  1,  1,
                0,  0, -1,  1,  1,
                0,  0,  0,  1,  0,
        };
        return color;
    }

    /* 怀旧 */
    public static float[] reminiscenceMatrix() {
        float[] color = new float[]{
                0.393f,  0.769f,  0.189f,  0,  0,
                0.349f,  0.686f,  0.168f,  0,  0,
                0.272f,  0.534f,  0.131f,  0,  0,
                0,       0,       0,       1,  0,
        };
        return color;
    }

    /* 去色 */
    public static float[] discolorationMatrix() {
        float[] color = new float[]{
               1.5f, 1.5f, 1.5f, 0, -1,
               1.5f, 1.5f, 1.5f, 0, -1,
               1.5f, 1.5f, 1.5f, 0, -1,
                0,    0,    0,   1,  0
        };
        return color;
    }

    /* 高饱和度 */
    public static float[] highSatMatrix() {
        float[] color = new float[]{
                 1.438f, -0.122f, -0.016f, 0, -0.03f,
                -0.062f,  1.378f, -0.016f, 0,  0.05f,
                -0.062f, -0.122f,  1.483f, 0, -0.02f,
                 0,       0,       0,      1,  0
        };
        return color;
    }


}
