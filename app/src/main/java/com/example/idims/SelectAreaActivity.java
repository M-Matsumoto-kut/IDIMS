package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.content.Intent;

import android.os.Bundle;


//災害検索画面内の地域選択画面アクティビティ
public class SelectAreaActivity extends DisasterSearchActivity {

    private int areaNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);

        //地域をint型で返す
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_area);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                if(radioButton.isChecked()){
                    switch(i){
                        case R.id.radioButton_Hokkaido:
                            areaNum = 1;
                            break;
                        case R.id.radioButton_Touhoku:
                            areaNum = 2;
                            break;
                        case R.id.radioButton_Kanto:
                            areaNum = 3;
                            break;
                        case R.id.radioButton_Chubu:
                            areaNum = 4;
                            break;
                        case R.id.radioButton_Kinki:
                            areaNum = 5;
                            break;
                        case R.id.radioButton_ChugokuShikoku:
                            areaNum = 6;
                            break;
                        case R.id.radioButton_Kyushu:
                            areaNum = 7;
                            break;
                    }
                    //選択結果を元の画面に渡す
                    Intent intent = new Intent();
                    intent.putExtra("areaNumber", areaNum);
                    setResult(RESULT_OK, intent);
                    //画面の終了
                    finish();
                }
            }
        });
    }
}