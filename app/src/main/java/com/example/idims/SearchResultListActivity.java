package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;

import android.location.Geocoder;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DisasterSearchActivityから検索条件を受け取る
        Intent intentDisasterSearch = getIntent();
        areaNumber = intentDisasterSearch.getIntExtra("areaNumber", 0);
        waveOn = intentDisasterSearch.getBooleanExtra("Wave", false);
        landsrideOn = intentDisasterSearch.getBooleanExtra("landsride", false);
        thounderOn = intentDisasterSearch.getBooleanExtra("thounder", false);
        startTime = intentDisasterSearch.getStringExtra("startTime");
        endTime = intentDisasterSearch.getStringExtra("endTime");

        //デバック用
        debugGetData();

        //データベースに接続し結果を一覧で表示する
        /*
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


                }


            }
            if(landsrideOn){

            }
            if(thounderOn){

            }

        }catch(SQLException ex){

        }

        */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);
    }

    //データ受け取りが出来ているかのデバッグ
    private void debugGetData(){
        TextView textView = (TextView) findViewById(R.id.textView_DebugSR);
        if(landsrideOn == true || waveOn == true || thounderOn == true){
            if(startTime == null || endTime == null){
                textView.setText("areaNumber: " + areaNumber +", ALLTime");
            }else{
                textView.setText("areaNumber: " + areaNumber + "StartTime:" + startTime + "Endtime" + endTime);
            }
        }
    }

}