package com.magus.trainingfirstapp.module.image_handle.handle_method;

/**
 * Created by yangshuai in the 18:14 of 2015.11.30 .
 */
public class OldImage implements ImageHandleMethod {
    @Override
    public int r(int r, int g, int b) {
        return (int) (0.393 * r + 0.769 * g + 0.189 * b);
    }

    @Override
    public int g(int r, int g, int b) {
        return (int) (0.349 * r + 0.686 * g + 0.168 * b);
    }

    @Override
    public int b(int r, int g, int b) {
        return (int) (0.272 * r + 0.534 * g + 0.131 * b);
    }
}
