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

//災害発生を通知し，ホーム画面に遷移させる
public class DisasterDetection extends Application{
    private static final int MY_NOTIFICATION_ID = 1;
    private String[] disasterName = {"津波", "土砂崩れ","雷"};

    //発生した災害の種類を取得（現在地における)
    public void disasterDetection(int disasterNum) {



        //通知内容の設定
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                    .setTicker("災害発生通知")
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setContentTitle(disasterName[disasterNum] + "があなたの近くで発生しました!")
                    .setContentText("通知をタップして災害情報を確認してください");

        //通知(Notification)
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, builder.build());

        //通知がタップされた時にホーム画面に遷移
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //新しい災害情報を取得した時，通知を行う（DB)
        //最後にDBにアクセスした後に追加された最新災害情報があるか検索(現在閲覧している地域，災害の種類,発生時刻で検索)
        //もしあったら通知・アラートの許可状態によって通知方法を選択・通知　
        //最後にDBにアクセスした時刻を更新
    }
}