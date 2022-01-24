package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
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
    public ArrayList<Double> resultLat = new ArrayList<>(); //緯度
    public ArrayList<Double> resultLng = new ArrayList<>(); //経度
    public ArrayList<Integer> resultLevel = new ArrayList<>(); //災害レベル
    public ArrayList<Integer> resultConDis = new ArrayList<>(); //災害種類
    public ArrayList<Double> resultTime = new ArrayList<>(); //発生時刻


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

        //データベースに接続し検索結果を格納する
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://idims-database-dev-1", "Numasa_89", "admin");
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
                Address address = (Address) geocoder.getFromLocation(lat, lng, 1);
                //都道府県を取得
                String addressStr = address.getAdminArea();
                if(checkAdminArea(addressStr, prefList)){ //割り出した都道府県が検索条件を満たす場合検索結果表示リストに入れる
                    resultLat.add(selectLat.get(i));
                    resultLng.add(selectLng.get(i));
                    resultLevel.add(selectLevel.get(i));
                    resultConDis.add(selectConDis.get(i));
                    resultTime.add(selectTime.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //リストで表示する
        ArrayList<Button> button = new ArrayList<>(); //ボタンのリスト表示
        for(int i = 0; i < resultLat.size(); i++){

        }

        //検索条件画面に戻る(初期化するため新しいインテントを渡す)

        //マップ表示にする





    }

    //都道府県が検索条件内に入っているかを確認するメソッド
    private boolean checkAdminArea(String str, ArrayList<String> prefList){
        for(int i = 0; i < prefList.size(); i++){
            if(str.equals(prefList.get(i))){return true;} //同じ文字列が入っていた場合その地方に該当するので真を返す
        }
        return false;
    }

    //選択地方から県をリストに格納するメソッド
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

}