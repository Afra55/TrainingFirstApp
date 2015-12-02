package com.magus.trainingfirstapp.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.magus.trainingfirstapp.R;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by yangshuai on 2015/10/13 0013 11:01.
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

        /* imageLoader
        * http://blog.csdn.net/yang786654260/article/details/44300833 */
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)  // 如果经常 OutOfMemoryError 禁用缓存
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)  // 位图RGB_565比ARGB_8888消耗更少的内存
                .imageScaleType(ImageScaleType.EXACTLY)  // 图像缩小尺寸刚好到目标大小（按比例缩小的宽度或高度将等于目标大小，依赖缩放类型） 注意：如果原始图片尺寸小于目标尺寸将不会缩放
                .displayer(new FadeInBitmapDisplayer(300))  // “淡入”动画显示图像
                .showImageOnLoading(R.drawable.beauty)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .threadPoolSize(3)  // 线程池大小建议1 - 5
                .diskCacheExtraOptions(screenWidth, screenHeight, null)
                .diskCacheFileNameGenerator(new FileNameGenerator() {   // new Md5FileNameGenerator()
                    @Override
                    public String generate(String imageUri) {
                        return getString(R.string.app_name)+ "_" + imageUri;
                    }
                })
                .build();
        ImageLoader.getInstance().init(configuration);


        // 注意：避免列表(网格,…)滚动滞后
        //        boolean pauseOnScroll = false; // or true
        //        boolean pauseOnFling = true; // or false
        //        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
        //        listView.setOnScrollListener(listener);

    }
}
