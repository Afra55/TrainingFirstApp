package com.afra55.trainingfirstapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
}
