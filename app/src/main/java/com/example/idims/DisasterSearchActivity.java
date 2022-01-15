package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//災害検索アクティビティ
public class DisasterSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面の表示
        setContentView(R.layout.activity_disaster_search);

        //検索地域選択ボタン押下時の処理
        Button serectAreaButton = (Button) findViewById(R.id.button_SelectArea);
        serectAreaButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(DisasterSearchActivity.this, SelectAreaActivity.class);
                startActivity(intent);
            }
        });

        //検索結果表示ボタン押下時の処理
        Button resultButton = (Button) findViewById(R.id.button_Search);
        resultButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Connection con_SearchResult = null;

                //try文でデータベースに接続し、失敗した場合はSQL例外で処理終了
                try{
                    //データベースMySQLに接続
                    con_SearchResult = DriverManager.getConnection(uri, username, passward);
                    Statement statement = con_SearchResult.createStatement();

                } catch(SQLException e){

                }

                //検索結果画面を表示
                //Intent intent = new Intent(DisasterSearchActivity.this, );

            }
        });
    }
}