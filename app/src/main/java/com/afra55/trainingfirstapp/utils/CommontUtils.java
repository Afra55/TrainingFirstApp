package com.afra55.trainingfirstapp.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import com.afra55.trainingfirstapp.utils.Log;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.afra55.trainingfirstapp.R;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangshuai
 * @version 创建时间：2015-7-8 下午4:34:23
 *          类说明 :
 */
public class CommontUtils {

    private static String TAG = CommontUtils.class.getSimpleName();

    /**
     * 等比压缩
     *
     * @param bitmap
     * @param scala
     * @return
     */
    public static final Bitmap extractThumbNail(Bitmap bitmap, float scala) {
        try {

            int newHeight = (int) ((bitmap.getHeight()) * scala);
            int newWidth = (int) ((bitmap.getWidth()) * scala);

            final Bitmap scale = Bitmap.createScaledBitmap(bitmap, newWidth,
                    newHeight, true);
            if (scale != null) {

                return scale;
            }

        } catch (final OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装链接上的键值对并保存到hashmap
     *
     * @param url
     * @return HashMap<key,value>
     */
    public static HashMap<String, String> handleUrlParams(String url) {
        HashMap<String, String> hm = new HashMap<String, String>();
        if (TextUtils.isEmpty(url)) {
            return hm;
        }
        if (url.contains("?") && url.indexOf("?") != url.length() - 1) {
            String params = url.substring(url.indexOf("?") + 1);
            if (params.contains("&")) {
                String[] paramArr = params.split("&");
                for (int i = 0; i < paramArr.length; i++) {
                    String str = paramArr[i];
                    if (str.contains("=")) {
                        try {
                            hm.put(str.substring(0, str.indexOf("=")),
                                    URLDecoder.decode(
                                            str.substring(str.indexOf("=") + 1,
                                                    str.length()), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (params.contains("=")) {
                    try {
                        hm.put(params.substring(0, params.indexOf("=")),
                                URLDecoder.decode(params.substring(
                                        params.indexOf("=") + 1,
                                        params.length()), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return hm;
    }

    public static final Bitmap drawText2Bitmap(Context context, Bitmap bitmap,
                                               final String text) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap imgTemp = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint paint = new Paint(); // 建立画笔
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmap, src, dst, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(20.0f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.WHITE);

        canvas.drawText(text, width / 2 - 5, height / 2 + 5, textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return imgTemp;

    }

    /**
     * 绘制带文字的图片
     */
    public static final Bitmap imageAddText(Context context, final String text) {
        TextView txt = new TextView(context);
        txt.setText(text);
        txt.setDrawingCacheEnabled(true);
        txt.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        txt.layout(0, 0, txt.getMeasuredWidth(), txt.getMeasuredHeight());
        Bitmap bitmap = txt.getDrawingCache();

        return bitmap;
    }

    /**
     * 获取版本号,默认1
     *
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1";
    }


    /**
     * 拼接URL。返回主机地址及参数
     *
     * @param map
     * @return
     */
    public static String getHost(String url, HashMap<String, String> map) {
        return url + handlerURLParams(map);
    }

    /**
     * hashmap生成url参数地址
     *
     * @param map
     * @return
     */
    public static String handlerURLParams(HashMap<String, String> map) {
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (TextUtils.isEmpty(value)) {
                value = "";
            }
            sb.append(entry.getKey() + "=" + value + "&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 获取已完整安装签名信息
     *
     * @return
     */
    public static String getSignInfo(Context context) {

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            return parseSignature(sign.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseSignature(byte[] signature) {

        try {
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            return MD5.getMessageDigest(signNumber + cert.getSubjectDN().toString());
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static DisplayMetrics displayMetrics = null;

    /**
     * dip 转 px
     *
     * @param dip
     * @return
     */
    public static final int dip2px(float dip) {
        if (null == displayMetrics)
            displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) (displayMetrics.density * dip);
    }

    private static Typeface tf;

    /**
     * 修改textView的字体
     *
     * @param textView
     */
    public static final void setTypeFace(Context context, TextView textView) {
        getTypeFace(context.getApplicationContext());
        textView.setTypeface(tf);
    }

    public static final Typeface getTypeFace(Context context) {
        if (null == tf) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(),
                        "fonts/2B721B_0_0.ttf");//写放到asstes文件夹下面的字体文件
            } catch (Exception e) {
                //				LogUtils.defaultLog(e);
            }
        }
        return tf;
    }

    /**
     * 在指定的文件中读取数据
     *
     * @param fileName 文件名称
     * @param objs     数组{key,defaultValue}
     */
    public static Object getSpFromFile(String fileName, Context context,
                                       Object[] objs) {
        try {
            SharedPreferences sp = getSP(fileName, context);
            if (objs[1] instanceof String) {
                return sp.getString(objs[0].toString(), objs[1].toString());
            } else if (objs[1] instanceof Integer) {
                return sp.getInt(objs[0].toString(),
                        Integer.parseInt(objs[1].toString()));
            } else if (objs[1] instanceof Long) {
                return sp.getLong(objs[0].toString(),
                        Long.parseLong((objs[1].toString())));
            } else if (objs[1] instanceof Float) {
                return sp.getFloat(objs[0].toString(),
                        Float.parseFloat((objs[1].toString())));
            } else if (objs[1] instanceof Boolean) {
                return sp.getBoolean(objs[0].toString(),
                        Boolean.parseBoolean((objs[1].toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得指定名称文件存储数据库
     *
     * @return
     */
    private static SharedPreferences getSP(String name, Context context) {
        return context.getSharedPreferences(name, Context.MODE_WORLD_READABLE);
    }

    /**
     * 在指定的文件中保存数据
     *
     * @param fileName 文件名称
     * @param objs     数组{key,value}
     */
    public static void saveSp2File(String fileName, Context context,
                                   Object[] objs) {
        try {
            SharedPreferences sp = getSP(fileName, context);
            Editor editor = sp.edit();
            if (objs[1] instanceof String) {
                editor.putString(objs[0].toString(), objs[1].toString());
            } else if (objs[1] instanceof Integer) {
                editor.putInt(objs[0].toString(),
                        Integer.parseInt(objs[1].toString()));
            } else if (objs[1] instanceof Long) {
                editor.putLong(objs[0].toString(),
                        Long.parseLong((objs[1].toString())));
            } else if (objs[1] instanceof Float) {
                editor.putFloat(objs[0].toString(),
                        Float.parseFloat((objs[1].toString())));
            } else if (objs[1] instanceof Boolean) {
                editor.putBoolean(objs[0].toString(),
                        Boolean.parseBoolean((objs[1].toString())));
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 将json字符串转换为map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> parseDataToMap(String json) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, Object> map = g.fromJson(json,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        return map;
    }

    /***
     * 复制内容到系统的剪贴板
     *
     * @param str
     */
    public static void copy(Context ctx, String str) {
        // 获取剪贴板服务
        ClipboardManager clipboard = (ClipboardManager) ctx
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(str.trim());
    }

    /***
     * 实现粘贴功能
     *
     * @param ctx
     * @return
     */
    public static String paste(Context ctx) {
        ClipboardManager clipboard = (ClipboardManager) ctx
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return clipboard.getText().toString().trim();
    }

    /***
     * 从字符串中截取网址
     *
     * @param str 字符串
     * @return
     */
    public static String cutOutUrl(String str) {
        String url = "";
        // Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Pattern p = Pattern.compile("^.*[com|cn|html]{1}$");
        Matcher m = p.matcher(str);
        if (m.find()) {
            url = m.group();
        }
        return url;
    }

    /***
     * 根据网址打开网页
     *
     * @param ctx
     * @param url 网址
     */
    public static void jumpToURL(Context ctx, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            ctx.startActivity(intent);
        } catch (Exception e) {
            if (!url.contains("http")) {
                Uri uri = Uri.parse("http://" + url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        }
    }

    /****
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 当前是否是html
     *
     * @param str
     * @return
     */
    public static final boolean isHtmlString(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        String regex = "<(\\S*?)[^>]*>.*?</\1>|<.*? />";
        return str.matches(regex);
    }

    /***
     * 将字节流转化成Bitmap
     *
     * @param code
     * @return
     */
    public static Bitmap getBitmap(String code) {
        if (null == code) {
            return null;
        }
        Bitmap bitmap = null;
        byte[] data = Base64.decode(code, Base64.DEFAULT);
        if (null != data) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            return null;
        }
        return bitmap;
    }

    /**
     * 获取当前API Level
     *
     * @return
     */
    public static int getCurrentApiLevel() {
        return VERSION.SDK_INT;
    }

    /**
     * 获取设备厂商
     *
     * @return
     */
    public static String getDeviceBrand() {
        String deviceBrand = Build.BRAND;
        return deviceBrand;
    }

    /**
     * 最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /***
     * 隐藏软键盘
     * {@link #dismissSoftKeyboard(Activity)}
     */
    @Deprecated
    public static void hideSoftInput(Context context) {
        if (((Activity) context).getCurrentFocus() != null
                && ((Activity) context).getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            ((Activity) context).getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭系统的软键盘
     *
     * @param activity
     */
    public static void dismissSoftKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void dismissSoftKeyboard(Context context, EditText edit) {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    /* 动态显示软键盘 */
    public static void showSoftInput(Context context, EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(edit, 0);
    }

    /* 动态显示或者是隐藏软键盘 */
    public static void toggleSoftInput(Context context, EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private static long lastClickTime;

    /**
     * 防暴力点击
     *
     * @return ture:多次点击，不处理
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;

        Log.e("点击时间差：", timeD + "");
        if (0 < timeD && timeD < 1000) {
            lastClickTime = time;
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    protected int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
//	/**
//	 * 改变状态栏颜色
//	 */
//	protected void changeStatusBarHeight(Context context){
//		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
//			//透明状态栏
//			((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			//透明导航栏
//			((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//			// 创建TextView
//			TextView textView = new TextView(context);
//			LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getStatusBarHeight(context));
//			textView.setBackgroundResource(Color.parseColor("#A52A2A"));
//			textView.setLayoutParams(lParams);
//			// 获得根视图并把TextView加进去。
//			ViewGroup view = (ViewGroup) ((Activity) context).getWindow().getDecorView();
//			view.addView(textView);
//		}
//	}

    /**
     * 保存手机分辨率内部类
     *
     * @author yangshuai
     */
    public static class Screen {
        public static int SCREEN_WIDTH = 0;
        public static int SCREEN_HEIGHT = 0;
    }

    /**
     * 获取手机分辨率
     */
    public static String getDisplayMetrix(Context context) {
        if (Screen.SCREEN_WIDTH == 0 || Screen.SCREEN_HEIGHT == 0) {
            if (context != null) {
                int width = 0;
                int height = 0;
                SharedPreferences DiaplayMetrixInfo = context.getSharedPreferences("display_metrix_info", 0);
                if (context instanceof Activity) {
                    WindowManager windowManager = ((Activity) context).getWindowManager();
                    Display display = windowManager.getDefaultDisplay();
                    DisplayMetrics dm = new DisplayMetrics();
                    display.getMetrics(dm);
                    width = dm.widthPixels;
                    height = dm.heightPixels;

                    Editor editor = DiaplayMetrixInfo.edit();
                    editor.putInt("width", width);
                    editor.putInt("height", height);
                    editor.commit();
                } else {
                    width = DiaplayMetrixInfo.getInt("width", 0);
                    height = DiaplayMetrixInfo.getInt("height", 0);
                }

                Screen.SCREEN_WIDTH = width;
                Screen.SCREEN_HEIGHT = height;
            }
        }
        return Screen.SCREEN_WIDTH + "×" + Screen.SCREEN_HEIGHT;
    }

    /**
     * 检测某程序是否安装
     */
    public static boolean isInstalledApp(Context context, String packageName) {
        Boolean flag = false;

        try {
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            for (PackageInfo pkg : pkgs) {
                // 当找到了名字和该包名相同的时候，返回
                if ((pkg.packageName).equals(packageName)) {
                    return flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 安装.apk文件
     *
     * @param context
     */
    public void install(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName) || context == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装.apk文件
     *
     * @param context
     */
    public void install(Context context, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @return 返回像素值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @return 返回dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设备唯一编码
     */
    private static String DEVICEKEY = "";

    /**
     * 根据mac地址+deviceid
     * 获取设备唯一编码
     *
     * @return
     * @see #DEVICEKEY 设备唯一编码
     */
    public static String getDeviceKey(Context context) {
        if ("".equals(DEVICEKEY)) {
            String macAddress = "";
            WifiManager wifiMgr = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info) {
                macAddress = info.getMacAddress();
            }
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            String deviceId = telephonyManager.getDeviceId();
            DEVICEKEY = macAddress + "," + deviceId;
        }
        return DEVICEKEY;
    }

    /**
     * 获取手机及SIM卡相关信息
     *
     * @param context
     * @return
     */
    public static Map<String, String> getPhoneInfo(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();
        String phoneMode = Build.MODEL;
        String phoneSDk = VERSION.RELEASE;
        map.put("imei", imei);
        map.put("imsi", imsi);
        map.put("phoneMode", phoneMode + "##" + phoneSDk);
        map.put("model", phoneMode);
        map.put("sdk", phoneSDk);
        return map;
    }

    /**
     * 获取本地网络ip
     *
     * @return
     */
    public static String getLocalIpAddress() {
        String IP = null;
        StringBuilder IPStringBuilder = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            !inetAddress.isLinkLocalAddress() &&
                            inetAddress.isSiteLocalAddress()) {
                        IPStringBuilder.append(inetAddress.getHostAddress().toString());
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        IP = IPStringBuilder.toString();
        return IP;

    }

    /**
     * dip转像素
     *
     * @param context
     * @param dip
     * @return
     */
    public static int DipToPixels(Context context, int dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;

        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);

        return valuePixels;

    }

    /**
     * 像素转dip
     *
     * @param context
     * @param Pixels
     * @return
     */
    public static float PixelsToDip(Context context, int Pixels) {
        final float SCALE = context.getResources().getDisplayMetrics().density;

        float dips = Pixels / SCALE;

        return dips;
    }

    // 判断字符串是否为有效的url
    public boolean isURL(String url) {
        // 转换为小写
        url = url.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return url.matches(regex);
    }

    //================================v2===============================

    /**
     * 在指定app的缓存目录下，创建一个独特的子目录，优先使用外部存储，外部存储没有挂载则使用内部存储
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Check if external storage is built-in or removable.
     * 检查外部存储是否内置的或可移动的。
     *
     * @return 如果外部存储是可拆卸的 (like an SD card) True, 否则false.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            return Environment.isExternalStorageRemovable();
        return true;
    }

    /**
     * Get the external app cache directory.
     * 外部应用程序缓存目录。
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
            return context.getExternalCacheDir();

        // Froyo之前需要构造外部缓存dir
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 检测当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean IsNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//		可以通过这两个判断是wifi还是移动网络
//		networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
//		networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 检测当前网络是否是Wifi
     *
     * @param context
     * @return
     */
    public static boolean IsWifiNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 检测当前网络是否是移动网络
     *
     * @param context
     * @return
     */
    public static boolean IsWifiMobileWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

    /*SDcard的动态路径*/
    static String sdcardPath = Environment.getExternalStorageDirectory() + "";

    public static boolean hasSDcard() {
        boolean b = false;
        try {
            b = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
        }
        return b;
    }

    /**
     * 获得缓存的绝对路径
     *
     * @param context
     * @return
     */
    public static String cachePath(Context context) {
        String path;
        if (hasSDcard()) {
            path = sdcardPath + File.separator + context.getResources().getString(R.string.app_name);
            File file = new File(path);
            if (!file.isDirectory()) {
                file.delete();
            }
            if (file == null || !file.exists()) {
                file.mkdirs();
            }
            path += File.separator;

        } else {
            path = context.getCacheDir().getAbsolutePath();
        }
        return path;
    }

    /**
     * Returns a cache size equal to approximately three screens worth of images.
     */
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

    /**
     * Load a contact photo thumbnail and return it as a Bitmap,
     * resizing the image to the provided image dimensions as needed.
     *
     * @param photoData photo ID Prior to Honeycomb, the contact's _ID value.
     *                  For Honeycomb and later, the value of PHOTO_THUMBNAIL_URI_INDEX.
     * @return A thumbnail Bitmap, sized to the provided width and height.
     * Returns null if the thumbnail is not found.
     */
    public static Bitmap loadContactPhotoThumbnail(Activity activity, String photoData) {
        // Creates an asset file descriptor for the thumbnail file.
        AssetFileDescriptor afd = null;
        // try-catch block for file not found
        try {
            // Creates a holder for the URI.
            Uri thumbUri;
            // If Android 3.0 or later
            if (Build.VERSION.SDK_INT
                    >=
                    Build.VERSION_CODES.HONEYCOMB) {
                // Sets the URI from the incoming PHOTO_THUMBNAIL_URI_INDEX
                thumbUri = Uri.parse(photoData);
            } else {
                // Prior to Android 3.0, constructs a photo Uri using _ID
            /*
             * Creates a contact URI from the Contacts content URI
             * incoming photoData (_ID)
             */
                final Uri contactUri = Uri.withAppendedPath(
                        ContactsContract.Contacts.CONTENT_URI, photoData);
            /*
             * Creates a photo URI by appending the content URI of
             * Contacts.Photo.
             */
                thumbUri =
                        Uri.withAppendedPath(
                                contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }

    /*
     * Retrieves an AssetFileDescriptor object for the thumbnail
     * URI
     * using ContentResolver.openAssetFileDescriptor
     */
            afd = activity.getContentResolver().
                    openAssetFileDescriptor(thumbUri, "r");
    /*
     * Gets a file descriptor from the asset file descriptor.
     * This object can be used across processes.
     */
            FileDescriptor fileDescriptor = afd.getFileDescriptor();
            // Decode the photo file and return the result as a Bitmap
            // If the file descriptor is valid
            if (fileDescriptor != null) {
                // Decodes the bitmap
                return BitmapFactory.decodeFileDescriptor(
                        fileDescriptor, null, null);
            }
            // If the file isn't found
        } catch (FileNotFoundException e) {
        /*
         * Handle file not found errors
         */
            return null;
        }
        // In all cases, close the asset file descriptor
        finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * App 签名信息
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getSign(Context context, String packageName) {
        Signature[] signs = getRawSignature(context, packageName);
        if ((signs == null) || (signs.length == 0)) {
            return null;
        } else {
            Signature sign = signs[0];
            return sign.hashCode() + "";
        }
    }


    public static Signature[] getRawSignature(Context context,
                                              String packageName) {
        if ((packageName == null) || (packageName.length() == 0)) {
            return null;
        }
        PackageManager pkgMgr = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pkgMgr.getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        if (info == null) {
            return null;
        }
        return info.signatures;
    }

    /* 拨打电话 */
    public static void call(Context context, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }

    /* 跳转至拨号界面 */
    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /* 发送短信 */
    public static void sendSms(Context context, String phoneNumber,
                               String content) {
        Uri uri = Uri.parse("smsto:"
                + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /* 唤醒屏幕并解锁,需要权限
     * <uses-permission android:name="android.permission.WAKE_LOCK" />
       <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
     */
    public static void wakeUpAndUnlock(Context context){
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

    /* 判断当前App处于前台还是后台状态，需要权限
       <uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static boolean isApplicationBackground(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* 判断当前手机是否处于锁屏(睡眠)状态 */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

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

    /* 安装APK */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /* 获取当前设备宽，单位px */
    @SuppressWarnings("deprecation")
    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    /* 获取当前设备高，单位px */
    @SuppressWarnings("deprecation")
    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    /* 判断当前设备是否为手机 */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /* 获取当前设备的IMEI，需要与上面的isPhone()一起使用 */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getDeviceIMEI(Context context) {
        String deviceId;
        if (isPhone(context)) {
            TelephonyManager telephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephony.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /* 获取当前设备的MAC地址 */
    public static String getMacAddress(Context context) {
        String macAddress;
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        if (null == macAddress) {
            return "";
        }
        macAddress = macAddress.replace(":", "");
        return macAddress;
    }

    /* 获取当前程序的版本号 */
    public static String getAppVersion(Context context) {
        String version = "0";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /* 收集设备信息，用于信息统计分析 */
    public static Properties collectDeviceInfo(Context context) {
        Properties mDeviceCrashInfo = new Properties();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put("VERSION_NAME",
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put("VERSION_CODE", pi.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }
        }

        return mDeviceCrashInfo;
    }

    public static String collectDeviceInfoStr(Context context) {
        Properties prop = collectDeviceInfo(context);
        Set deviceInfos = prop.keySet();
        StringBuilder deviceInfoStr = new StringBuilder("{\n");
        for (Iterator iter = deviceInfos.iterator(); iter.hasNext();) {
            Object item = iter.next();
            deviceInfoStr.append("\t\t\t" + item + ":" + prop.get(item)
                    + ", \n");
        }
        deviceInfoStr.append("}");
        return deviceInfoStr.toString();
    }

    /* 是否有SD卡 */
    public static boolean haveSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /* 主动回到Home，后台运行 */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    /* 获取状态栏高度, 要在onWindowFocusChanged中调用，在onCreate中获取高度为0 */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /* 获取状态栏高度+标题栏(ActionBar)高度, 如果没有ActionBar，那么获取的高度只有状态栏的高度 */
    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
                .getTop();
    }

    /* 获取MCC+MNC代码 (SIM卡运营商国家代码和运营商网络代码)
    * 仅当用户已在网络注册时有效, CDMA 可能会无效(中国移动：46000 46002, 中国联通：46001,中国电信：46003)
    */
    public static String getNetworkOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperator();
    }

    /* 返回移动网络运营商的名字
    * 例：中国联通、中国移动、中国电信, 仅当用户已在网络注册时有效, CDMA 可能会无效
    */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    /* 返回移动终端类型
     * PHONE_TYPE_NONE :0 手机制式未知
       PHONE_TYPE_GSM :1 手机制式为GSM，移动和联通
       PHONE_TYPE_CDMA :2 手机制式为CDMA，电信
       PHONE_TYPE_SIP:3
     */
    public static int getPhoneType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneType();
    }

    /* 手机号码正则 */
    public static final String REG_PHONE_CHINA = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /* 邮箱正则 */
    public static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /* 检测是否运行在模拟器中, 通过检测 ro.kernel.qemu(通常在模拟器中是1， 在正常手机中没有该属性) */
    public static boolean isRunningInEmualtor() {
        boolean qemukernel = false;
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("getprop ro.kernel.qemu");
            os = new DataOutputStream(process.getOutputStream()); // 获取执行getprop 后的输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            os.writeBytes("exit\n"); // 执行退出
            os.flush(); // 刷新输出流
            process.waitFor();
            qemukernel = (Integer.valueOf(in.readLine()) == 1); // ro.kernel.qemu 属性是否为1
            com.afra55.trainingfirstapp.utils.Log.d("CommontUtils", "检测到模拟器：" + qemukernel);

        } catch (Exception e) {
            qemukernel = false; // 出现异常则判断在正常手机上
            com.afra55.trainingfirstapp.utils.Log.d("CommontUtils", e.toString());
        } finally {
            try {

                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return qemukernel;
    }
}
