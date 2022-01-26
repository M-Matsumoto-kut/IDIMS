package com.example.idims;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
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
    private StatusFlag status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (StatusFlag) getApplication();

        setContentView(R.layout.activity_main);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener(v -> {
            finish();
        });

        //各種スイッチ、チェックボックス
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch gpsSw = findViewById(R.id.gpsSwitch);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch noticeSw = findViewById(R.id.noticeSwitch);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch alertSw = findViewById(R.id.alertSwitch);
        CheckBox tsunamiCb = findViewById(R.id.tsunamiCheckBox);
        CheckBox landslideCb = findViewById(R.id.landslideCheckBox);
        CheckBox thunderCb = findViewById(R.id.thunderCheckBox);

        // 各種スイッチ、チェックボックスのデフォルト
        gpsSw.setChecked(false);
        noticeSw.setChecked(false);

        alertSw.setChecked(false);
        alertSw.setClickable(false);

        tsunamiCb.setChecked(false);
        landslideCb.setChecked(false);
        thunderCb.setChecked(false);

        //災害の種類は通知がオフのとき操作できない（デフォルト）
        tsunamiCb.setClickable(false);
        landslideCb.setClickable(false);
        thunderCb.setClickable(false);

        // GPSスイッチ
        gpsSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(gpsSw.isChecked()) {
                    //スイッチがOFF->ONなら
                    Toast.makeText(getApplication(), "位置情報ON", Toast.LENGTH_LONG).show();

                    status.setGpsPermission(1);

                    /*
                    //スイッチをONにした場合に起こる動作
                    ActivityResultLauncher<String[]> locationPermissionRequest =
                            registerForActivityResult(new ActivityResultContracts()
                                            .RequestMultiplePermissions(), result -> {
                                        Boolean fineLocationGranted = result.getOrDefault(
                                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                                        Boolean coarseLocationGranted = result.getOrDefault(
                                                Manifest.permission.ACCESS_COARSE_LOCATION,false);

                                        if (fineLocationGranted != null && fineLocationGranted) {
                                            // 正確な位置情報アクセスを許可する．
                                            Toast.makeText(getApplication(), "位置情報ON", Toast.LENGTH_LONG).show();
                                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                            // おおよその位置情報アクセスのみが許可されます。
                                            Toast.makeText(getApplication(), "位置情報ON", Toast.LENGTH_LONG).show();
                                        } else {
                                            // 位置情報アクセスは許可されません。
                                            Toast.makeText(getApplication(), "位置情報OFF", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            );

                    locationPermissionRequest.launch(new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    });

                     */


                }else {
                    //スイッチがON->OFFなら
                    Toast.makeText(getApplication(), "位置情報OFF", Toast.LENGTH_LONG).show();

                    //スイッチをOFFにした場合に起こる動作
                    status.setGpsPermission(0);

                }
            }
        });

        // 通知
        noticeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(noticeSw.isChecked()) {
                    //スイッチがOFF->ONなら
                    Toast.makeText(getApplication(), "通知ON", Toast.LENGTH_LONG).show();

                    //スイッチをONにした場合に起こる動作
                    status.setNoticePermission(1);

                    //「災害の種類」のチェックボックスがONになる
                    tsunamiCb.setChecked(true);
                    landslideCb.setChecked(true);
                    thunderCb.setChecked(true);

                    //アラートスイッチ，全てのチェックボックスが動くようになる
                    alertSw.setClickable(true);
                    tsunamiCb.setClickable(true);
                    landslideCb.setClickable(true);
                    thunderCb.setClickable(true);



                }else {
                    //スイッチがON->OFFなら
                    Toast.makeText(getApplication(), "通知OFF", Toast.LENGTH_LONG).show();

                    //スイッチをOFFにした場合に起こる動作
                    status.setNoticePermission(0);

                    //アラートスイッチと全てのチェックボックスがOFFになる
                    alertSw.setChecked(false);
                    tsunamiCb.setChecked(false);
                    landslideCb.setChecked(false);
                    thunderCb.setChecked(false);

                    //アラートスイッチ，「災害の種類」のチェックボックスが動かなくなる
                    alertSw.setClickable(false);
                    tsunamiCb.setClickable(false);
                    landslideCb.setClickable(false);
                    thunderCb.setClickable(false);

                }
            }
        });

        //アラートのオン/オフ処理
        alertSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(alertSw.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    Toast.makeText(getApplication(), "アラートON", Toast.LENGTH_LONG).show();

                    //スイッチをONにした場合に起こる動作
                    status.setAlertPermission(1);

                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作
                    status.setAlertPermission(0);

                }
            }
        });

        //「津波」に関する情報の取得の切り替え
        tsunamiCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(tsunamiCb.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    Toast.makeText(getApplication(), "アラートOFF", Toast.LENGTH_LONG).show();

                    //スイッチをONにした場合に起こる動作
                    status.setTsunamiStatus(1);

                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作
                    status.setTsunamiStatus(0);

                }
            }
        });

        //「土砂崩れ」に関する情報の取得の切り替え
        landslideCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(landslideCb.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    //スイッチをONにした場合に起こる動作
                    status.setLandslideStatus(1);

                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作
                    status.setLandslideStatus(0);

                }
            }
        });

        //「雷」に関する情報の取得の切り替え
        thunderCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(thunderCb.isChecked()) {
                    //チェックボックスがOFF->ONなら
                    //スイッチをONにした場合に起こる動作
                    status.setThunderStatus(1);

                }else {
                    //チェックボックスがON->OFFなら
                    //チェックボックスをOFFにした場合に起こる動作
                    status.setThunderStatus(0);

                }
            }
        });
    }
}