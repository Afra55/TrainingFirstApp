//package com.magus.trainingfirstapp.umeng;
//
//import android.content.Context;
//
//import com.umeng.analytics.MobclickAgent;
//
//import java.util.Map;
//
///**
// * Created by guanjun on 2015/10/27.
// */
//public class MyMobclickAgent {
//
//    /**
//     * 页面统计
//     *
//     * @param string 页面名称
//     */
//    public static void onPageStart(String string) {
////		MobclickAgent.onPageStart(string);
//    }
//
//    /**
//     * 页面统计
//     *
//     * @param string 页面名称
//     */
//    public static void onPageEnd(String string) {
////		MobclickAgent.onPageEnd(string);
//    }
//
//    /**
//     * 自定义事件
//     *
//     * @param context  当前Activity引用
//     * @param event_id 事件id
//     */
//    public static void onEvent(Context context, String event_id) {
//        MobclickAgent.onEvent(context, event_id);
//    }
//
//    /**
//     * 自定义事件
//     *
//     * @param context  当前Activity引用
//     * @param event_id 事件id
//     */
//    public static void onEvent(Context context, String event_id, String params) {
//        MobclickAgent.onEvent(context, event_id, params);
//    }
//
//    /**
//     * 自定义事件
//     *
//     * @param context  当前Activity引用
//     * @param event_id 事件id
//     * @param map      当前事件的属性和取值集合
//     */
//    public static void onEvent(Context context, String event_id, Map<String, String> map) {
//        MobclickAgent.onEvent(context, event_id, map);
//    }
//
//
//    /**
//     * session的统计
//     *
//     * @param context 当前Activity引用
//     */
//    public static void onPause(Context context) {
//        MobclickAgent.onPause(context);
//    }
//
//    /**
//     * session的统计
//     *
//     * @param context 当前Activity引用
//     */
//    public static void onResume(Context context) {
//        MobclickAgent.onResume(context);
//    }
//
//    /**
//     * 设置调试模式(数据实时发送，不受发送策略控制)
//     *
//     * @param b
//     */
//    public static void setDebugMode(boolean b) {
//        MobclickAgent.setDebugMode(b);
//    }
//
//    /**
//     * 禁止默认的页面统计方式
//     *
//     * @param b
//     */
//    public static void openActivityDurationTrack(boolean b) {
//        MobclickAgent.openActivityDurationTrack(b);
//    }
//}
//
