package com.magus.trainingfirstapp.utils.download_utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.Environment;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.magus.trainingfirstapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author yangshuai
 * @version 创建时间：2015-7-16 下午1:44:22 
 * 类说明 :下载服务
 */
public class DownLoadService {

	private Notification notification;
	private NotificationManager mNotiManager;
	private ConditionVariable mCondition;
	private int downLoadSize = 0; // 当前下载量
	private int fileSize = -1;
	private final int MSG_SUCESS = 1; // 下载成功
	private final int MSG_DOWNLOAD = 2;// 下载
	private final int MSG_ERROR = 3; // 下载出错
	private final int MSG_CANCEL = 4;// 取消下载
	private final int MSG_PAUSE = 5;// 暂停下载
	private final int MSG_CONTINUATION = 6;// 继续下载
	private final int MSG_REFESH_NOFI = 10;// 刷新notify
	private final int MSG_SDCARD_ERROR = 11;// 刷新notify
	private String path = "";
	private Activity mContext;
	private String apkName = "";
	private int NOTIFY_ID = 20;
	private String fileName ="";
	public DownLoadService(Activity mContext, String downPath) {
		String [] items = downPath.split("/");
		apkName = items[items.length-1];
		fileName = getApkCachePath(mContext) + File.separator + apkName;
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}
		path = downPath;
		this.mContext = mContext;
	}

	public void startDownLoad() {
		showNotify();
		new Thread() {
			public void run() {
				if(!hasSDcard()){
					handler.sendEmptyMessage(MSG_SDCARD_ERROR);
					return;
				}
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(10000 /* milliseconds */);
					conn.setConnectTimeout(15000 /* milliseconds */);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					// 开始查询
					conn.connect();
					InputStream stream = conn.getInputStream();
					fileSize = conn.getContentLength();

//					DefaultHttpClient client = new DefaultHttpClient();
//
//					HttpGet httpGet = new HttpGet(path);
//					HttpResponse response = client.execute(httpGet);
//
//					HttpEntity httpEntity = response.getEntity();

//					fileSize = (int) httpEntity.getContentLength() ;

					int s = fileSize;
					File file = new File(fileName);
					if(file.exists()&&file.length()==fileSize){ // 如果文件存在并且已经下载完毕
						downLoadSize = fileSize;
						handler.sendEmptyMessage(MSG_SUCESS);
					}else{
						if (fileSize == -1) { // 无法获取数据大小
							handler.sendEmptyMessage(MSG_ERROR);
							return;
						}
						handler.sendEmptyMessage(MSG_REFESH_NOFI);
//					InputStream in = httpEntity.getContent();
						InputStream in = stream;
						OutputStream os = new FileOutputStream(fileName);
						byte[] b = new byte[1024 * 30];
						if (in != null) {
							int i = 0;
							while ((i = in.read(b)) != -1) {
								os.write(b, 0, i);
								downLoadSize += i;
							}
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				// 进行下载工作，这里需要更新downLoadQueue中对应的queue的进度信息
				// 使用Handler更新notification信息

			}
		}.start();
	}

	private void showNotify() {
		mCondition = new ConditionVariable(false);
		ApplicationInfo info = mContext.getApplicationInfo();
		CharSequence lable = mContext.getPackageManager().getApplicationLabel(
				info);
		apkName  = lable+"";
		CharSequence title = "正在下载...";
		long when = System.currentTimeMillis();
		Notification.Builder mNotify = new Notification.Builder(mContext);

		mNotify.setWhen(when + 10000);

		// 这里为空
		mNotify.setSmallIcon(R.drawable.icon_logo_s);
		mNotify.setTicker(title);
		mNotify.setOngoing(true);
		mNotify.setAutoCancel(false);



		// 1、创建一个自定义的消息布局 view.xml
		// 2、在程序代码中使用RemoteViews的方法来定义image和text。然后把RemoteViews对象传到contentView字段
		RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.down_load_notifi);
		remoteView.setProgressBar(R.id.progress_horizontal, fileSize, downLoadSize, true);
		remoteView.setImageViewResource(R.id.image, R.drawable.icon_logo);
		remoteView.setTextViewText(R.id.text, lable);
		mNotify.setContent(remoteView);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,new Intent(), 0);
		mNotify.setContentIntent(contentIntent);

		mNotiManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);

		notification = mNotify.getNotification();
		mNotiManager.notify(NOTIFY_ID, notification);

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case MSG_ERROR:
					Toast.makeText(mContext, "无法获取文件大小",Toast.LENGTH_SHORT).show();
					mNotiManager.cancelAll();
					break;
				case MSG_SDCARD_ERROR:
					Toast.makeText(mContext, "当前SDcard不存在，或不可用",Toast.LENGTH_SHORT).show();
					mNotiManager.cancelAll();
					break;
				case MSG_REFESH_NOFI:
					new Thread(new Runnable() {

						@Override
						public void run() {
							while (downLoadSize < fileSize) {
								handler.sendEmptyMessage(MSG_DOWNLOAD);
								mCondition.block(500);
							}
							handler.sendEmptyMessage(MSG_SUCESS);
						}
					}).start();
					break;
				case MSG_SUCESS:
					Intent intent1 = installApk();
					PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,intent1, 0);
					notification.contentIntent = contentIntent;
				case MSG_DOWNLOAD:
					notification.contentView.setProgressBar(R.id.progress_horizontal,
							fileSize, downLoadSize, false);
					notification.contentView.setTextViewText(R.id.text1,(int)(( Double.valueOf(downLoadSize)/Double.valueOf(fileSize))*100)+"%");
					mNotiManager.notify(NOTIFY_ID, notification);
					break;
				default:
					break;
			}

		}
	};

	public static boolean hasSDcard() {
		boolean b = false;
		try {
			b = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
		}
		return b;
	}
	private Intent installApk(){
		Intent intent1 = new Intent(Intent.ACTION_VIEW);
		try {
			intent1.setDataAndType(Uri.fromFile(new File(fileName)),
					"application/vnd.android.package-archive");
		} catch (Exception e) {
		}
		mContext.startActivity(intent1);
		return intent1;
	}
	/**
	 * 获得APK缓存目录
	 * @param context
	 * @return
	 */
	public static String getApkCachePath(Context context){
		String apk_path = cachePath(context)+"apk";
		File file = new File(apk_path);
		if (!file.exists()){
			file.mkdir();
		}
		return apk_path;
	}
	/*SDcard的动态路径*/
	static String sdcardPath = Environment.getExternalStorageDirectory() + "";
	/**
	 * 获得缓存的绝对路径
	 * @param context
	 * @return
	 */
	public static String cachePath(Context context){
		String path;
		if (hasSDcard()){
			path = sdcardPath+File.separator+ context.getResources().getString(R.string.app_name);
			File file = new File(path);
			if (!file.isDirectory()){
				file.delete();
			}
			if (file == null || !file.exists()){
				file.mkdirs();
			}
			path += File.separator;

		}else
		{
			path = context.getCacheDir().getAbsolutePath();
		}
		return path;
	}

}
