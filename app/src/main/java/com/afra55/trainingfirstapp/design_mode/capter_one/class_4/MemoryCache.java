package com.afra55.trainingfirstapp.design_mode.capter_one.class_4;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by yangshuai in the 10:43 of 2015.11.17 .
 */
public class MemoryCache implements ImageCacheInterface{

    private LruCache<String, Bitmap> mCache;

    public MemoryCache(){
        final int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemorySize / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getWidth() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(String key, Bitmap bitmap){
        mCache.put(key, bitmap);
    }

    @Override
    public Bitmap get(String key) {
        return mCache.get(key);
    }
}
