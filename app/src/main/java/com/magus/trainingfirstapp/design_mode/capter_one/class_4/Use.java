package com.magus.trainingfirstapp.design_mode.capter_one.class_4;

import android.graphics.Bitmap;

/**
 * Created by yangshuai in the 17:10 of 2015.11.18 .
 */
public class Use {

    public Use() {
        CusstomImageLoader imageLoader = new CusstomImageLoader();

        /*使用内存缓存*/
        MemoryCache memoryCache = new MemoryCache();
        imageLoader.setImageCache(memoryCache);
/*
        使用磁盘缓存*/
        DiskCache diskCache = new DiskCache();
        imageLoader.setImageCache(diskCache);

        /*使用双缓存*/
        DoubleCache doubleCache = new DoubleCache();
        imageLoader.setImageCache(doubleCache);

        /*自定义缓存的实现*/
        imageLoader.setImageCache(new ImageCacheInterface() {
            @Override
            public Bitmap get(String url) {
                return null;
            }

            @Override
            public void put(String url, Bitmap bitmap) {

            }
        });
    }
}
