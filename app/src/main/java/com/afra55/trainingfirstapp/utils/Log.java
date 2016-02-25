package com.afra55.trainingfirstapp.utils;

import com.afra55.trainingfirstapp.base.field.G;

/**
 * Log输出工具
 */
public class Log {
	
	/** 指示是否显示Log */
	public static boolean showLog = G.HostConst.isQA;
	
	/**
	 * 提示信息-黑色
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (showLog) {
			android.util.Log.v(tag, msg);
		}
	}
	
	
	/**
	 * 提示信息-黑色
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void v(String tag, String msg, Throwable tr) {
		if (showLog) {
			android.util.Log.v(tag, msg, tr);
		}
	}
	
	/**
	 * 调试-蓝色
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (showLog) {
			android.util.Log.d(tag, msg);
		}
	}
	
	/**
	 * 调试-蓝色
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void d(String tag, String msg, Throwable tr) {
		if (showLog) {
			android.util.Log.d(tag, msg, tr);
		}
	}
	
	/**
	 * 重要的信息-绿色
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (showLog) {
			android.util.Log.i(tag, msg);
		}
	}
	
	
	/**
	 * 重要的信息-绿色
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void i(String tag, String msg, Throwable tr) {
		if (showLog) {
			android.util.Log.i(tag, msg, tr);
		}
	}
	
	/**
	 * 警告-黄色
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (showLog) {
			android.util.Log.w(tag, msg);
		}
	}
	
	/**
	 * 警告-黄色
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void w(String tag, String msg, Throwable tr) {
		if (showLog) {
			android.util.Log.w(tag, msg, tr);
		}
	}
	
	/**
	 * 错误-红色
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (showLog) {
			android.util.Log.e(tag, msg);
		}
	}
	
	/**
	 * 错误-红色
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (showLog) {
			android.util.Log.e(tag, msg, tr);
		}
	}
}
