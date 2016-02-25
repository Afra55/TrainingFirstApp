package com.afra55.trainingfirstapp.design_mode.capter_one.class_4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.utils.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangshuai in the 10:38 of 2015.11.17 .
 */
public class CusstomImageLoader {

    private final String TAG = this.getClass().getSimpleName();

    /* 线程池 */
    private ExecutorService mExecutorService;

    /* 缓存，默认使用内存缓存 */
    private ImageCacheInterface imageCache = new MemoryCache();

    /* 注入缓存功能 */
    public void setImageCache(ImageCacheInterface imageCache) {
        this.imageCache = imageCache;
    }



    public CusstomImageLoader() {
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    public void displayImg(final String imgUrl, final ImageView imageView) {

        /* 先在缓存里获取bitmap，如果没有再开线程到网上获取 */
        Bitmap bitmap = null;
        imageCache.get(imgUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        imageView.setTag(imgUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmap(imgUrl);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().toString().equals(imgUrl)) {
                    imageView.setImageBitmap(bitmap);
                }

                    /* 缓存bitmap */
                imageCache.put(imgUrl, bitmap);
            }
        });
    }

    /* 获取图片 */
    private Bitmap getBitmap(String imgUrl) {
        Bitmap bitmap = null;
        try {
            URL url = null;
            url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (Exception e) {
            Log.d(TAG, "请检查网络连接或图片地址的有效性");
            e.printStackTrace();
        }
        return bitmap;
    }
}

        // 使用 setImageCache 方法来实现缓存功能的注入
 /*     CusstomImageLoader imageLoader = new CusstomImageLoader();

        *//* 使用内存缓存 *//*
        MemoryCache memoryCache = new MemoryCache();
        imageLoader.setImageCache(memoryCache);

        *//* 使用磁盘缓存 *//*
        DiskCache diskCache = new DiskCache();
        imageLoader.setImageCache(diskCache);

        *//* 使用双缓存 *//*
        DoubleCache doubleCache = new DoubleCache();
        imageLoader.setImageCache(doubleCache);

        *//* 自定义缓存的实现 *//*
        imageLoader.setImageCache(new ImageCacheInterface() {
        @Override
        public Bitmap get(String url) {
                return null;
                }

        @Override
        public void put(String url, Bitmap bitmap) {

                }
                });
*/

// 开闭原则总结： 对修改封闭， 对扩展开放，一个产品必定需要升级维护，修改原来的代码可能会引发其他一些列的问题，因此要尽量避免影响原有的代码，使用父类接口·抽象方法是最有用的手段。
// 同时，依赖注入的方法，也体现了依赖倒置原则： 依赖抽象，不依赖细节。也很好的反映了里氏替换原则：代码重用，可扩展性，父类可替换为子类使用即多态。