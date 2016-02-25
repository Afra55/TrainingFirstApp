package com.afra55.trainingfirstapp.utils.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.afra55.trainingfirstapp.utils.service.MyService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangshuai in the 9:19 of 2016.01.25 .
 * 屏幕锁屏和唤醒监听
 */
public class ScreenOnOffReceiver extends BroadcastReceiver {

    public static final String DATE = "SCREEN_OFF_DATE";
    public static final String SCREEN_OFF_TIMES = "screen_off_times";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ScreenOnOffReceiver", intent.getAction());
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
            case Intent.ACTION_USER_PRESENT:
                Toast.makeText(context, "今天已锁屏" + sharedPreferences.getInt(SCREEN_OFF_TIMES, 0) + "次", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_SCREEN_OFF:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                if (sharedPreferences.getString(DATE, "").equals(simpleDateFormat.format(new Date()))) {
                    editor.putInt(SCREEN_OFF_TIMES, sharedPreferences.getInt(SCREEN_OFF_TIMES, 0) + 1);
                } else {
                    editor.putString(DATE, simpleDateFormat.format(new Date()));
                    editor.putInt(SCREEN_OFF_TIMES, 1);
                }
                editor.commit();
                break;
        }
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, new Intent(myContext, MyService.class));
    }
}
