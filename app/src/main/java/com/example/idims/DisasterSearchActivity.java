package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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

        //検索条件を格納するSearchConditionsの宣言
        SearchConditions search = new SearchConditions();

        //検索地域選択ボタン押下時の処理
        Button serectAreaButton = (Button) findViewById(R.id.button_SelectArea);
        serectAreaButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(DisasterSearchActivity.this, SelectAreaActivity.class);
                startActivity(intent);
            }
        });

        //災害の種類のチェックボタンの定義(上から津波、土砂崩れ、雷)
        CheckBox checkBox_Wave = (CheckBox) findViewById(R.id.checkBox_Wave);
        CheckBox checkBox_Landsride = (CheckBox) findViewById(R.id.checkBox_Landsride);
        CheckBox checkBox_thounder = (CheckBox) findViewById(R.id.checkBox_Thounder);
        //種類チェックボタンが押されているかを格納するboolean型
        boolean wave_On = false;
        boolean landsride_On = false;
        boolean thounder = false;
        //それぞれのチェックボタンが

        //検索結果表示ボタン押下時の処理
        Button resultButton = (Button) findViewById(R.id.button_Search);
        resultButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //SearchCondisionsクラスに検索条件を引き渡す

                //検索結果画面へ移動
                Intent intent = new Intent(DisasterSearchActivity.this, );
                startActivity(intent);

            }
        });
    }
}