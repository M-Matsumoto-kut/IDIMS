package com.example.idims;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
//import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

//災害検知モジュール
public class DisasterDetection extends Application{
    private static final int MY_NOTIFICATION_ID = 1;

    private void disasterDetection() {

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, IMPORTANCE_HIGH)
                    .setTicker("Oh.")
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setContentTitle("fuck you")
                    .setContentText("bitch");

        //通知がタップされた時にホーム画面に遷移
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        )


        //新しい災害情報を取得した時，通知を行う（DB)
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, builder.build());
    }


}