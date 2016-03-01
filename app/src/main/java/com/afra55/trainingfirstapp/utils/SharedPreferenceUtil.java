package com.afra55.trainingfirstapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by yangshuai on 2015/9/30 0030.
 */
public class SharedPreferenceUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void init(Activity context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        }

        if (editor == null) {
            editor = sharedPreferences.edit();
        }
    }

    public static void saveIntData(String key, int data){
        editor.putInt(key, data);
        editor.commit();
    }
    
    public static int getIntData(String key){
        return sharedPreferences.getInt(key, 0);
    }
    
    public static int getIntData(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }
    
    public static void saveStringData(String key, String data){
        editor.putString(key, data);
        editor.commit();
    }
    
    public static String getStringData(String key){
        return sharedPreferences.getString(key, "");
    }
    
    public static String getStringData(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void saveBooleanData(String key, boolean data){
        editor.putBoolean(key, data);
        editor.commit();
    }

    public static boolean getBooleanData(String key){
        return sharedPreferences.getBoolean(key, false);
    }
    
    public static boolean getBooleanData(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key, defaultValue);
    }
    
    public static void saveFloatData(String key, float data){
        editor.putFloat(key, data);
        editor.commit();
    }
    
    public static float getFloatData(String key){
        return sharedPreferences.getFloat(key, 0);
    }
    
    public static float getFloatData(String key, float defaultValue){
        return sharedPreferences.getFloat(key, defaultValue);
    }
    
    public static Map<String, ?> getAllData(){
       return sharedPreferences.getAll();
    }

    public static String useFileStreamReadSharedPreferencesData(Context context) {
        String content = "";
        String path = android.os.Environment.getDataDirectory().getAbsolutePath()
                + "/data/" + context.getPackageName() + "/shared_prefs/abc.xml";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            content = bufferedReader.readLine();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public void saveToSDcard(Context context) {
        try {
            /* 获取 ContextWrapper 对象中的 mBase 变量 */
            Field field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);

            /* 获取mBase变量的值 */
            Object object = field.get(context);

            /* 获取 ContextImpl.mPreferencesDir 变量 */
            field = object.getClass().getDeclaredField("mPreferencesDir");
            field.setAccessible(true);

            /* 创建自定义路径 */
            File file = new File("/sdcard");

            /* 修改 mPreferencesDir 变量的值 */
            field.set(object, file);

            /* 执行该语句，在 /sdcard 目录中创建一个 save_data.xml 文件 */
            SharedPreferences sharedPreferences = context.getSharedPreferences("save_data", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("afra55", "AAA啊哈");
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Field field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
            Object object = field.get(context);
            object.getClass().getName(); // 获取到名字
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* 存储一个序列化（实现 Serializable接口）的对象 */
    public static void saveSerializableObject(String key, Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        saveStringData(key, Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
    }

    /* 获取序列化对象 */
    public static Object getSerializableObject(String key) throws IOException {
        byte[] base64Bytes = Base64.decode(getStringData(key).getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
