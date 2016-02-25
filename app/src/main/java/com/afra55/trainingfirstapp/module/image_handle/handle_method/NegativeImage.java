package com.afra55.trainingfirstapp.module.image_handle.handle_method;

/**
 * Created by yangshuai in the 18:10 of 2015.11.30 .
 */
public class NegativeImage implements ImageHandleMethod {
    @Override
    public int r(int r, int g, int b) {
        return 255 - r;
    }

    @Override
    public int g(int r, int g, int b) {
        return 255 - g;
    }

    @Override
    public int b(int r, int g, int b) {
        return 255 - b;
    }
}
