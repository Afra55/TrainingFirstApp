package com.afra55.trainingfirstapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 网络工具，获取网络状态
 */
public class NetUtil {


    /* 判断当前是否有网络连接 */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /* 判断当前是否是WIFI连接状态 */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

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


    public class Constants {
        /**
         * Unknown network class
         */
        public static final int NETWORK_CLASS_UNKNOWN = 0;

        /**
         * wifi net work
         */
        public static final int NETWORK_WIFI = 1;

        /**
         * "2G" networks
         */
        public static final int NETWORK_CLASS_2_G = 2;

        /**
         * "3G" networks
         */
        public static final int NETWORK_CLASS_3_G = 3;

        /**
         * "4G" networks
         */
        public static final int NETWORK_CLASS_4_G = 4;

    }

    /* 判断手机连接的网络类型(2G,3G,4G)
    * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
    */
    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constants.NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constants.NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constants.NETWORK_CLASS_4_G;

            default:
                return Constants.NETWORK_CLASS_UNKNOWN;
        }
    }

    /* 判断当前手机的网络类型(WIFI还是2,3,4G) */
    public static int getNetWorkStatus(Context context) {
        int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }
}
