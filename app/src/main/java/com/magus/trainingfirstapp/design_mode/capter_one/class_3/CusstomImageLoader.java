package com.magus.trainingfirstapp.design_mode.capter_one.class_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.magus.trainingfirstapp.utils.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangshuai in the 10:38 of 2015.11.17 .
 */
public class CusstomImageLoader {

    private final String TAG = this.getClass().getSimpleName();

    /* 内存缓存 */
    private ImageCache imageCache;

    /* 磁盘缓存 */
    private DiskCache diskCache;

    /* 双缓存 */
    private DoubleCache doubleCache;

    /* 线程池 */
    private ExecutorService mExecutorService;

    /* 使用磁盘缓存flag*/
    private boolean isUseDiskCache = false;

    /* 使用双缓存flag*/
    private boolean isUseDoubleCache = false;

    public void setIsUseDiskCache(boolean isUseDiskCache) {
        this.isUseDiskCache = isUseDiskCache;
    }

    public void setIsUseDoubleCache(boolean isUseDoubleCache) {
        this.isUseDoubleCache = isUseDoubleCache;
    }

    public CusstomImageLoader() {
        imageCache = new ImageCache();
        diskCache = new DiskCache();
        doubleCache = new DoubleCache();
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void displayImg(final String imgUrl, final ImageView imageView) {

        /* 先在缓存里获取bitmap，如果没有再开线程到网上获取 */
        Bitmap bitmap = null;
        if (isUseDoubleCache) {
            bitmap = doubleCache.get(imgUrl);
        }else if (isUseDiskCache) {
            bitmap = diskCache.get(imgUrl);
        } else {
            bitmap = imageCache.get(imgUrl);
        }
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
                if (isUseDoubleCache) {
                    doubleCache.put(imgUrl, bitmap);
                }else if (isUseDiskCache) {
                    diskCache.put(imgUrl, bitmap);
                } else {
                    imageCache.put(imgUrl, bitmap);
                }
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

// 每次加新的缓存方法都要修改原来的代码，而且导致逻辑越来越复杂化，过多的if else出现，代码变得非常臃肿。可扩展性差。
// 当软件变化的时候，应该尽量以扩展的形式来实现变化。
// 优化：缓存功能有多个，而且功能在使用上都一样，保存和取出。因而，以缓存功能依赖注入的形式来优化代码。
