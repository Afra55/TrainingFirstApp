package com.magus.trainingfirstapp.module.sms_broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * 监听短信
 */
public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        if (bundle != null) {
            switch (intent.getAction()) {
                case "android.provider.Telephony.SMS_RECEIVED": // 短信
                    MonitoringSms(context, bundle);
                    break;
            }
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
