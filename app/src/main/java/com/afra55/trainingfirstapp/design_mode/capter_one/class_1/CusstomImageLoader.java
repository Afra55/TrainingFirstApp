package com.afra55.trainingfirstapp.design_mode.capter_one.class_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.utils.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangshuai in the 9:41 of 2015.11.17 .
 */
public class CusstomImageLoader {

    private final String Tag = this.getClass().getSimpleName();

    /* 图片缓存 */
    private LruCache<String, Bitmap> mCacheImage;

    /* 线程池,线程数量是CPU的数量 */
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public CusstomImageLoader() {

        /* 获取最大可使用内存 */
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        /* 最大可用内存的四分之一作为缓存 */
        int cacheSize = maxMemory / 4;

        mCacheImage = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight() * value.getWidth() / 1024;
            }
        };
    }

    /**
     * 展示图片
     * @param imgUrl
     * @param imageView
     */
    public void displayImg(final String imgUrl, final ImageView imageView) {
        imageView.setTag(imgUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {

                    /* 获取图片 */
                    Bitmap bitmap = getBitmap(imgUrl);
                    if (bitmap == null) {
                        return;
                    }
                    if (imageView.getTag().toString().equals(imgUrl)) {
                        imageView.setImageBitmap(bitmap);
                    }

                    /* 缓存bitmap */
                    mCacheImage.put(imgUrl, bitmap);
                } catch (Exception e) {
                    Log.d(Tag, "请检查网络连接或者图片地址的有效性");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取bitmap
     * @param imgUrl
     * @return
     */
    private Bitmap getBitmap(String imgUrl) throws Exception{
        URL url = new URL(imgUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        connection.disconnect();
        return bitmap;
    }
}

// 问题：耦合严重，没有扩展和灵活性，所有的功能都在一个类中，会随着功能的增加导致代码越加复杂，图片加载系统更加脆弱。
// 优化: 拆分功能模块，满足单一职责原则