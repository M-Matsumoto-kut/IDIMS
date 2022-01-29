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

    //地域を参照して県を格納する
    private ArrayList<String> prefList = new ArrayList<>(); //県が格納されているリスト



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
        allTime = intentDisasterSearch.getBooleanExtra("allTime", false);

        //ArrayListの引き渡しテスト
        /*
        ArrayList<Integer> intTest = intentDisasterSearch.getIntegerArrayListExtra("testArrayInt");
        ArrayList<String> stringTest = intentDisasterSearch.getStringArrayListExtra("testArrayStr");
         */

        //デバッグ用のクラス宣言
        debugDisasterSearchData debugData = new debugDisasterSearchData();

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


        /*
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
        */

        Log.d("debugDataOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO", "searching");


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
                    resultArea.add(String.valueOf(sb)); //場所
                    Log.d("OOOOOOOOOOOOOOOOOOOOOOOOOOOO", "setting");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //テキストビューで追加する
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlaytout_text);
        for(int i = 0;i <resultLat.size(); i++){
            TextView textView = new TextView(this);
            textView.setTextSize(16);
            //textView.setBackground(R.drawable.text_border);
            textView.setText(getDisasterName(resultConDis.get(i)) + "  レベル: " + resultLevel.get(i) + ", " + resultArea.get(i) + "\n 発生時刻: " + resultTime.get(i));
            linearLayout.addView(textView);
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
        //String hour = before.substring(8, 9); //時
        //String minute = before.substring(10, 11); //分
        //StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append(hour).append(":").append(minute); //結合
        StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append("00:00"); //結合

        return String.valueOf(str);

    }

    //都道府県が検索条件内に入っているかを確認するメソッド
    private boolean checkAdminArea(String str){
        //Log.d("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO", str);
        //Log.d("Prefを示せやこらあああああああああああああ", prefList.get(0));
        for(int i = 0; i < prefList.size(); i++){
            Log.d("現在のprefを示していますうううううううううううう", prefList.get(i));
            if(str.equals(prefList.get(i))){return true;} //同じ文字列が入っていた場合その地方に該当するので真を返す
            //Log.d("ループ回数を示しています......", String.valueOf(i));
        }
        return false;
    }

    //選択地方から県をリストに格納するメソッド
    /////////////////もしかしたら日本語も入れる必要性があるかもしれない
    private void addPrefFromAreaNum(int areaNum, ArrayList<String> prefList){
        if(areaNum == 1){ //北海道である場合
            prefList.add("Hokkaido");
            prefList.add("北海道");
        }else if(areaNum == 2){ //東北
            prefList.add("Aomori");
            prefList.add("青森県");
            prefList.add("Iwate");
            prefList.add("岩手県");
            prefList.add("Miyagi");
            prefList.add("宮城県");
            prefList.add("Akita");
            prefList.add("秋田県");
            prefList.add("Yamagata");
            prefList.add("山形県");
            prefList.add("Hukushima");
            prefList.add("福島県");

        }else if(areaNum == 3){ //関東
            prefList.add("Ibaraki");
            prefList.add("茨城県");
            prefList.add("Tochigi");
            prefList.add("栃木県");
            prefList.add("Gunma");
            prefList.add("群馬県");
            prefList.add("Saitama");
            prefList.add("埼玉県");
            prefList.add("Chiba");
            prefList.add("千葉県");
            prefList.add("Tokyo");
            prefList.add("東京都");
            prefList.add("Kanagawa");
            prefList.add("神奈川県");


        }else if (areaNum == 4) { //中部
            prefList.add("Niigata");
            prefList.add("新潟県");
            prefList.add("Toyama");
            prefList.add("富山県");
            prefList.add("Ishikawa");
            prefList.add("石川県");
            prefList.add("Fukui");
            prefList.add("福井県");
            prefList.add("Yamanashi");
            prefList.add("山口県");
            prefList.add("Nagano");
            prefList.add("長野県");
            prefList.add("Gifu");
            prefList.add("岐阜県");
            prefList.add("Shizuoka");
            prefList.add("静岡県");
            prefList.add("Aichi");
            prefList.add("愛知県");

        }else if (areaNum == 5){ //近畿
            prefList.add("Mie");
            prefList.add("三重県");
            prefList.add("Shiga");
            prefList.add("滋賀県");
            prefList.add("Kyoto");
            prefList.add("京都府");
            prefList.add("Osaka");
            prefList.add("大阪府");
            prefList.add("Hyogo");
            prefList.add("兵庫県");
            prefList.add("Nara");
            prefList.add("奈良県");
            prefList.add("Wakayama");
            prefList.add("和歌山県");

        }else if(areaNum == 6){ //中国・四国
            prefList.add("Tottori");
            prefList.add("鳥取県");
            prefList.add("Shimane");
            prefList.add("島根県");
            prefList.add("Okayama");
            prefList.add("岡山県");
            prefList.add("Hiroshima");
            prefList.add("広島県");
            prefList.add("Yamaguchi");
            prefList.add("山口県");
            prefList.add("Tokushima");
            prefList.add("徳島県");
            prefList.add("Kagawa");
            prefList.add("香川県");
            prefList.add("Ehime");
            prefList.add("愛媛県");
            prefList.add("Kochi");
            prefList.add("高知県");


        }else if(areaNum == 7){ //九州
            prefList.add("Fukuoka");
            prefList.add("福岡県");
            prefList.add("Saga");
            prefList.add("佐賀県");
            prefList.add("Nagasaki");
            prefList.add("長崎県");
            prefList.add("Kumamoto");
            prefList.add("熊本県");
            prefList.add("Oita");
            prefList.add("大分県");
            prefList.add("Miyazaki");
            prefList.add("宮崎県");
            prefList.add("Kagoshima");
            prefList.add("鹿児島県");
            prefList.add("Okinawa");
            prefList.add("沖縄県");

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