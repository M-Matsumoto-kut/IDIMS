package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/*
    設定モジュール
 */
public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener(v -> {
            finish();
        });

        //スイッチ、チェックボックス
        Switch sw1 = findViewById(R.id.switch1);
        Switch sw2 = findViewById(R.id.switch2);
        Switch sw3 = findViewById(R.id.switch3);
        CheckBox cb1 = findViewById(R.id.checkBox1);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        CheckBox cb3 = findViewById(R.id.checkBox3);

// スイッチ、チェックボックスをオンにする
        sw1.setChecked(true);
        sw2.setChecked(true);
        sw3.setChecked(true);
        sw3.setClickable(false);
        cb1.setChecked(true);
        cb2.setChecked(true);
        cb3.setChecked(true);

// イベント
        // スイッチの状態が変化
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(sw1.isChecked()) {
                    //スイッチがOFF->ONなら
                    Toast.makeText(getApplication(), "位置情報ON", Toast.LENGTH_LONG).show();

                    //ここに位置情報をONにするコードを入れたい


                }else {
                    //スイッチがON->OFFなら
                    Toast.makeText(getApplication(), "位置情報OFF", Toast.LENGTH_LONG).show();

                    //ここに位置情報をOFFにするコードを入れたい


                }
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(sw2.isChecked()) {
                    //スイッチがOFF->ONなら
                    Toast.makeText(getApplication(), "通知、アラートON", Toast.LENGTH_LONG).show();

                    //ここに通知、アラートをONにするコードを入れたい
                    sw3.setChecked(true);
                    cb1.setChecked(true);
                    cb2.setChecked(true);
                    cb3.setChecked(true);
                    cb1.setClickable(true);
                    cb2.setClickable(true);
                    cb3.setClickable(true);


                }else {
                    //スイッチがON->OFFなら
                    Toast.makeText(getApplication(), "通知、アラートOFF", Toast.LENGTH_LONG).show();

                    //ここに通知、アラートをOFFにするコードを入れたい
                    sw3.setChecked(false);
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb1.setClickable(false);
                    cb2.setClickable(false);
                    cb3.setClickable(false);

                }
            }
        });

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(cb1.isChecked()) {
                    //チェックボックスがOFF->ONなら


                    //ここに位置情報をONにするコードを入れたい


                }else {
                    //チェックボックスがON->OFFなら


                    //ここに位置情報をOFFにするコードを入れたい


                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(cb2.isChecked()) {
                    //チェックボックスがOFF->ONなら


                    //ここに位置情報をONにするコードを入れたい


                }else {
                    //チェックボックスがON->OFFなら


                    //ここに位置情報をOFFにするコードを入れたい


                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(cb3.isChecked()) {
                    //スイッチがOFF->ONなら


                    //ここに位置情報をONにするコードを入れたい


                }else {
                    //スイッチがON->OFFなら


                    //ここに位置情報をOFFにするコードを入れたい


                }
            }
        });

    }
}