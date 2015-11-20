package com.magus.trainingfirstapp.design_mode.capter_one.class_5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.magus.trainingfirstapp.design_mode.capter_one.class_4.ImageCacheInterface;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yangshuai in the 14:33 of 2015.11.17 .
 */
public class DiskCache implements ImageCacheInterface{

    private static String cacheDir = "sdcard/cache/";

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(cacheDir + url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + url);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeQuietly(fileOutputStream);
        }
    }
}

// java 里有个 Closeable 接口，标识一个可关闭的对象，只有一个close方法。
// FileOutputStream 就实现了这个方法，还有其他的100多个类型的对象，就可以键一个统一的方法来关闭这些可关闭的对象，保证代码的重用性。
// CloseUtils 就是实现了的 closeQuietly 方法就是依赖 Closeable 接口，而不是具体实现（依赖倒置原则），
// 建立在最小依赖原则的基础上，我只知道这个对象可以关闭，其他的一概不关心。
// 接口隔离原则：依赖最小化

// 总结 ： SOLID
// 关键字：抽象，单一职责，最小化