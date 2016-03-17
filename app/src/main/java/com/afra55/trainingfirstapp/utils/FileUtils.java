package com.afra55.trainingfirstapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.afra55.trainingfirstapp.R;
import com.nostra13.universalimageloader.core.assist.FlushedInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * Created by yangshuai in the 10:47 of 2016.03.17 .
 * 文件操作类。专门提供文件的创建、读写（文件缓存系统及指定文件目录下获取文件的相关信息）、
 * 拷贝、删除、指定路径下，文件夹存在与否判断
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean hasSDcard() {
        boolean b = false;
        try {
            b = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
        }
        return b;
    }

    /*SDcard的动态路径*/
    static String sdcardPath = Environment.getExternalStorageDirectory() + "";

    /**
     * 获得缓存的绝对路径
     *
     * @param context
     * @return
     */
    public static String cachePath(Context context) {
        String path;
        if (hasSDcard()) {
            path = sdcardPath + File.separator + context.getResources().getString(R.string.app_name);
            File file = new File(path);
            if (!file.isDirectory()) {
                file.delete();
            }
            if (file == null || !file.exists()) {
                file.mkdirs();
            }
            path += File.separator;

        } else {
            path = context.getCacheDir().getAbsolutePath();
        }
        return path;
    }

    /**
     * 获取指定路径下以特定字符开头的文件名
     *
     * @param fileDir
     * @param specificName
     * @return
     */
    public static String getSpecificFileName(File fileDir, String specificName) {
        String fileName = "";
        if (null == fileDir || specificName.isEmpty()) {
            return null;
        }
        File[] files = fileDir.listFiles();
        if (null == files) {
            return null;
        }
        File latestFile = null;
        // 根据文件最后修改时间获取最新的文件
        for (File file : files) {
            fileName = file.getName();
            if (file.isFile() && fileName.startsWith(specificName)) {
                if (null == latestFile)
                    latestFile = file;
                if (file.lastModified() > latestFile.lastModified()) {
                    latestFile = file;
                }
            }
        }
        if (null != latestFile) {
            return latestFile.getName();
        } else {
            return null;
        }
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static File createFileIfNeed(File file) throws Exception {
        if (!file.exists()) {
            File parentDictionary = file.getParentFile();
            if (parentDictionary != null && !parentDictionary.exists()) {
                parentDictionary.mkdirs();
            }
            if (file.createNewFile()) {
                return file;
            } else {
                return null;
            }
        } else {
            return file;
        }
    }

    /**
     * 级连创建文件，通过一个分解字符串的形式循环创建目录
     *
     * @param path
     */
    public static File createFile(String path) {
        StringTokenizer st = new StringTokenizer(path, File.separator);
        String rootPath = st.nextToken() + File.separator;
        String tempPath = rootPath;
        File boxFile = null;
        while (st.hasMoreTokens()) {
            rootPath = st.nextToken() + File.separator;
            tempPath += rootPath;
            boxFile = new File(tempPath);
            if (!boxFile.exists()) {
                boxFile.mkdirs();
            }
        }
        return boxFile;
    }

    /**
     * 保存数据到当前context下，files文件夹目录下,使用FileOutputStream
     * @param context
     * @param fileName
     * @param cacheValue
     */
    public static void setData2File(Context context, String fileName,
                                    String cacheValue) {
        File file = new File(context.getFilesDir(), fileName);
        try {
            FileUtils.createFileIfNeed(file);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            byte[] bytes = cacheValue.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 从当前context下，files文件夹里面读取指定文件的内容, FileInputStream
     * @param context
     * @param fileName
     */
    public static String getDataFromFile(Context context, String fileName) {
        String cacheValue = "";
        try {
            File file = new File(context.getFilesDir(), fileName);
            if (!file.isFile()) {
                return cacheValue;
            }

            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            cacheValue = new String(buffer,Charset.forName("UTF-8"));
//                    EncodingUtils.getString(buffer, "UTF-8");
            fis.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return cacheValue;
    }

    /**
     * 保存数据到当前context下，files文件夹目录下
     * @param context
     * @param fileName
     * @param cacheValue
     */
    public static void writeData2File(Context context, String fileName,
                                      String cacheValue) {
        File file = new File(context.getFilesDir(), fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file = createFileIfNeed(file);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        write(file,cacheValue);
    }
    private static void write(File file,String cacheValue){
        RandomAccessFile fos = null;
        try {
            fos = new RandomAccessFile(file,"rw");
            byte[] bytes = cacheValue.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 从当前context下，files文件夹里面读取指定文件的内容
     * @param context
     * @param fileName
     */
    public static String readDataFromFile(Context context, String fileName) {
        String cacheValue = "";
        File file = new File(context.getFilesDir(), fileName);
        if (!file.isFile()) {
            return cacheValue;
        }
        cacheValue = read(file);
        return cacheValue;
    }

    private static String read(File file){
        String cacheValue = "";
        RandomAccessFile fis = null;
        try {
            fis = new RandomAccessFile(file,"r");
            int length = (int)file.length();
            byte[] buffer = new byte[length];
            fis.seek(0);
            fis.readFully(buffer);
            cacheValue = new String(buffer, Charset.forName("UTF-8"));
//                    EncodingUtils.getString(buffer, "UTF-8");
            fis.close();
        } catch (Exception e) {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    Log.e(TAG, e1.toString());
                }
            }
            Log.e(TAG, e.toString());
        }
        return cacheValue;
    }

    /**
     * 保存数据到指定文件夹目录下
     * @param filePath 文件的绝对路径
     * @param fileName
     * @param cacheValue
     */
    public static void writeFile(String filePath, String fileName,
                                 String cacheValue) {
        File file = new File(filePath, fileName);
        if (file.exists()) {
            file.delete();
        }
        createFile(filePath);
        write(file,cacheValue);
    }

    /**
     * 读取指定文件的内容
     * @param filePath 文件的绝对路径
     * @param fileName
     */
    public static String readFile(String filePath, String fileName) {
        String cacheValue = "";
        File file = new File(filePath, fileName);
        if (!file.isFile()) {
            return cacheValue;
        }
        cacheValue = read(file);
        return cacheValue;
    }

    /**
     * 通过管道，拷贝文件
     * @param sf
     * @param df
     * @return true:拷贝成功，false：拷贝失败
     */
    public static boolean copyFileForTransfer(File sf, File df) {
        int length = 2 * 1024 * 1024;
        FileInputStream in;
        try {
            in = new FileInputStream(sf);
            FileOutputStream out = new FileOutputStream(df);
            FileChannel inC = in.getChannel();
            FileChannel outC = out.getChannel();
            while (true) {
                if (inC.position() == inC.size()) {
                    inC.close();
                    outC.close();
                    return true;
                }
                if ((inC.size() - inC.position()) < 20971520) {
                    length = (int) (inC.size() - inC.position());
                } else {
                    length = 2 * 1024 * 1024;
                }
                inC.transferTo(inC.position(), length, outC);
                inC.position(inC.position() + length);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            return false;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    /**
     * 拷贝指定文件夹下面的所有子文件夹及文件
     * @param oldPath
     * @return true:拷贝成功。false：拷贝失败
     */
    public static boolean copyFload(String oldPath, String newPath) {
        if (oldPath.isEmpty() || newPath.isEmpty()) {
            return false;
        }
        File oldf = new File(oldPath);
        if (oldf.isDirectory()) {
            File[] oldfs = oldf.listFiles();
            for (int i = 0; i < oldfs.length; i++) {
                if (oldfs[i].isDirectory()) {
                    // 查看新文件夹里面是否有该文件夹
                    String newfPath = newPath
                            + (oldfs[i].toString()).substring(oldPath.length(),
                            oldfs[i].toString().length());
                    File newf = new File(newfPath);
                    if (!newf.exists()) {
                        createFile(newfPath);
                    }
                    File[] file = oldfs[i].listFiles();
                    String newfsPath = newPath;
                    for (int j = 0; j < file.length; j++) {
                        // 查看新文件夹里面是否有该文件夹
                        newfsPath = newfPath
                                + (file[j].toString()).substring(oldfs[i]
                                .toString().length(), file[j]
                                .toString().length());
                        if (file[j].isDirectory()) {
                            File newfs = new File(newfsPath);
                            if (!newfs.exists()) {
                                createFile(newfsPath);
                            }
                        } else {
                            boolean flag = copyFileForTransfer(file[j],
                                    new File(newfsPath));
                            if (!flag) {
                                return false;
                            }
                        }
                        copyFload(file[j].toString(), newfsPath);
                    }
                } else {
                    String newFile = newPath
                            + (oldfs[i].toString()).substring(oldf.toString()
                            .length(), oldfs[i].toString().length());
                    boolean flag = copyFileForTransfer(oldfs[i], new File(
                            newFile));
                    if (!flag) {
                        return false;
                    }
                }
            }
        } else {
            String newFile = newPath
                    + (oldf.toString()).substring(oldPath.length(), oldf
                    .toString().length());
            boolean flag = copyFileForTransfer(oldf, new File(newFile));
            if (!flag) {
                return false;
            }
        }
        return true;
    }


    /**
     * 删除指定文件或文件夹（文件夹及所有的子文件夹及文件）
     * @param filePath :文件夹的绝对路径
     */
    public static void deletefile(String filePath) {
        File f = new File(filePath);
        if (f.isDirectory()) {
            File[] file = f.listFiles();
            for (int i = 0; i < file.length; i++) {
                deletefile(file[i].toString());
                file[i].delete();
            }
        }
        f.delete();
    }

    /**
     * 判断当前指定路径下，是否有该文件夹
     * @param fileAbsolutePath
     *            ：文件夹父路径（如：/mnt/sdcard/）
     * @param  folderName：文件夹名字
     * @return true:拷贝成功，false：拷贝失败
     */
    public static boolean isHaveFolder(String fileAbsolutePath,
                                       String folderName) {
        File f = new File(fileAbsolutePath);
        File[] fs = f.listFiles();
        if ( null == fs ) return false ;
        for (int i = 0; i < fs.length; i++) {
            String filePath = fs[i].getAbsolutePath();
            String[] spStr = filePath.split("/");
            if (filePath.contains(folderName)
                    && spStr[spStr.length - 1].equals(folderName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     *            File实例
     * @return long 单位为M
     * @throws Exception
     */
    public static double getFolderSize(File file) throws Exception {
        double size = 0;
        File[] fileList = file.listFiles();
        if (null == fileList) {
            return 0;
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        size = size / 1048576;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(size));
    }

    /**
     * 保存图片文件到指定的路径
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String saveBitmap(Bitmap bitmap, String path) {
        File fileBitmap = new File(path);
        if (fileBitmap.exists())
            fileBitmap.delete();
        try {
            fileBitmap.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            Log.i("saveBitmap" ,"store bitmap of path:" + path);
            stream = new FileOutputStream(path);
            if (!bitmap.compress(format, quality, stream))
                path = null;
            try {
                stream.flush();
                stream.close();
            } catch (IOException e) {
                Log.i("saveBitmap", e.toString());
            }
        } catch (FileNotFoundException e) {
            path = null;
        }
        return path;
    }

    /**
     * 从绝对路径中按大小获取图片
     *
     * @param path
     *            图片路径
     * @param reqWidth
     *            期望高度，单位px
     * @param reqheight
     *            ，单位px
     * @return
     */
    public synchronized static Bitmap readBitmapFromFile(String path, int reqWidth, int reqheight) {
        try {
            BitmapFactory.Options optTemp = new BitmapFactory.Options();
            optTemp.inJustDecodeBounds = true;
            FileInputStream fileInpuStream = new FileInputStream(path);
            BitmapFactory.decodeStream(fileInpuStream, null, optTemp);

			/* 计算缩放比例 */
            int scale = calculateInSampleSizePow(optTemp, reqWidth, reqheight);

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = scale;
            opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(path), null, opt);
            try {
                fileInpuStream.close();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                Log.w(TAG, FileUtils.class.getName() + ":fileInpuStream not released!");
            }
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.w(TAG, FileUtils.class.getName() + ":File(" + path + ")not found!");
            return null;
        }
    }

    /**
     *
     * @param op
     * @param reqWidth
     *            期望图片宽度，单位px
     * @param reqheight
     *            期望图片高度，单位px
     * @return
     */
    public static int calculateInSampleSizePow(BitmapFactory.Options op, int reqWidth, int reqheight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;
        int inSampleSize = 1;
        if (originalWidth > reqWidth || originalHeight > reqheight) {
            int halfWidth = originalWidth / 2;
            int halfHeight = originalHeight / 2;
            while ((halfWidth / inSampleSize > reqWidth) && (halfHeight / inSampleSize > reqheight)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 计算最接近期望宽高的opt
     * @param op
     * @param reqWidth
     *            期望图片宽度，单位px
     * @param reqheight
     *            期望图片高度，单位px
     * @return
     */
    public static int calculateInSampleSizePowFabs(BitmapFactory.Options op, int reqWidth, int reqheight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;
        int inSampleSize = 1;
        if (originalWidth > reqWidth || originalHeight > reqheight) {
            int halfWidth = originalWidth / 2;
            int halfHeight = originalHeight / 2;
            while ((halfWidth / inSampleSize > reqWidth) && (halfHeight / inSampleSize > reqheight)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 获取 File 路径
     * @param context
     * @return
     */
    public static String getAppFilePath(Context context) {
        return context.getApplicationContext().getFilesDir().getAbsolutePath();
    }

    /**
     * 删除单个文件
     *
     * @param filePath
     *            被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            if (file.delete()) {
                Log.d(TAG, "delete file of path:" + filePath);
                return true;
            }
        }
        return false;
    }

    /**
     * 保存InputStream流到硬盘缓存
     */
    public static synchronized void saveBitmapToFile(InputStream is, File file) {
        BufferedOutputStream out = null;
        FlushedInputStream in = null;
        FileOutputStream outputStream = null;
        try {
            in = new FlushedInputStream(new BufferedInputStream(is, 8 * 1024));
            outputStream = new FileOutputStream(file);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        } catch (Exception e) {
            if (file != null && file.exists()) {
                file.delete();
            }
            file = null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (null != outputStream) {
                    outputStream.close();
                }
                if (in != null) {
                    in.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (final IOException e) {
            }
        }
    }
}
