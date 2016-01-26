package com.magus.trainingfirstapp.utils.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.magus.trainingfirstapp.utils.SharedPreferenceUtil;
import com.magus.trainingfirstapp.utils.broadcast_receiver.ScreenOnOffReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {

    private BroadcastReceiver screenOnOffReceiver; // 监听屏幕唤醒和休眠

    public MyService() {
    }

    /**
     * 当服务第一次创建时调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        screenOnOffReceiver = new ScreenOnOffReceiver();
          /* 注册屏幕唤醒和休眠监听 */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        registerReceiver(screenOnOffReceiver, intentFilter);
        Log.d("MyService", "onCreate");
    }

    /**
     * 创建服务时调用，也可以通过 {@link android.content.Context#startService}调用
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("MyService", "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 销毁服务
     */
    @Override
    public void onDestroy() {
        Log.d("MyService", "onDestroy");
        unregisterReceiver(screenOnOffReceiver);
        startService(new Intent(getApplicationContext(), MyService.class));
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService", "onBind");
        // TODO: Return the communication channel to the service.
        throw null;
    }

}
