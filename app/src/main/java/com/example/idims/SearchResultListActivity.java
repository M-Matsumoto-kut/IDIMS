package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;

import android.location.Geocoder;
import android.os.Bundle;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class SearchResultListActivity extends AppCompatActivity {

    private int areaNumber;
    private boolean waveOn;
    private boolean landsrideOn;
    private boolean thounderOn;
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

        //DisasterSearchActivityから検索条件を受け取る
        Intent intentDisasterSearch = getIntent();
        areaNumber = intentDisasterSearch.getIntExtra("AreaNumber", 0);
        waveOn = intentDisasterSearch.getBooleanExtra("Wave", false);
        landsrideOn = intentDisasterSearch.getBooleanExtra("Landsride", false);
        thounderOn = intentDisasterSearch.getBooleanExtra("Thounder", false);
        startTime = intentDisasterSearch.getStringExtra("startTime");
        endTime = intentDisasterSearch.getStringExtra("endTime");

        //データベース検索用のArrayList 発生場所により不要な情報もあるので消去するため一時的な保存個所として置いておく
        ArrayList<Double> selectLat = new ArrayList<>();
        ArrayList<Double> selectLng = new ArrayList<>();
        ArrayList<Integer> selectLevel = new ArrayList<>();
        ArrayList<Integer> selectConDis = new ArrayList<>();
        ArrayList<Double> selectTime = new ArrayList<>();


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
                String sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_time FROM disaster WHERE disaster_class = 1 AND disaster_time >= " +
                        startTime + "% AND disaster_time <= " + endTime + "%;";
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

            }
            if(thounderOn){

            }

        }catch(SQLException ex){

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