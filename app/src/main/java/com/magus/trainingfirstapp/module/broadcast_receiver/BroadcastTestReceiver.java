package com.magus.trainingfirstapp.module.broadcast_receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.magus.trainingfirstapp.base.TrainingFirstActivity;
import com.magus.trainingfirstapp.base.field.G;
import com.magus.trainingfirstapp.utils.SharedPreferenceUtil;

import java.lang.reflect.Method;

/**
 * 监听短信
 */
public class BroadcastTestReceiver extends BroadcastReceiver {


    public static String MONITORING_CALL_NUM = "10086";

    public BroadcastTestReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        if (bundle != null) {
            switch (intent.getAction()) {
                case "android.provider.Telephony.SMS_RECEIVED": // 短信
                    if (SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_SMS))
                        MonitoringSms(context, bundle);
                    break;
                case "android.intent.action.PHONE_STATE": // 电话
                    if (SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_CALL))
                        MonitorCallNumber(context, intent);
                    break;
                case "android.intent.action.BOOT_COMPLETED": // 手机启动了
                    openMyApp(context);
                    break;
            }
        }
    }

    private void openMyApp(Context context) {
        Intent openAppIntent = new Intent(context, TrainingFirstActivity.class);
        openAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(openAppIntent);
        Toast.makeText(context, "欢迎你开机", Toast.LENGTH_SHORT).show();
    }

    /* 监听来电电话 */
    private void MonitorCallNumber(Context context, Intent intent) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        switch (telephonyManager.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING: // 响铃
                String incommingNumber = intent.getStringExtra("incoming_number"); // 获得来电电话号码
                if (incommingNumber.contains(BroadcastTestReceiver.MONITORING_CALL_NUM)) { // 判断来电电话
                    try {
                        Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;

                        /* 通过反射获取getITelephony方法对应的method对象 */
                        Method telephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony", null);

                        /* 允许访问 getITelephony 方法 */
                        telephonyMethod.setAccessible(true);

                        /* 调用 getITelephony 方法获取 ITelephony 对象 */
                        Object object = telephonyMethod.invoke(telephonyManager, null);

                        /* 获取与 endCall 方法对应的 Method 对象 */
                        Method endCallMethod = object.getClass().getMethod("endCall", null);

                        /* 允许访问 endCall 方法*/
                        endCallMethod.setAccessible(true);

                        /* 调用 endCall 方法挂断电话*/
                        endCallMethod.invoke(object, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: // 接听电话
                break;
            case TelephonyManager.CALL_STATE_IDLE: // 挂断电话
                Toast.makeText(context, "已经挂断黑名单用户", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /* 短信监听并提示 */
    private void MonitoringSms(Context context, Bundle bundle) {
        Object[] objArray = (Object[]) bundle.get("pdus"); // 获得收到的短信数据
        SmsMessage[] message = new SmsMessage[objArray.length]; // 定义封装短信内容的SmsMessage对象数组
        for (int i = 0; i < objArray.length; i++) { // 循环处理收到的所有短信
            message[i] = SmsMessage.createFromPdu((byte[]) objArray[i]); // 将每条短信转换成SmsMessage对象
            String s = "手机号：" + message[i].getOriginatingAddress() + "\n"
                    + "短信内容：" + message[i].getDisplayMessageBody();
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }
}
