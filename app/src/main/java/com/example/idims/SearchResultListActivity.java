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




public class SearchResultListActivity extends AppCompatActivity {

    private int areaNumber;
    private boolean waveOn;
    private boolean landsrideOn;
    private boolean thounderOn;
    private boolean allTime;
    private String startTime;
    private String endTime;

    //DBの検索結果を格納する
    private ArrayList<Double> resultLat = new ArrayList<>(); //緯度
    private ArrayList<Double> resultLng = new ArrayList<>(); //経度
    private ArrayList<Integer> resultLevel = new ArrayList<>(); //災害レベル
    private ArrayList<Integer> resultConDis = new ArrayList<>(); //災害種類
    private ArrayList<Double> resultTime = new ArrayList<>(); //発生時刻
    private ArrayList<String> resultArea = new ArrayList<>(); //発生地域


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);

        //地域を参照して県を格納する
        ArrayList<String> prefList = new ArrayList<>(); //県が格納されているリスト

        //DisasterSearchActivityから検索条件を受け取る
        Intent intentDisasterSearch = getIntent();
        areaNumber = intentDisasterSearch.getIntExtra("AreaNumber", 0);
        waveOn = intentDisasterSearch.getBooleanExtra("Wave", false);
        landsrideOn = intentDisasterSearch.getBooleanExtra("Landsride", false);
        thounderOn = intentDisasterSearch.getBooleanExtra("Thounder", false);
        startTime = intentDisasterSearch.getStringExtra("startTime");
        endTime = intentDisasterSearch.getStringExtra("endTime");
        allTime = intentDisasterSearch.getBooleanExtra("allTime", false);

        //ArrayListの引き渡しテスト
        /*
        ArrayList<Integer> intTest = intentDisasterSearch.getIntegerArrayListExtra("testArrayInt");
        ArrayList<String> stringTest = intentDisasterSearch.getStringArrayListExtra("testArrayStr");
         */

        //データベース検索用のArrayList 発生場所により不要な情報もあるので消去するため一時的な保存個所として置いておく
        ArrayList<Double> selectLat = new ArrayList<>();
        ArrayList<Double> selectLng = new ArrayList<>();
        ArrayList<Integer> selectLevel = new ArrayList<>();
        ArrayList<Integer> selectConDis = new ArrayList<>();
        ArrayList<Double> selectTime = new ArrayList<>();

        //地域を参照して県をリストに格納する
        addPrefFromAreaNum(areaNumber, prefList);


        //デバック用
        debugGetData();
        debugTimeLook(startTime, endTime);


        //データベースに接続し検索結果を格納する
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://idims-database-dev-1", "admin", "Numasa_89");
            Statement state = con.createStatement();
            ResultSet resultWave = null;
            ResultSet resultLandsride = null;
            ResultSet resultThounder = null;
            if(waveOn){
                String sql = null;
                if(allTime){ //全時間が対象の場合
                    sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_class = 1";

                }else{ //そうでない場合時間を指定
                    sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_class = 1 AND disaster_time >= " + startTime + " AND disaster_time <= " + endTime;
                }

                resultWave = state.executeQuery(sql); //データの取得
                while(resultWave.next()){
                    //検索結果を格納していく
                    selectLat.add(resultWave.getDouble("disaster_x")); //緯度
                    selectLng.add(resultWave.getDouble("disaster_y")); //経度
                    selectLevel.add(resultWave.getInt("disaster_level")); //災害レベル
                    selectConDis.add(resultWave.getInt("disaster_class")); //災害種類
                    selectTime.add(resultWave.getDouble("disaster_time")); //発生時刻
                }
            }
            if(landsrideOn){
                String sql = null;
                if(allTime){ //全時間が対象の場合
                    sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_class = 2";

                }else{ //そうでない場合時間を指定
                    sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_class = 2 AND disaster_time >= " + startTime + " AND disaster_time <= " + endTime;
                }

                resultLandsride = state.executeQuery(sql); //データの取得
                while(resultLandsride.next()){
                    //検索結果を格納していく
                    selectLat.add(resultLandsride.getDouble("disaster_x")); //緯度
                    selectLng.add(resultLandsride.getDouble("disaster_y")); //経度
                    selectLevel.add(resultLandsride.getInt("disaster_level")); //災害レベル
                    selectConDis.add(resultLandsride.getInt("disaster_class")); //災害種類
                    selectTime.add(resultLandsride.getDouble("disaster_time")); //発生時刻
                }

            }
            if(thounderOn){
                String sql = null;
                if(allTime){ //全時間が対象の場合
                    sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_class = 3";

                }else{ //そうでない場合時間を指定
                    sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_class = 3 AND disaster_time >= " + startTime + " AND disaster_time <= " + endTime;
                }

                resultThounder = state.executeQuery(sql); //データの取得
                while(resultThounder.next()){
                    //検索結果を格納していく
                    selectLat.add(resultThounder.getDouble("disaster_x")); //緯度
                    selectLng.add(resultThounder.getDouble("disaster_y")); //経度
                    selectLevel.add(resultThounder.getInt("disaster_level")); //災害レベル
                    selectConDis.add(resultThounder.getInt("disaster_class")); //災害種類
                    selectTime.add(resultThounder.getDouble("disaster_time")); //発生時刻
                }

            }

        }catch(SQLException ex){
            TextView textView = (TextView) findViewById(R.id.textView_ErrorDBCon);
            textView.setText("エラー:データベースに接続できませんでした。");
        }

        //格納した検索結果の緯度経度から住所を割り出し、該当する場所であるかを検索する
        //メモ:nullで帰ってくる場合は緯度経度に小数点以下が含まれている場合がある。それらがあると無理なのか？
        for(int i = 0; i < selectLat.size(); i++){
            //緯度経度を取得
            Double lat = selectLat.get(i);
            Double lng = selectLng.get(i);
            //住所を取得するジオコーダークラスの宣言
            Geocoder geocoder = new Geocoder(this);
            //住所を取得
            try {
                //住所を取得
                List<Address> address = geocoder.getFromLocation(lat, lng, 1);
                //都道府県を取得
                String addressAdm = address.get(0).getAdminArea(); //県名を取得
                if(checkAdminArea(addressAdm, prefList)){ //割り出した都道府県が検索条件を満たす場合検索結果表示リストに入れる
                    //市町村名を獲得して結合
                    String addressLoc = address.get(0).getLocality();
                    StringBuffer sb = new StringBuffer().append(addressAdm).append(addressLoc);
                    resultLat.add(selectLat.get(i)); //緯度
                    resultLng.add(selectLng.get(i)); //経度
                    resultLevel.add(selectLevel.get(i)); //災害レベル
                    resultConDis.add(selectConDis.get(i)); //災害種類
                    resultTime.add(selectTime.get(i)); //災害時間
                    resultArea.add(String.valueOf(sb)); //場所
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TableLayout mTableLayout = (TableLayout) findViewById(R.id.tablelayout_List);
        //テーブルで表示
        for(int i = -1; i < resultLat.size(); i++){

            //一列目の名前の定義
            final TextView textDisName = new TextView(this);
            textDisName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textDisName.setGravity(Gravity.LEFT);
            textDisName.setPadding(5, 15, 0, 15);
            if(i == -1){ //もし最初の行なら列名を代入

                textDisName.setText("災害名");
                textDisName.setBackgroundColor(Color.parseColor("#202020"));
                textDisName.setTextSize(16);

            }else{ //そうでないならば災害名を代入
                textDisName.setText(getDisasterName(resultConDis.get(i)));
            }

            //二列目の名前の定義
            final TextView textLevel = new TextView(this);
            textLevel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textLevel.setGravity(Gravity.LEFT);
            textLevel.setPadding(5, 15, 0, 15);
            if(i == -1){ //もし最初の行なら列名を代入
                textLevel.setText("レベル");
                textLevel.setBackgroundColor(Color.parseColor("#202020"));
                textLevel.setTextSize(20);
            }else{ //そうでないならば災害レベルを入力
                textDisName.setText(resultLevel.get(i).toString());
            }

            //三列目の名前の定義
            final TextView textArea = new TextView(this);
            textArea.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textArea.setGravity(Gravity.LEFT);
            textArea.setPadding(5, 15, 0, 15);
            if(i == -1){ //もし最初の行なら列名を代入
                textArea.setText("場所");
                textArea.setBackgroundColor(Color.parseColor("#202020"));
                textArea.setTextSize(12);
            }else{ //そうでないならば場所を入力
                textArea.setText(resultArea.get(i));
            }

            //四列目の名前の定義
            final TextView textTime = new TextView(this);
            textTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textTime.setGravity(Gravity.LEFT);
            textTime.setPadding(5, 15, 0, 15);
            if(i == -1){ //もし最初の行なら列名を代入
                textTime.setText("レベル");
                textTime.setBackgroundColor(Color.parseColor("#202020"));
                textTime.setTextSize(12);
            }else{ //そうでないならば緯度経度を入力
                textTime.setText(getNowTimeString(resultTime.get(i)));
            }

            //テーブルに行を追加
            final TableRow tr = new TableRow(this);
            tr.setId(i + 1);
            LinearLayout.LayoutParams trParams = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(0,0,0,0);
            tr.setPadding(0, 0, 0, 0);
            tr.setLayoutParams(trParams);
            tr.addView(textDisName); //災害名セット
            tr.addView(textLevel); //レベルセット
            tr.addView(textArea); //地名セット
            tr.addView(textTime); //時間セット

            //罫線を追加する
            if(i > -1){
                final TableRow trSep = new TableRow(this);
                TableLayout.LayoutParams trParamsSep = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(0, 0, 0, 0);
                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 4;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }

        /*
        //リストで表示する
        //ArrayList<Button> buttonResult = new ArrayList<>(); //ボタンのリスト表示
        for(int i = 0; i < resultLat.size(); i++){
            //ボタンを追加するビューの追加
            LinearLayout buttonResultRayout = new LinearLayout(this);
            buttonResultRayout.setOrientation(LinearLayout.VERTICAL); //垂直にビューを追加する
            setContentView(buttonResultRayout);

            //ボタンの追加
            Button buttonResult = new Button(this);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.height = 100; //ボタン高さのセット
            buttonLayoutParams.width = 200; //ボタン幅のセット

            buttonResult.setLayoutParams(buttonLayoutParams); //レイアウトにボタンをセット
            //buttonResult.setBackground(getDrawable());

            //ボタンにテキストを設定
            buttonResult.setTextSize(20); //ボタンのテキストサイズ設定
            buttonResult.setTextColor(0xffffffff); //ボタンのテキスト色設定
            buttonResult.setText(getDisasterName(resultConDis.get(i)) + " [レベル" + resultLevel.get(i) + "] " + resultArea.get(i) + "\n " + getNowTimeString(resultTime.get(i))); //ボタンにテキストをセット


            //ボタンレイアウトを追加
            buttonResultRayout.addView(buttonResult);

            //ボタンの挙動追加(マップ表示を行う画面へ遷移)
            buttonResult.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    //マップ出力時に必要となる識別番号リストの作成
                    ArrayList<Integer> disasterNum = new ArrayList<>();
                    for(int j = 0; j < resultLat.size(); j++){ //数字をループで代入させ、
                        disasterNum.add(j);
                    }
                    Intent intent = new Intent(SearchResultListActivity.this, SearchResultMapActivity.class);
                    //データを受け渡してアクティビティを移動する
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
                    intent.putStringArrayListExtra("resultArea", resultArea); //発生地域
                    intent.putIntegerArrayListExtra("disasterNumber", disasterNum); //災害番号
                    intent.putExtra("areaNumber", areaNumber); //検索地域
                    startActivity(intent);


                }
            });



        }
        */

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
                intent.putStringArrayListExtra("resultArea", resultArea); //発生地域
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
        String hour = before.substring(8, 9); //時
        String minute = before.substring(10, 11); //分
        StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append(hour).append(":").append(minute); //結合
        return String.valueOf(str);

    }

    //都道府県が検索条件内に入っているかを確認するメソッド
    private boolean checkAdminArea(String str, ArrayList<String> prefList){
        for(int i = 0; i < prefList.size(); i++){
            if(str.equals(prefList.get(i))){return true;} //同じ文字列が入っていた場合その地方に該当するので真を返す
        }
        return false;
    }

    //選択地方から県をリストに格納するメソッド
    /////////////////もしかしたら日本語も入れる必要性があるかもしれない
    private void addPrefFromAreaNum(int areaNum, ArrayList<String> prefList){
        if(areaNum == 1){ //北海道である場合
            prefList.add("Hokkaido");
        }else if(areaNum == 2){ //東北
            prefList.add("Aomori");
            prefList.add("Iwate");
            prefList.add("Miyagi");
            prefList.add("Akita");
            prefList.add("Yamagata");
            prefList.add("Hukushima");

        }else if(areaNum == 3){ //関東
            prefList.add("Ibaraki");
            prefList.add("Tochigi");
            prefList.add("Gunma");
            prefList.add("Saitama");
            prefList.add("Chiba");
            prefList.add("Tokyo");
            prefList.add("Kanagawa");


        }else if (areaNum == 4) { //中部
            prefList.add("Niigata");
            prefList.add("Toyama");
            prefList.add("Ishikawa");
            prefList.add("Fukui");
            prefList.add("Yamanashi");
            prefList.add("Nagano");
            prefList.add("Gifu");
            prefList.add("Shizuoka");
            prefList.add("Aichi");

        }else if (areaNum == 5){ //近畿
            prefList.add("Mie");
            prefList.add("Shiga");
            prefList.add("Kyoto");
            prefList.add("Osaka");
            prefList.add("Hyogo");
            prefList.add("Nara");
            prefList.add("Wakayama");

        }else if(areaNum == 6){ //中国・四国
            prefList.add("Tottori");
            prefList.add("Shimane");
            prefList.add("Okayama");
            prefList.add("Hiroshima");
            prefList.add("Yamaguchi");
            prefList.add("Tokushima");
            prefList.add("Kagawa");
            prefList.add("Ehime");
            prefList.add("Kochi");


        }else if(areaNum == 7){ //九州
            prefList.add("Fukuoka");
            prefList.add("Saga");
            prefList.add("Nagasaki");
            prefList.add("Kumamoto");
            prefList.add("Oita");
            prefList.add("Miyazaki");
            prefList.add("Kagoshima");
            prefList.add("Okinawa");

        }
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