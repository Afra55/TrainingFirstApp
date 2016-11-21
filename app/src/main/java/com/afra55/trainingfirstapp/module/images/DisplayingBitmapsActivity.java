package com.afra55.trainingfirstapp.module.images;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.module.anim_demo.AnimDemoMainActivity;
import com.afra55.trainingfirstapp.module.opengl.OpenGLES20Activity;

import java.io.File;
import java.lang.ref.WeakReference;

public class DisplayingBitmapsActivity extends BaseActivity {

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.displaying_openGL_btn:
                startActivity(new Intent(DisplayingBitmapsActivity.this, OpenGLES20Activity.class));
                break;
            case R.id.displaying_anim_btn:
                startActivity(new Intent(DisplayingBitmapsActivity.this, AnimDemoMainActivity.class));
                break;
        }
        super.onClick(v);
    }

    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_displaying_bitmaps);

        /** 内存缓存 **/
        //获取最大可用的vm内存，超过这个值就会抛出内存溢出异常。
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //使用1/8 maxMemory来作为缓存空间
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {

                //最低api 12，以kb为单位
                return value.getByteCount() / 1024;
            }

        };



        imageView = (ImageView) findViewById(R.id.display_imageView);


//        Bitmap bitmap = decodeSampledBitmapFromResourse(R.drawable.zhizhuxia);
//        imageView.setImageBitmap(bitmap);
        loadBitmap(R.drawable.zhizhuxia, imageView);
    }

    /**
     * 获取Bitmap
     * @param imageId 图片资源id
     * @return
     */
    private Bitmap decodeSampledBitmapFromResourse(int imageId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //不加载图片，只获取图片的宽高和type
        BitmapFactory.decodeResource(getResources(), imageId, options);

        //获取屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）

        options.inSampleSize = calculateInSampleSize(options, width ,height);
        options.inJustDecodeBounds = false;
        try {

            return BitmapFactory.decodeResource(getResources(), imageId, options);
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
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

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
    public void loadBitmap(int imageId, ImageView imageView){

        //首先在缓存中查找
        final String imgKey = String.valueOf(imageId);
        final Bitmap bitmap = getBitmapFromLruMemCache(imgKey);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else if (cancelPotentialWork(imageId, imageView)){
            final BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(bitmapWorkerTask);
            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkerTask.execute(imageId);
        }
    }

    /**
     * 用来存储 BitmapWorkerTask 与之相应的 imageView 关联, 使用 {@link #getBitmapWorkerTask(ImageView)} 来获取任务
     */
    static class AsyncDrawable extends BitmapDrawable{

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
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>{

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

            //将处理后的bitmap添加到缓存中
            addBitmapToMemoryCache(String.valueOf(imageId), bitmap);
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

    //内存缓存
    private LruCache<String, Bitmap> mMemoryCache;

    /**
     * 添加 bitmap到缓存中
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap){

        if (getBitmapFromLruMemCache(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }


    /**
     * 从缓存中获取 Bitmap
     * @param key
     * @return
     */
    public Bitmap getBitmapFromLruMemCache(String key){
        return mMemoryCache.get(key);
    }



    /**
     * 获取版本号
     * @return
     */
    public String getVersionName(){
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1";
    }

    /**
     * 在指定app的缓存目录下，创建一个独特的子目录，优先使用外部存储，外部存储没有挂载则使用内部存储
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName){
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Check if external storage is built-in or removable.
     * 检查外部存储是否内置的或可移动的。
     * @return 如果外部存储是可拆卸的 (like an SD card) True, 否则false.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            return Environment.isExternalStorageRemovable();
        return true;
    }

    /**
     * Get the external app cache directory.
     * 外部应用程序缓存目录。
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
            return context.getExternalCacheDir();

        // Froyo之前需要构造外部缓存dir
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    @Override
    protected void onStop() {
        BitmapWorkerTask b= getBitmapWorkerTask(imageView);
        if (b != null) b.cancel(true);
        mMemoryCache.snapshot().clear();
        super.onStop();
    }
}
