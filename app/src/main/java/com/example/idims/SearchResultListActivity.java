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
    private ArrayList<Double> resultTime = new ArrayList<>(); //発生時刻

    //地方の4座標を格納する配列
    private Double[][] areaRange = new Double[4][2];

    //データベース検索用のArrayList 発生場所により不要な情報もあるので消去するため一時的な保存個所として置いておく
    ArrayList<Double> selectLat = new ArrayList<>(); //緯度
    ArrayList<Double> selectLng = new ArrayList<>(); //経度
    ArrayList<Integer> selectLevel = new ArrayList<>(); //レベル
    ArrayList<Integer> selectConDis = new ArrayList<>(); //災害種類
    ArrayList<Double> selectTime = new ArrayList<>(); //時間



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



        //デバック用
        debugGetData();
        debugTimeLook(startTime, endTime);



        //データベースに接続し検索結果を格納する
        if (waveOn) { //もし津波の検索条件がonならAWSのMYSQLサーバに接続
            AWSConnect con = new AWSConnect();
            String url = ""; //PHPファイルの場所
            StringBuffer var = new StringBuffer(); //SQL探索条件を格納する文字の代入,区切り文字はカンマ(,)
            con.setOnCallBack(this); //callbackの呼び出し?
            con.execute(url, String.valueOf(var)); //PHPファイルにアクセスしてSQLクエリを実行
        }
        if(landsrideOn){
            AWSConnect con = new AWSConnect();
            String url = ""; //PHPファイルの場所
            StringBuffer var = new StringBuffer(); //SQL探索条件を格納する文字の代入,区切り文字はカンマ(,)
            con.setOnCallBack(this); //callbackの呼び出し?
            con.execute(url, String.valueOf(var)); //PHPファイルにアクセスしてSQLクエリを実行

        }
        if(thounderOn){
            AWSConnect con = new AWSConnect();
            String url = ""; //PHPファイルの場所
            StringBuffer var = new StringBuffer(); //SQL探索条件を格納する文字の代入,区切り文字はカンマ(,)
            con.setOnCallBack(this); //callbackの呼び出し?
            con.execute(url, String.valueOf(var)); //PHPファイルにアクセスしてSQLクエリを実行

        }


        Log.d("debugDataOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO", "searching");

        //データベース検索後格納したデータから該当地域か判定し、真であれば格納する


        //デバッグ:単なるデータセット
        for(int i = 0; i < 20; i++){
            Double lat = debugData.getLatList(i);
            Double lng = debugData.getLngList(i);
            //住所を取得するジオコーダークラスの宣言
            Geocoder geocoder = new Geocoder(this);
            //住所を取得
            Log.d("テスト配列内の文字列を表示しま―――――――ス", String.valueOf(i));
            try {
                //住所を取得
                List<Address> address = geocoder.getFromLocation(lat, lng, 1);
                //都道府県を取得
                String addressAdm = address.get(0).getAdminArea(); //県名を取得

                    String addressLoc = address.get(0).getLocality();
                    StringBuffer sb = new StringBuffer().append(addressAdm).append(addressLoc);
                    resultLat.add(debugData.getLatList(i)); //緯度
                    resultLng.add(debugData.getLngList(i)); //経度
                    resultLevel.add(debugData.getLevelList(i)); //災害レベル
                    resultConDis.add(debugData.getConDisList(i)); //災害種類
                    resultTime.add(debugData.getTimeList(i)); //災害時間
                    Log.d("OOOOOOOOOOOOOOOOOOOOOOOOOOOO", "setting");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //テキストビューで追加する
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlaytout_text);
        for(int i = 0;i <resultLat.size(); i++){
            TextView textView = new TextView(this);
            textView.setTextSize(16); //文字サイズの指定
            textView.setBackgroundResource(R.drawable.text_border); //枠線を表示する背景ファイルの設定
            textView.setText(getDisasterName(resultConDis.get(i)) + "  レベル: " + resultLevel.get(i) + "\n 発生時刻: " + resultTime.get(i)); //テキストのセット
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
        Button mapButton = (Button) findViewById(R.id.button_Map);
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
                ArrayList<String> castTime = new ArrayList<>(); //発生時刻


                for(int i = 0; i < resultLat.size(); i++){ //double型をstring型に変換して返す
                    castLat.add(resultLat.get(i).toString());
                    castLng.add(resultLng.get(i).toString());
                    castTime.add(resultTime.get(i).toString());
                }
                intent.putStringArrayListExtra("resultLat", castLat); //緯度(型変換)
                intent.putStringArrayListExtra("resultLng", castLng); //経度(型変換)
                intent.putIntegerArrayListExtra("resultLevel", resultLevel); //災害レベル
                intent.putIntegerArrayListExtra("resultConDis", resultConDis); //災害種類
                intent.putStringArrayListExtra("resultTime", castTime); //発生時刻(型変換)
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

    //コールバックメソッド:mysql空のデータを処理する
    public void CallBack(String str){

        //災害発生個所の緯度経度
        double lat = 111;
    }

    //引数に渡された数字に応じて災害名の文字列を返すメソッド
    private String getDisasterName(int num){
        if(num == 1){return "津波";}
        else if(num == 2){return "土砂崩れ";}
        else if(num == 3){return "雷";}
        else{return "識別エラー";}
    }

    //~年~月~日~:~の表記として返すメソッド
    private String getNowTimeString(Double time){
        String before = String.valueOf(time); //ダブル型を文字列型に変換
        String year = before.substring(0, 3); //年を取得
        String month = before.substring(4, 5); //月
        String day = before.substring(6, 7); //日
        //String hour = before.substring(8, 9); //時
        //String minute = before.substring(10, 11); //分
        //StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append(hour).append(":").append(minute); //結合
        StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append("00:00"); //結合

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