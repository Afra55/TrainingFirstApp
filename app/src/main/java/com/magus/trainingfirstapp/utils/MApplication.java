package com.magus.trainingfirstapp.utils;

import android.app.Application;

import com.magus.trainingfirstapp.exception.CrashHandler;

/**
 * Created by yangshuai on 2015/10/13 0013 11:01.
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
