package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//災害検索アクティビティ
public class DisasterSearchActivity extends AppCompatActivity {

    int areaNum = 0;
    static final int RESULT_AREA = 1000;
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
                startActivityForResult(intent, RESULT_AREA);//地域選択画面へ移行
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

        //期間設定のスピナーの設定
        //一定期間スピナー
        Spinner spinner_Constant = (Spinner) findViewById(R.id.spinner_Constant);
        //adapterクラスを利用して作成した文字配列をspinnerのリソースにする 以下のスピナーにも同様の処理を行う
        ArrayAdapter<CharSequence> adapter_Constant = ArrayAdapter.createFromResource(this, R.array.spinner_Constant, R.layout.activity_disaster_search);
        adapter_Constant.setDropDownViewResource(R.layout.activity_disaster_search);
        spinner_Constant.setAdapter(adapter_Constant);

        //自由期間スピナー:始まり
        Spinner spinner_Free_YearStart = (Spinner) findViewById(R.id.spinner_FreeYear_Start);
        Spinner spinner_Free_MonthStart = (Spinner) findViewById(R.id.spinner_FreeMonth_Start);

        //自由期間スピナー:終わり
        Spinner spinner_Free_YearEnd = (Spinner) findViewById(R.id.spinner_FreeYear_End);
        Spinner spinner_Free_MonthEnd = (Spinner) findViewById(R.id.spinner_FreeMonth_End);

        //自由期間のスピナーにadapterを当てる
        ArrayAdapter<CharSequence> adapter_FreeYear = ArrayAdapter.createFromResource(this, R.array.spinner_Year, R.layout.activity_disaster_search);
        adapter_FreeYear.setDropDownViewResource(R.layout.activity_disaster_search);
        ArrayAdapter<CharSequence> adapter_FreeMonth = ArrayAdapter.createFromResource(this, R.array.spinner_Month, R.layout.activity_disaster_search);
        adapter_FreeMonth.setDropDownViewResource(R.layout.activity_disaster_search);
        spinner_Free_YearStart.setAdapter(adapter_FreeYear);
        spinner_Free_MonthStart.setAdapter(adapter_FreeMonth);
        spinner_Free_YearEnd.setAdapter(adapter_FreeYear);
        spinner_Free_MonthEnd.setAdapter(adapter_FreeMonth);

        //期間設定の挙動の処理
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup_period);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton radio = (RadioButton)findViewById(checkedId);
                if(radio.isChecked() == true){//ラジオボタンのどちらかが押されている場合
                    switch(checkedId){
                        case R.id.radioButton_Constant://一定期間が押されている場合
                            String str = spinner_Constant.getSelectedItem().toString();//期間の取得
                            search.settingConstant(str);
                            break;
                        case R.id.radioButton_free://自由期間が押されている場合
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


        //検索結果表示ボタン押下時の処理
        Button resultButton = (Button) findViewById(R.id.button_Search);
        resultButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //検索条件を満たさない場合エラーを起こす
                //地域が選択されていない場合
                if(areaNum == 0){
                    TextView textView = (TextView) findViewById(R.id.textView_Error);
                    textView.setText("地域を選択してください");
                    break;
                }

                //災害の種類が一つも選択されていない場合
                //自由設定期間が正しく設定されていない場合
                //検索結果画面へ移動
                Connection con = null;
                try{
                    con = DriverManager.getConnection(jdbc:mysql://);

                    Statement state = con.createStatement();
                    String sql =
                }
                Intent intent = new Intent(DisasterSearchActivity.this, SearchResultListActivity.class);
                startActivity(intent);

            }
        });
    }

    //地域選択アクティビティから結果を受け取る
    protected void OnActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK && requestCode == RESULT_AREA && intent != null){
            areaNum = intent.getIntExtra("areaNumber", 0);
        }
    }


}