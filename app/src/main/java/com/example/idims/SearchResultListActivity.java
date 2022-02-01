package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;

import android.os.Bundle;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;




public class SearchResultListActivity extends AppCompatActivity implements AWSConnect.CallBackTask{

    //検索条件画面から受け取った検索条件
    private int areaNumber; //選択地方
    private boolean waveOn; //検索条件_津波
    private boolean landsrideOn; //検索条件_地すべり
    private boolean thounderOn; //検索条件_雷
    private String startTime; //検索条件_開始時刻
    private String endTime; //検索条件_終了時刻

    //DBの検索結果を格納する
    private ArrayList<Double> resultLat = new ArrayList<>(); //緯度
    private ArrayList<Double> resultLng = new ArrayList<>(); //経度
    private ArrayList<Integer> resultLevel = new ArrayList<>(); //災害レベル
    private ArrayList<Integer> resultConDis = new ArrayList<>(); //災害種類
    private ArrayList<String> resultTime = new ArrayList<>(); //発生時刻

    //地方の4座標を格納する配列
    private Double[] areaLange = new Double[4]; //左緯度、右緯度、上経度、下経度

    //データベース検索用のArrayList 発生場所により不要な情報もあるので消去するため一時的な保存個所として置いておく
    ArrayList<Double> selectLat = new ArrayList<>(); //緯度
    ArrayList<Double> selectLng = new ArrayList<>(); //経度
    ArrayList<Integer> selectLevel = new ArrayList<>(); //レベル
    ArrayList<Integer> selectConDis = new ArrayList<>(); //災害種類
    ArrayList<String> selectTime = new ArrayList<>(); //時間

    //Nullを処理した場合onになりマップに移動できない
    private boolean nullP = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);


        //DisasterSearchActivityから検索条件を受け取る
        Intent intentDisasterSearch = getIntent();
        areaNumber = intentDisasterSearch.getIntExtra("AreaNumber", 0);
        waveOn = intentDisasterSearch.getBooleanExtra("Wave", false);
        landsrideOn = intentDisasterSearch.getBooleanExtra("Landsride", false);
        thounderOn = intentDisasterSearch.getBooleanExtra("Thounder", false);
        startTime = intentDisasterSearch.getStringExtra("startTime");
        endTime = intentDisasterSearch.getStringExtra("endTime");


        //デバッグ用のクラス宣言
        debugDisasterSearchData debugData = new debugDisasterSearchData();



        //地方を参照して選択地方を囲んだ4点の座標を格納する
        setAreaLange(areaNumber);



        //デバック用
        debugGetData();
        debugTimeLook(startTime, endTime);



        //データベースに接続し検索結果を格納する
        if (waveOn) { //もし津波の検索条件がonならAWSのMYSQLサーバに接続
            AWSConnect con = new AWSConnect();
            String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/disastersearch.php"; //PHPファイルの場所
            StringBuffer var = new StringBuffer().append("value=").append(1).append(",").append(startTime).append(",").append(endTime); //SQL探索条件を格納する文字の代入,区切り文字はカンマ(,)
            con.setOnCallBack(this); //callbackの呼び出し?
            con.execute(url, String.valueOf(var)); //PHPファイルにアクセスしてSQLクエリを実行
        }
        if(landsrideOn){
            AWSConnect con = new AWSConnect();
            String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/disastersearch.php"; //PHPファイルの場所
            StringBuffer var = new StringBuffer().append("value=").append(2).append(",").append(startTime).append(",").append(endTime); //SQL探索条件を格納する文字の代入,区切り文字はカンマ(,)
            con.setOnCallBack(this); //callbackの呼び出し?
            con.execute(url, String.valueOf(var)); //PHPファイルにアクセスしてSQLクエリを実行

        }
        if(thounderOn){
            AWSConnect con = new AWSConnect();
            String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/disastersearch.php"; //PHPファイルの場所
            StringBuffer var = new StringBuffer().append("value=").append(3).append(",").append(startTime).append(",").append(endTime); //SQL探索条件を格納する文字の代入,区切り文字はカンマ(,)
            con.setOnCallBack(this); //callbackの呼び出し?
            con.execute(url, String.valueOf(var)); //PHPファイルにアクセスしてSQLクエリを実行

        }


        Log.d("debugDataOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO", "searching");

        Button mapButton = (Button) findViewById(R.id.button_Map); //マップ画面遷移ボタン


        //データベース検索後格納したデータから該当地域か判定し、真であれば格納する
        try{
            if(selectLat.size() != 0){ //データを受け取ってない場合そもそも判定しない
                for(int i = 0; i < selectLat.size(); i++){
                    double nowlat = selectLat.get(i);
                    double nowlng = selectLng.get(i);
                    if(areaLange[0] <= nowlat && areaLange[1] > nowlat){//もし 経度下 < 災害緯度 < 経度上 かつ 緯度左 < 緯度右ならばデータをセット
                        resultLat.add(nowlat); //緯度
                        resultLng.add(nowlng); //経度
                        resultConDis.add(selectConDis.get(i)); //災害種類
                        resultLevel.add(selectLevel.get(i)); //災害レベル
                        resultTime.add(selectTime.get(i)); //時間
                    }
                }
            }
        }catch(NullPointerException e){ //そもそもデータベースに一致する条件がなければこうするほかなし
            Log.d("配列指定エラー", "NullPointerException");
            //マップ遷移ボタンを押下不可にする
            mapButton.setClickable(false);
            nullP = true;
        }


        /*
        //デバッグ:単なるデータセット
        for(int i = 0; i < 20; i++){
            Double lat = debugData.getLatList(i);
            Double lng = debugData.getLngList(i);
            resultLat.add(debugData.getLatList(i)); //緯度
            resultLng.add(debugData.getLngList(i)); //経度
            resultLevel.add(debugData.getLevelList(i)); //災害レベル
            resultConDis.add(debugData.getConDisList(i)); //災害種類
            resultTime.add(debugData.getTimeList(i)); //災害時間
            Log.d("OOOOOOOOOOOOOOOOOOOOOOOOOOOO", "setting");
        }

         */





        //テキストビューで追加する
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout_text);
        for(int i = 0;i <resultLat.size(); i++){
            TextView textView = new TextView(this);
            textView.setTextSize(14); //文字サイズの指定
            textView.setBackgroundResource(R.drawable.text_border); //枠線を表示する背景ファイルの設定
            textView.setText(getDisasterName(resultConDis.get(i)) + "  レベル" + resultLevel.get(i) + "  ,緯度:"  + getLatitudeString(resultLat.get(i)) + "  ,経度:" + getLongtitudeString(resultLng.get(i))  +"\n発生時刻: " + getNowTimeString(resultTime.get(i))); //テキストのセット
            linearLayout.addView(textView); //テキストの表示
            //空行を入力
            if(i == resultLat.size() - 1){
                TextView kara = new TextView(this);
                kara.setTextSize(20);
                kara.setText("\n \n");
                linearLayout.addView(kara);
            }
        }

        //マップ画面に移行する
        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //マップ出力時に必要となる識別番号リストの作成
                ArrayList<Integer> disasterNum = new ArrayList<>();
                for(int j = 0; j < resultLat.size(); j++){ //数字をループで代入させ、
                    disasterNum.add(j);
                }
                //データを受け渡してアクティビティを移動する
                Intent intent = new Intent(SearchResultListActivity.this, SearchResultMapActivity.class);
                //その前に型変換を行う
                ArrayList<String> castLat = new ArrayList<>(); //緯度
                ArrayList<String> castLng = new ArrayList<>(); //経度


                for(int i = 0; i < resultLat.size(); i++){ //double型をstring型に変換して返す
                    castLat.add(resultLat.get(i).toString());
                    castLng.add(resultLng.get(i).toString());
                }
                intent.putStringArrayListExtra("resultLat", castLat); //緯度(型変換)
                intent.putStringArrayListExtra("resultLng", castLng); //経度(型変換)
                intent.putIntegerArrayListExtra("resultLevel", resultLevel); //災害レベル
                intent.putIntegerArrayListExtra("resultConDis", resultConDis); //災害種類
                intent.putStringArrayListExtra("resultTime", resultTime); //発生時刻(型変換)
                intent.putIntegerArrayListExtra("disasterNumber", disasterNum); //災害番号
                intent.putExtra("areaNumber", areaNumber); //検索地域
                startActivity(intent);

            }
        });

        //検索条件画面に戻る(初期化するため新しいインテントを渡す)
        Button backSearchCon = (Button) findViewById(R.id.button_BackDS);
        backSearchCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultListActivity.this, DisasterSearchActivity.class);
                startActivity(intent);
            }
        });


    }

    //地方を囲む四方の頂点緯度経度を格納する
    public void setAreaLange(int num){
        if(num == 1){ //北海道の座標
            areaLange[0] = 41.26171; //緯度下
            areaLange[1] = 46.62585; //緯度上
            areaLange[2] = 139.18517; //経度右
            areaLange[3] = 150.93011; //経度左

        }else if(num == 2){ //東北
            areaLange[0] = 36.68679; //緯度下
            areaLange[1] = 41.77072; //緯度上
            areaLange[2] = 138.78217; //経度右
            areaLange[3] = 142.31465; //経度左

        }else if(num == 3){ //関東
            areaLange[0] = 34.59297; //緯度下
            areaLange[1] = 37.27521; //緯度上
            areaLange[2] = 138.29331; //経度右
            areaLange[3] = 141.15635; //経度左

        }else if(num == 4){ //中部
            areaLange[0] = 34.21240; //緯度下
            areaLange[1] = 38.77298; //緯度上
            areaLange[2] = 135.36760; //経度右
            areaLange[3] = 140.11170; //経度左

        }else if(num == 5){ //近畿
            areaLange[0] = 33.20705; //緯度下
            areaLange[1] = 36.05544; //緯度上
            areaLange[2] = 133.98506; //経度右
            areaLange[3] = 137.10602; //経度左

        }else if(num == 6){ //中国四国
            areaLange[0] = 32.42970; //緯度下
            areaLange[1] = 36.45527; //緯度上
            areaLange[2] = 130.95228; //経度右
            areaLange[3] = 135.33631; //経度左

        }else if(num == 7){ //九州
            areaLange[0] = 25.57795; //緯度下
            areaLange[1] = 34.96802; //緯度上
            areaLange[2] = 127.10649; //経度右
            areaLange[3] = 132.20441; //経度左

        }
    }

    //コールバックメソッド:mysql空のデータを処理する
    public void CallBack(String result){
        Log.d("CallBackが呼び出されました", result);
        String[] tmp = result.split(","); //,(カンマ)を区切り文字として文字型配列に格納
        int alpha = 5; //SQLで要求する要素数
        for(int i = 0; i < tmp.length - alpha; i += alpha){
            selectLat.add(Double.parseDouble(tmp[i])); //緯度を追加
            selectLng.add(Double.parseDouble(tmp[i + 1])); //経度を追加
            selectConDis.add(Integer.parseInt(tmp[i + 2])); //災害種類を追加
            selectLevel.add(Integer.parseInt(tmp[i + 3])); //災害レベルを追加
            selectTime.add(tmp[i + 4]); //災害時間を追加
        }
    }

    //引数に渡された数字に応じて災害名の文字列を返すメソッド
    private String getDisasterName(int num){
        if(num == 1){return "津波";}
        else if(num == 2){return "土砂崩れ";}
        else if(num == 3){return "雷";}
        else{return "識別エラー";}
    }

    //緯度を成形した文字型として返すメソッド
    private String getLatitudeString(Double lat){
        String strLat = String.valueOf(lat);
        String ret = strLat;
        if(strLat.length() >= 6){
            ret = strLat.substring(0, 6); //小数点3桁まで取得(整数2桁、少数桁3桁)
        }
        return ret;
    }

    //経度を成型した文字型として返すメソッド
    private String getLongtitudeString(Double lng){
        String strLng = String.valueOf(lng);
        String ret = strLng;
        if(strLng.length() >= 7){
            ret = strLng.substring(0, 7); //小数点3桁まで取得(整数3桁、少数桁3桁)
        }
        return ret;
    }




    //~年~月~日~:~の表記として返すメソッド
    private String getNowTimeString(String time){
        //String before = String.valueOf(time); //ダブル型を文字列型に変換
        String year = time.substring(0, 4); //年を取得
        String month = time.substring(4, 6); //月
        String day = time.substring(6, 8); //日
        String hour = time.substring(8, 10); //時
        String minute = time.substring(10, 12); //分
        Log.d("表示の確認", year);
        Log.d("表示の確認", month);
        Log.d("表示の確認", day);
        Log.d("表示の確認", hour);
        Log.d("表示の確認", minute);
        StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append(hour).append(":").append(minute); //結合
        return String.valueOf(str);

    }




    //Android搭載の戻るボタンの無効化 処理を書かないことにより無効化される
    @Override
    public void onBackPressed(){}



    //データ受け取りが出来ているかのデバッグ
    private void debugGetData(){
        TextView textView = (TextView) findViewById(R.id.textView_DebugSR);
        if(landsrideOn == true || waveOn == true || thounderOn == true){
            if(startTime == null || endTime == null){
                textView.setText("areaNumber: " + areaNumber +", ALLTime");
            }else{
                textView.setText("areaNumber: " + areaNumber);
            }
        }
    }

    private void debugTimeLook(String start, String end){
        TextView textView = (TextView) findViewById(R.id.textView_timeSet);
        if(start != null && end != null){
            textView.setText("StartTime: " + startTime + ", EndTime: " + endTime);
        }
    }

}