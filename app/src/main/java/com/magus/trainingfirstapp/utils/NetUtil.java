package com.magus.trainingfirstapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 网络工具，获取网络状态
 */
public class NetUtil {


    /**
     * 获取网络，如果网络不是wifi或者不是mobile返回false。
     *
     * @return boolean
     */
    public static boolean hasNet(Context ctx) {
        NetworkInfo info = getNetworkInfo(ctx);
        // 判断是否是wi-access 设备的wifi
        if (info != null) { // 0代表wifi 1代表 mobile
            int type = info.getType();
            return type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE;
        } else {
            // 无网络，清楚缓存数据
            Toast.makeText(ctx, "没有网络", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 判断是否为WIFI网络
     *
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info == null) {
            return false;
        }
        return info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否为移动网络
     *
     * @return
     */
    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info == null) {
            return false;
        }
        return info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public static void addNetListener() {

    }

    /**
     * 获得网络信息
     *
     * @return info
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info;
    }


}
