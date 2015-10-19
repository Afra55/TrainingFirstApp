package com.magus.trainingfirstapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by yangshuai on 2015/10/16 0016 16:09.
 */
public class BitmapUtils {
    private static Context context;

    /**
     * 获取Bitmap
     * @param imageId 图片资源id
     * @return
     */
    private static Bitmap decodeSampledBitmapFromResourse(int imageId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //不加载图片，只获取图片的宽高和type
        BitmapFactory.decodeResource(context.getResources(), imageId, options);

        //获取屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）

        options.inSampleSize = calculateInSampleSize(options, width ,height);
        options.inJustDecodeBounds = false;
        try {

            return BitmapFactory.decodeResource(context.getResources(), imageId, options);
        }catch (RuntimeException e){
            return null;
        }
    }

    /**
     * 计算inSampleSize，例如, 一个分辨率为2048x1536的图片，如果设置 inSampleSize 为4，那么会产出一个大约512x384大小的Bitmap。
     * @param options
     * @param reqWidth 想要压缩到的宽度
     * @param reqHeight  想要压缩到的高度
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        //图片资源文件的高
        final int height = options.outHeight;

        //图片资源文件的宽
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //计算最大inSampleSize值是2的幂，并保持高度和宽度大于所要求的高度和宽度。
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth
                    ) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 异步任务为 imageView加载大图, 适用 ListView 和 gridView
     * @param imageId
     * @param imageView
     */
    public static void loadBitmap(Context cont,int imageId, ImageView imageView){

        context = cont;
        if (cancelPotentialWork(imageId, imageView)){
            final BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(bitmapWorkerTask);
            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkerTask.execute(imageId);
        }
    }

    /**
     * 用来存储 BitmapWorkerTask 与之相应的 imageView 关联, 使用 {@link #getBitmapWorkerTask(ImageView)} 来获取任务
     */
    static  class AsyncDrawable extends BitmapDrawable {

        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
        public AsyncDrawable(BitmapWorkerTask bitmapWorkerTask) {
            bitmapWorkerTaskReference = new WeakReference(bitmapWorkerTask);
        }
        public BitmapWorkerTask getBitmapWorkerTask(){
            return bitmapWorkerTaskReference.get();
        }
    }

    /**
     * 判定当前与imageView绑定的任务
     * @param imageId
     * @param imageView
     * @return
     */
    public static boolean cancelPotentialWork(int imageId, ImageView imageView) {

        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null){
            final int bitmapId = bitmapWorkerTask.imageId;
            if (bitmapId == 0 || bitmapId != imageId){

                // 当前任务不是加载 imageId资源的异步任务，取消该任务
                bitmapWorkerTask.cancel(true);
            }else{

                // 当前任务正在加载 imageId资源，没有其他任务占用
                return false;
            }
        }

        //没有任务与 imageView 绑定
        return true;
    }

    /**
     * 获取与ImageView相关联的 BitmapWorkerTask
     * @param imageView
     * @return
     */
    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView){
        if (imageView != null){
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable){
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /**
     * 异步任务，为ImageView加载大图
     */
    static class  BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

        /**
         * 为ImageView使用WeakReference确保了AsyncTask所引用的资源可以被垃圾回收器回收。
         * 由于当任务结束时不能确保ImageView仍然存在，因此我们必须在onPostExecute()里面对引用进行检查。
         * 该ImageView在有些情况下可能已经不存在了，例如，在任务结束之前用户使用了回退操作，或者是配置发生了改变（如旋转屏幕等）。
         */
        private final WeakReference<ImageView> imageViewWeakReference;
        private int imageId;

        public BitmapWorkerTask(ImageView imageView){
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            imageId = params[0];
            Bitmap bitmap = null;
            try {
                bitmap = decodeSampledBitmapFromResourse(imageId);

            }catch (Exception e){
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()){
                bitmap = null;
            }
            if (imageViewWeakReference != null && bitmap != null){
                final ImageView imageView = imageViewWeakReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
