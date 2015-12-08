package com.magus.trainingfirstapp.utils;

import android.os.Build;

public class SystemInfoTools {

    public static String makeInfoString(String[] description, String[] prop) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prop.length; i++) {
            sb.append(description[i]).append(" : ").append(prop[i]).append("\r\n");
        }
        return sb.toString();
    }

    public static String getBuildInfo() {
        String board = Build.BOARD; // 主板
        String brand = Build.BRAND; // Android 系统定制商
        String supported_abis = null; // CPU 指令集
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            supported_abis = Build.SUPPORTED_ABIS[0];
        }
        String device = Build.DEVICE; // 设备参数
        String display = Build.DISPLAY; // 显示屏幕参数
        String fingerprint = Build.FINGERPRINT; // 唯一编号
        String serial = Build.SERIAL; // 硬件序列号
        String id = Build.ID; // 修订版本列表
        String manufacturer = Build.MANUFACTURER; // 硬件制造商
        String model = Build.MODEL; // 版本
        String hardware = Build.HARDWARE; // 硬件名
        String product = Build.PRODUCT; // 手机产品名
        String tags = Build.TAGS; // 描述 Build 的标签
        String type = Build.TYPE; // Builer 类型
        String codename = Build.VERSION.CODENAME; // 当前开发代号
        String incremental = Build.VERSION.INCREMENTAL; // 源码控制版本
        String release = Build.VERSION.RELEASE; // 版本字符串
        String sdk_int = "" + Build.VERSION.SDK_INT; // 版本号
        String host = Build.HOST; // Host 值
        String user = Build.USER; // User 名
        String time = "" + Build.TIME; // 编译时间
        String[] prop = {
                board,
                brand,
                supported_abis,
                device,
                display,
                fingerprint,
                serial,
                id,
                manufacturer,
                model,
                hardware,
                product,
                tags,
                type,
                codename,
                incremental,
                release,
                sdk_int,
                host,
                user,
                time
        };
        String[] description = {
                "主板(board)",
                "Android系统定制商(brand)",
                "CPU指令集(supported_abis)",
                "设备参数(device)",
                "显示屏幕参数(display)",
                "唯一编号(fingerprint)",
                "硬件序列号(serial)",
                "修订版本列表(id)",
                "硬件制造商(manufacturer)",
                "版本(model)",
                "硬件名(hardware)",
                "手机产品名(product)",
                "描述Build的标签(tags)",
                "Builer类型(type)",
                "当前开发代号(codename)",
                "源码控制版本(incremental)",
                "版本字符串(release)",
                "版本号(sdk_int)",
                "Host值(host)",
                "User名(user)",
                "编译时间(time)"
        };
        return makeInfoString(description, prop);
    }

    public static String getSystemPropertyInfo() {
        String os_version = System.getProperty("os.version"); // OS 版本
        String os_name = System.getProperty("os.name"); // OS 名称
        String os_arch = System.getProperty("os.arch"); // OS 架构
        String user_home = System.getProperty("user.home"); // Home 属性
        String user_name = System.getProperty("user.name"); // Name 属性
        String user_dir = System.getProperty("user.dir"); // Dir 属性
        String user_timezone = System.getProperty("user.timezone"); // 时区
        String path_separator = System.getProperty("path.separator"); // 路径分割符
        String line_separator = System.getProperty("line.separator"); // 行分割符
        String file_separator = System.getProperty("file.separator"); // 文件分隔符
        String java_vendor_url = System.getProperty("java.vendor.url"); // Java vender URL 属性
        String java_class_path = System.getProperty("java.class.path"); // Java Class 路径
        String java_class_version = System.getProperty("java.class.version"); // Java Class 版本
        String java_vendor = System.getProperty("java.vendor"); // Java vender 属性
        String java_version = System.getProperty("java.version"); // Java 版本
        String java_home = System.getProperty("java_home"); // java Home 属性
        String[] prop = {
                os_version,
                os_name,
                os_arch,
                user_home,
                user_name,
                user_dir,
                user_timezone,
                path_separator,
                line_separator,
                file_separator,
                java_vendor_url,
                java_class_path,
                java_class_version,
                java_vendor,
                java_version,
                java_home
        };
        String[] description = {
                "OS版本(os_version)",
                "OS名称(os_name)",
                "OS架构(os_arch)",
                "Home属性(user_home)",
                "Name属性(user_name)",
                "Dir属性(user_dir)",
                "时区(user_timezone)",
                "路径分割符(path_separator)",
                "行分割符(line_separator)",
                "文件分隔符(file_separator)",
                "java_vendor_url",
                "java_class_path",
                "java_class_version",
                "java_vendor",
                "java_version",
                "java_home"
        };
        return makeInfoString(description, prop);
    }
}
