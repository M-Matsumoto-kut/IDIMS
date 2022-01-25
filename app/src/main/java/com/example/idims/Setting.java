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

        //各種スイッチ、チェックボックス
        Switch gpsSw = findViewById(R.id.gpsSwitch);
        Switch noticeSw = findViewById(R.id.noticeSwitch);
        Switch alertSw = findViewById(R.id.alertSwitch);
        CheckBox tsunamiCb = findViewById(R.id.tsunamiCheckBox);
        CheckBox landslideCb = findViewById(R.id.landslideCheckBox);
        CheckBox thunderCb = findViewById(R.id.thunderCheckBox);

        // 各種スイッチ、チェックボックスをONの状態にする
        gpsSw.setChecked(true);
        noticeSw.setChecked(true);
        alertSw.setChecked(true);
        alertSw.setClickable(false);
        tsunamiCb.setChecked(true);
        landslideCb.setChecked(true);
        thunderCb.setChecked(true);

        // GPSスイッチ
        gpsSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(gpsSw.isChecked()) {
                    //スイッチがOFF->ONなら
                    Toast.makeText(getApplication(), "位置情報ON", Toast.LENGTH_LONG).show();

                    //スイッチをONにした場合に起こる動作


                }else {
                    //スイッチがON->OFFなら
                    Toast.makeText(getApplication(), "位置情報OFF", Toast.LENGTH_LONG).show();

                    //スイッチをOFFにした場合に起こる動作


                }
            }
        });

        // 通知、アラートスイッチ
        noticeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(noticeSw.isChecked()) {
                    //スイッチがOFF->ONなら
                    Toast.makeText(getApplication(), "通知、アラートON", Toast.LENGTH_LONG).show();

                    //アラートスイッチと全てのチェックボックスがONになる
                    alertSw.setChecked(true);
                    tsunamiCb.setChecked(true);
                    landslideCb.setChecked(true);
                    thunderCb.setChecked(true);

                    //全てのチェックボックスが動くようになる
                    tsunamiCb.setClickable(true);
                    landslideCb.setClickable(true);
                    thunderCb.setClickable(true);

                    //スイッチをONにした場合に起こる動作


                }else {
                    //スイッチがON->OFFなら
                    Toast.makeText(getApplication(), "通知、アラートOFF", Toast.LENGTH_LONG).show();

                    //アラートスイッチと全てのチェックボックスがOFFになる
                    alertSw.setChecked(false);
                    tsunamiCb.setChecked(false);
                    landslideCb.setChecked(false);
                    thunderCb.setChecked(false);

                    //全てのチェックボックスが動かなくなる
                    tsunamiCb.setClickable(false);
                    landslideCb.setClickable(false);
                    thunderCb.setClickable(false);

                    //スイッチをOFFにした場合に起こる動作

                }
            }
        });

        tsunamiCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(tsunamiCb.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    //スイッチをONにした場合に起こる動作



                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作


                }
            }
        });

        landslideCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(landslideCb.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    //スイッチをONにした場合に起こる動作



                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作


                }
            }
        });

        thunderCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(thunderCb.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    //スイッチをONにした場合に起こる動作


                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作

                }
            }
        });

    }
}