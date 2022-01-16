package com.example.idims;

import android.widget.SimpleAdapter;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

//検索条件を格納するクラス
public class SearchConditions {
    //種類チェックボタンが押されているかを格納するboolean型,押されている場合は真となる
    public boolean wave_On = false;
    public boolean landsride_On = false;
    public boolean thounder_On = false;
    //一定期間において全ての期間が選ばれているかを判定するbool型
    public boolean allConstant = false;
    //日時を格納する変数
    public String endDate;//最新の方
    public String startDate;//過去の方


    //インストラクタ
    public SearchConditions(){}

    //期間設定において一定期間を選択した際にどの期間にするかを決定する
    public void settingConstant(String str) {
        endDate = getToday();
        String all = "全て";
        String a_month = "過去１か月";
        String half_year = "過去半年";
        String a_year = "過去１年";
        String five_year = "過去５年";
        if (str.equals(all)) {
            allConstant = true;//全ての日数有効化
        } else {
            allConstant = false;//全ての日数無効化
            //検索の起点となる時刻(過去の時刻のこと)を算出する
            Date date = new Date();//日数を格納
            Calendar calendar = Calendar.getInstance();//日数計算のためにカレンダークラスを使用
            calendar.setTime(date); //date型をcalendar型に変換

            if (str.equals(a_month)) {//過去1か月を選択した場合
                calendar.add(Calendar.MONTH, -1);
            } else if (str.equals(half_year)) {
                calendar.add(Calendar.MONTH, -6);
            } else if (str.equals(a_year)) {
                calendar.add(Calendar.YEAR, -1);
            } else if (str.equals(five_year)) {
                calendar.add(Calendar.YEAR, -5);
            }
            //計算された時刻をString型に変換する
            startDate = getStringToday(calendar);

        }
    }

    //現在日時の取得
    public String getToday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //年月日のみのデータに変換
        String str = sdf.format(date); //文字型に変換
        return str.replace("/", "");         //文字列にスラッシュ(//)が入っているので除去して返す
    }

    //日時の型変換メソッド 上と処理が似ているがこちらは時刻の変換のみ
    public String getStringToday(Calendar cal){
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //年月日のみのデータに変換
        String str = sdf.format(date);
        return str.replace("/", "");
    }



    //期間設定において自由期間を選択した際の期間選択
    public void settingFree(String startYear, String startMonth, String endYear, String endMonth){
        allConstant = false; //一応「全て」の無効化
        //文字列結合するだけ
        //開始年月
        startDate = new StringBuffer().append(startYear).append(startMonth).append("01").toString();
        //終了年月
        endDate = new StringBuffer().append(endYear).append(endMonth).append("01").toString();

    }

    //ここから下は検索条件に入っているかを判定するbool型のセット関数の集まり
    //津波
    public void setWave_on(boolean wave_On){
        this.wave_On = wave_On;
    }
    //地すべり
    public void setLandsride_On(boolean landsride_On){
        this.landsride_On = landsride_On;
    }
    //雷
    public void setThounder_On(boolean thounder_On){
        this.thounder_On = thounder_On;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

}
