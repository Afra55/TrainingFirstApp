package com.magus.trainingfirstapp.design_mode.capter_one.class_2;

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

    /* 缓存 */
    private ImageCache imageCache;

    /* 线程池 */
    private ExecutorService mExecutorService;

    public CusstomImageLoader() {
        imageCache = new ImageCache();
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void displayImg(final String imgUrl, final ImageView imageView) {

        /* 先在缓存里获取bitmap，如果没有再开线程到网上获取 */
        Bitmap bitmap = imageCache.get(imgUrl);
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

// CusstomImageLoader只负责图片加载， ImageCache负责图片缓存， 当缓存功能改变时就不用修改ImageLaoder类
// 缺点：缺乏可扩展性， 内存缓存有限且重启应用后内存缓存文件会丢失。
// 单一职责原则总结：两个完全不一样的功能不应该放在一个类中，一个类中应该是一组相关性很高的函数·数据的封装。
// 对类的拆分，迈出了代码优化的第一步。