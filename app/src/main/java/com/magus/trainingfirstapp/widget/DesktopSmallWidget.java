package com.magus.trainingfirstapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.utils.Log;

/**
 * 桌面小部件.
 */
public class DesktopSmallWidget extends AppWidgetProvider {

    public static final String TAG = DesktopSmallWidget.class.getSimpleName();
    public static final String CLICK_ACTION = "com.afra55.appwidget.action.CLICK";

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive : action = " + intent.getAction());

        /* 判断是否是自己的Action */
        if (intent.getAction().equals(CLICK_ACTION)) {
            Toast.makeText(context, "fuck", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beauty);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.desktop_small_widget);
                        remoteViews.setImageViewBitmap(R.id.new_app_widget_img, rotateBitmap(context, bitmap, degree));
                        Intent clickIntent = new Intent();
                        clickIntent.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
                        remoteViews.setOnClickPendingIntent(R.id.new_app_widget_img, pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context, DesktopSmallWidget.class), remoteViews);
                        SystemClock.sleep(30);
                    }

                }
            }).start();
        }
    }

    /**
     * 旋转 bitmap
     * @param context
     * @param scrBitmap
     * @param degree
     * @return
     */
    private Bitmap rotateBitmap(Context context, Bitmap scrBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(scrBitmap, 0, 0, scrBitmap.getWidth(), scrBitmap.getHeight(), matrix, true);
    }


    /**
     * 小部件被添加时或者小部件更新时都会调用一次这个方法<br/>
     * 小部件的更新时间由 updatePeriodMillis 指定，每个周期小部件会自动更新一次。
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "onUpdate, counter = " + appWidgetIds.length);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    /**
     * 更新桌面小部件
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.i(TAG, "appWidgetId = " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.desktop_small_widget);

        /* 桌面小部件单机事件发送Intent广播 */
        Intent clickIntent = new Intent();
        clickIntent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.new_app_widget_img, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * 桌面小部件第一次添加到桌面时调用该方法，可添加多次，但只在第一次调用
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.i(TAG, "onEnabled");
    }

    /**
     * 当"最后一个"该类型的桌面小部件被删除时调用该方法。
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.i(TAG, "onDisabled");
    }

    /**
     * 删除一次小部件就调用一次
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i(TAG, "onDeleted");
    }
}

