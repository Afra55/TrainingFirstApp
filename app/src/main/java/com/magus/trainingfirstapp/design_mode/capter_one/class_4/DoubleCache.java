package com.magus.trainingfirstapp.design_mode.capter_one.class_4;

import android.graphics.Bitmap;

/**
 * Created by yangshuai in the 14:40 of 2015.11.17 .
 */
public class DoubleCache implements ImageCacheInterface{

    private DiskCache diskCache;
    private MemoryCache imageCache;

    public DoubleCache() {
        diskCache = new DiskCache();
        imageCache = new MemoryCache();
    }

    @Override
    public void put(String url, Bitmap bitmap) {

        imageCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = imageCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }
        return bitmap;
    }
}
