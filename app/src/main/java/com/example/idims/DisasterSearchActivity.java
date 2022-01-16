package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
                startActivity(intent);//地域選択画面へ移行
            }
        });


        //災害の種類のチェックボタンの定義と挙動の設定 セット関数に引き渡す
        //津波
        CheckBox checkBox_Wave = (CheckBox) findViewById(R.id.checkBox_Wave);
        checkBox_Wave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    search.setWave_on(true);
                }else{
                    search.setWave_on(false);
                }
            }
        });

        //土砂崩れ
        CheckBox checkBox_Landsride = (CheckBox) findViewById(R.id.checkBox_Landsride);
        checkBox_Landsride.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    search.setLandsride_On(true);
                }else{
                    search.setLandsride_On(false);
                }
            }
        });

        //雷
        CheckBox checkBox_Thounder = (CheckBox) findViewById(R.id.checkBox_Thounder);
        checkBox_Thounder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    search.setThounder_On(true);
                }else{
                    search.setThounder_On(false);
                }
            }
        });

        //期間設定の挙動の処理
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup_period);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton radio = (RadioButton)findViewById(checkedId);
                if(radio.isChecked() == true){//ラジオボタンのどちらかが押されている場合
                    switch(checkedId){
                        case R.id.radioButton_Constant://一定期間が押されている場合
                            break;
                        case R.id.radioButton_free://自由期間が押されている場合
                            break;
                    }
                }
            }
        });


        //検索結果表示ボタン押下時の処理
        Button resultButton = (Button) findViewById(R.id.button_Search);
        resultButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //検索結果画面へ移動
                Intent intent = new Intent(DisasterSearchActivity.this, );
                startActivity(intent);

            }
        });
    }
}