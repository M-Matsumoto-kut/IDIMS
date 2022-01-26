package com.example.idims;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


//非同期で定期的にDBにアクセスし，災害情報が更新されれば通知処理を行う
public class Asynchronous implements Runnable {

    public void run(){
        // Timerクラスのオブジェクトを作成
        Timer time = new Timer();

        // 一定間隔で処理を開始する
        // AccessTaskを、0秒後に、10秒間隔で実行する
        time.scheduleAtFixedRate(new AccessTask(), 0, 10000);

    }

    //定期的にDBにアクセス，災害発生時に通知を行う
    public class AccessTask extends TimerTask {

        public void run() {

            /*
                災害情報テーブルにアクセスしてみる．(Callable)
                (最後にDBにアクセスした後に追加された最新災害情報があるか検索(現在閲覧している地域，災害の種類,発生時刻で検索))
                現在地付近で最新の災害情報があった場合，
                その災害の種類（番号）を戻り値として返す
                 */
            // ExecutorServiceを生成
            ExecutorService ex = Executors.newSingleThreadExecutor();
            // Executorにスレッド newDisasterInfoSearch() (災害情報テーブルアクセス）の実行を依頼
            Future<Integer> futureResult = ex.submit(new NewDisInfoSearch.newDisasterInfoSearch());

            try {
                int newDisasterNum = futureResult.get();  //DBアクセス結果を取得(災害の種類の番号)


                //もし災害が発生したら通知
                if(newDisasterNum < 99) {
                    DisasterNotification notification = new DisasterNotification();
                    notification.disasterDetection(newDisasterNum);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
