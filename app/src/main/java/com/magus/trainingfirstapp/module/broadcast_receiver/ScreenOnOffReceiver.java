package com.magus.trainingfirstapp.module.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.magus.trainingfirstapp.utils.SharedPreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangshuai in the 9:19 of 2016.01.25 .
 * 屏幕锁屏和唤醒监听
 */
public class ScreenOnOffReceiver extends BroadcastReceiver {

    public static final String DATE = "DATE";
    public static final String SCREEN_OFF_TIMES = "screen_off_times";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ScreenOnOffReceiver", intent.getAction());
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                Toast.makeText(context, "今天已锁屏" + SharedPreferenceUtil.getIntData(SCREEN_OFF_TIMES) + "次", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_SCREEN_OFF:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                if (SharedPreferenceUtil.getStringData(DATE).equals(simpleDateFormat.format(new Date()))) {
                    SharedPreferenceUtil.saveIntData(SCREEN_OFF_TIMES, SharedPreferenceUtil.getIntData(SCREEN_OFF_TIMES) + 1);
                } else {
                    SharedPreferenceUtil.saveStringData(DATE, simpleDateFormat.format(new Date()));
                    SharedPreferenceUtil.saveIntData(SCREEN_OFF_TIMES, 0);
                }
                break;
        }
    }
}
