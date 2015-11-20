package com.magus.trainingfirstapp.design_mode.capter_one.class_3;

import android.graphics.Bitmap;

/**
 * Created by yangshuai in the 14:40 of 2015.11.17 .
 */
public class DoubleCache {

    private DiskCache diskCache;
    private ImageCache imageCache;

    public DoubleCache() {
        diskCache = new DiskCache();
        imageCache = new ImageCache();
    }

    public void put(String url, Bitmap bitmap) {

        imageCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        Bitmap bitmap = imageCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }
        return bitmap;
    }
}
