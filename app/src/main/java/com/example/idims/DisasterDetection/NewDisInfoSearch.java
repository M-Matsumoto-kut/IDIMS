package com.example.idims.DisasterDetection;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class NewDisInfoSearch extends AppCompatActivity {
    //最新の災害情報を災害情報テーブルから検索(現在地の）

    public static class newDisasterInfoSearch implements Callable<Integer> {
        LocalDateTime nowDateTime;
        DateTimeFormatter dateTime;
        String lastTime;

        public Integer call() {

            int disasterNum = 99;   //災害の種類の番号(0:津波, 1:土砂崩れ, 2:雷, 99:初期値)

            try {

                //災害情報テーブルにアクセス

                //最後にアクセスした時刻よりも後のとき->現在地に近いか調べる

                    //現在地の位置情報を取得

                    //災害発生地点が現在地と近い時->災害の種類を返す

                //現在時刻を取得
                // 現在日時情報で初期化されたインスタンスの取得
                nowDateTime = LocalDateTime.now();
                dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                // 日時情報を指定フォーマットの文字列で取得,最終アクセス時刻を更新
                lastTime = nowDateTime.format(dateTime);

                return disasterNum;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return disasterNum;
        }
    }
}
