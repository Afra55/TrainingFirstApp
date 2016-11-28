package com.afra55.trainingfirstapp.module.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.base.field.G;
import com.afra55.trainingfirstapp.utils.NewMessageNotification;

public class NotificationTestActivity extends BaseActivity {

    private int NOTIFY_ID = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_notification_test);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.opennotifacation:

                NewMessageNotification.notify(this, "Exxxx Afra", 2);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    showNotifi();
                }
                break;
        }
    }

    private void showNotifi() {
        Intent notifiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(G.UrlConst.CSDN_BLOG));
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationTestActivity.this, 0, notifiIntent, 0);
        Notification.Builder builder = new Notification.Builder(NotificationTestActivity.this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setFullScreenIntent(pendingIntent, true);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.beauty));
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);

        Notification notification = builder.build();
        RemoteViews contentRemoteView = new RemoteViews(getPackageName(), R.layout.notification);
        contentRemoteView.setTextViewText(R.id.textView, G.UrlConst.CSDN_BLOG);
        notification.contentView = contentRemoteView;
        RemoteViews expandsRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        expandsRemoteViews.setTextViewText(R.id.textView, G.UrlConst.CSDN_BLOG);
        expandsRemoteViews.setImageViewResource(R.id.imageView, R.drawable.beauty);
        notification.bigContentView = expandsRemoteViews;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFY_ID, notification);
    }
}
