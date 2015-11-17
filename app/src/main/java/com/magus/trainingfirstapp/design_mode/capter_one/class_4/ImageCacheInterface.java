package com.magus.trainingfirstapp.design_mode.capter_one.class_4;

import android.graphics.Bitmap;

/**
 * Created by yangshuai in the 15:35 of 2015.11.17 .
 */
public interface ImageCacheInterface {

    public Bitmap get(String url);

    public void put(String url, Bitmap bitmap);
}
