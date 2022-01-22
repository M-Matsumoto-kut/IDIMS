package com.example.idims;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;





//災害検索アクティビティ
public class DisasterSearchActivity extends AppCompatActivity {

    private int areaNum; //地方を選択する
    private int disasterCount = 0; //災害の数が選択されている数を表す
    static final int RESULT_AREA = 1000; //getexistを使用するのに必要

    //期間設定が一度もされていないかを表す
    private boolean notSelectPeriod = true;

    public static final String SEARCHCONDITIONS_DATA = "com.example.idims.SearchConditions.java";//検索結果アクティビティに検索条件を引き渡すのに必要
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面の表示
        setContentView(R.layout.activity_disaster_search);

        //地域選択ボタンのテキスト表示
        textSetSelectAreaButton();

        //検索条件を格納するSearchConditionsの宣言
        SearchConditions search = new SearchConditions();

        //検索地域選択ボタン押下時の処理
        Button serectAreaButton = (Button) findViewById(R.id.button_SelectArea);
        serectAreaButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(DisasterSearchActivity.this, SelectAreaActivity.class);
                startActivityForResult(intent, RESULT_AREA);//地域選択画面へ移行
            }
        });


        //災害の種類のチェックボタンの定義と挙動の設定 セット関数に引き渡す
        //津波
        CheckBox checkBox_Wave = (CheckBox) findViewById(R.id.checkBox_Wave);
        checkBox_Wave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    search.setWave_On(true);
                    disasterCount++;
                }else{
                    search.setWave_On(false);
                    disasterCount--;
                }
            }
        });

        //土砂崩れ
        CheckBox checkBox_Landsride = (CheckBox) findViewById(R.id.checkBox_Landsride);
        checkBox_Landsride.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    search.setLandsride_On(true);
                    disasterCount++;
                }else{
                    search.setLandsride_On(false);
                    disasterCount--;
                }
            }
        });

        //雷
        CheckBox checkBox_Thounder = (CheckBox) findViewById(R.id.checkBox_Thounder);
        checkBox_Thounder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    search.setThounder_On(true);
                    disasterCount++;
                }else{
                    search.setThounder_On(false);
                    disasterCount--;
                }
            }
        });

        //期間設定のスピナーの設定
        //一定期間スピナー
        Spinner spinner_Constant = (Spinner) findViewById(R.id.spinner_Constant);

        //自由期間スピナー:始まり
        Spinner spinner_Free_YearStart = (Spinner) findViewById(R.id.spinner_FreeYear_Start);
        Spinner spinner_Free_MonthStart = (Spinner) findViewById(R.id.spinner_FreeMonth_Start);

        //自由期間スピナー:終わり
        Spinner spinner_Free_YearEnd = (Spinner) findViewById(R.id.spinner_FreeYear_End);
        Spinner spinner_Free_MonthEnd = (Spinner) findViewById(R.id.spinner_FreeMonth_End);

        //期間設定がどちらも選択されていない場合スピナーのクリックを無効にする
        spinner_Constant.setClickable(false);
        spinner_Free_YearStart.setClickable(false);
        spinner_Free_MonthStart.setClickable(false);
        spinner_Free_YearEnd.setClickable(false);
        spinner_Free_MonthEnd.setClickable(false);



        //期間設定の挙動の処理
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup_period);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton radio = (RadioButton)findViewById(checkedId);
                if(radio.isChecked()){//ラジオボタンのどちらかが押されている場合
                    switch(checkedId){
                        case R.id.radioButton_Constant://一定期間が押されている場合
                            //一定期間スピナーのクリックを有効にする
                            spinner_Constant.setClickable(true);
                            //自由期間スピナーのクリックを無効にする
                            spinner_Free_YearStart.setClickable(false);
                            spinner_Free_MonthStart.setClickable(false);
                            spinner_Free_YearEnd.setClickable(false);
                            spinner_Free_MonthEnd.setClickable(false);
                            //期間の取得
                            String str = spinner_Constant.getSelectedItem().toString();
                            search.settingConstant(str);
                            break;
                        case R.id.radioButton_free://自由期間が押されている場合
                            //自由期間スピナーのクリックを有効にする
                            spinner_Free_YearStart.setClickable(true);
                            spinner_Free_MonthStart.setClickable(true);
                            spinner_Free_YearEnd.setClickable(true);
                            spinner_Free_MonthEnd.setClickable(true);
                            //一定期間スピナーのクリックを無効にする
                            spinner_Constant.setClickable(false);
                            //文字列の取得
                            String startYear = spinner_Free_YearStart.getSelectedItem().toString();
                            String startMonth = spinner_Free_MonthStart.getSelectedItem().toString();
                            String endYear = spinner_Free_YearEnd.getSelectedItem().toString();
                            String endMonth = spinner_Free_MonthEnd.getSelectedItem().toString();
                            search.settingFree(startYear, startMonth, endYear, endMonth);
                            break;
                    }
                    //期間選択がされた
                    notSelectPeriod = false;
                }
            }
        });


        //検索結果表示ボタン押下時の処理
        Button resultButton = (Button) findViewById(R.id.button_Search);
        resultButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //検索条件を満たさない場合エラーを起こす
                if(areaNum > 7 || areaNum < 1){ //地域が選択されていない場合
                    TextView textView = (TextView) findViewById(R.id.textView_Error);
                    textView.setText("地域を選択してください");
                }else if(disasterCount < 1){ //災害の種類が一つも選択されていない場合
                    TextView textView = (TextView) findViewById(R.id.textView_Error);
                    textView.setText("災害を1種類以上選んでください");
                }else if(notSelectPeriod){ //期間設定がどちらもされていない場合
                    TextView textView = (TextView) findViewById(R.id.textView_Error);
                    textView.setText("どちらかの期間を選んでください");
                }else if(overTime(search.startDate, search.endDate) == 0){ //自由設定期間が正しく設定されていない場合
                    TextView textView = (TextView) findViewById(R.id.textView_Error);
                    textView.setText("開始時刻が終了時刻より先にならないよう設定してください");
                }else{
                    //期間選択を検索し、期間を返す
                    group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                             RadioButton radio = (RadioButton) findViewById(checkedId);
                                                             if (radio.isChecked()) {//ラジオボタンのどちらかが押されている場合
                                                                 switch (checkedId) {
                                                                     case R.id.radioButton_Constant://一定期間が押されている場合
                                                                         //期間の取得
                                                                         String str = spinner_Constant.getSelectedItem().toString();
                                                                         search.settingConstant(str);
                                                                         break;
                                                                     case R.id.radioButton_free://自由期間が押されている場合
                                                                         //文字列の取得
                                                                         String startYear = spinner_Free_YearStart.getSelectedItem().toString();
                                                                         String startMonth = spinner_Free_MonthStart.getSelectedItem().toString();
                                                                         String endYear = spinner_Free_YearEnd.getSelectedItem().toString();
                                                                         String endMonth = spinner_Free_MonthEnd.getSelectedItem().toString();
                                                                         search.settingFree(startYear, startMonth, endYear, endMonth);
                                                                         break;
                                                                 }
                                                             }
                                                         }
                                                     });
                    //検索結果画面へ移動
                    Intent intent = new Intent(DisasterSearchActivity.this, SearchResultListActivity.class);
                    //検索結果の条件を一つずつ引き渡す
                    intent.putExtra("AreaNumber", areaNum);
                    intent.putExtra("Wave", search.getWave_On());
                    intent.putExtra("Landsride", search.getLandsride_On());
                    intent.putExtra("Thounder", search.getThounder_On());
                    intent.putExtra("startTime", search.getStartDate());
                    intent.putExtra("endTime", search.getEndDate());
                    startActivity(intent);
                }

            }
        });
    }

    //地域選択アクティビティから結果を受け取る
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK && requestCode == RESULT_AREA && intent != null){
            areaNum = intent.getIntExtra("areaNumber", 0);
            textSetSelectAreaButton();
        }
    }

    //検索範囲の開始日時が終了日時より未来の場合0を返す
    protected int overTime(String startTime, String endTime){
        if(startTime == null || endTime == null){
            return 1;
        }
        int sTime = Integer.parseInt(startTime);
        int eTime = Integer.parseInt(endTime);
        if(sTime > eTime){
            return 0;
        }
        return 1;
    }

    //現在の地域選択状況をボタンに設定する
    protected void textSetSelectAreaButton(){
        Button button = (Button) findViewById(R.id.button_SelectArea);
        if(areaNum == 1){
            button.setText(getString(R.string.Hokkaido));
        }else if(areaNum == 2){
            button.setText(getString(R.string.Touhoku));
        }else if(areaNum == 3){
            button.setText(getString(R.string.Kantou));
        }else if(areaNum == 4){
            button.setText(getString(R.string.Chubu));
        }else if(areaNum == 5){
            button.setText(getString(R.string.Kinki));
        }else if(areaNum == 6){
            button.setText(getString(R.string.ChugokuShikoku));
        }else if(areaNum == 7){
            button.setText(getString(R.string.Kyushu));
        }else{
            button.setText(getString(R.string.selectArea));
        }

    }


}