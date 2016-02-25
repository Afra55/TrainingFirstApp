package com.afra55.trainingfirstapp.module.remote_views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.base.TrainingFirstActivity;

public class RemoteViewsTestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_remote_views_test);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.remote_views_notify:
                remoteNotify();
                break;
        }
    }

    private void remoteNotify() {
        Notification notification = new Notification();
        notification.icon = R.drawable.icon;
        notification.tickerText = "tickerText";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(this, TrainingFirstActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.alert);
        remoteViews.setTextViewText(R.id.alert__title_tv, "哈哈哈哈哈");
        remoteViews.setOnClickPendingIntent(R.id.alert_confirm_btn, pendingIntent);
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        notification.fullScreenIntent = pendingIntent;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }
}
