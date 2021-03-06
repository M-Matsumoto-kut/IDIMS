package com.example.idims;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.Area.AreaListActivity;
import com.example.idims.Researcher.ResearcherLogin;
import com.example.idims.Researcher.SensorListActivity;
import com.example.idims.StatusFlag;

//起動時
public class MainActivity extends AppCompatActivity {

    private StatusFlag status;

    //なにこれ
    //private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (StatusFlag) getApplication();

        //loginType（ログイン状態）を取得
        int loginType = status.getLoginType();

        //loginTypeの値によってActivityを変える（予定）
        Intent intent;
        if(loginType == 0) {
            this.userTypeSelect();
        } else {
            intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStart(){
        super.onStart();

        //災害検知モジュールを起動
        /*
        Asynchronous r = new Asynchronous();
        Thread t = new Thread(r); // Runnableをスレッドに渡してインスタンスを生成する
        t.start();
         */

        //loginType（ログイン状態）を取得
        int loginType = status.getLoginType();
        //loginTypeの値によってActivityを変える（予定）
        Intent intent;
        if(loginType == 0) {
            this.userTypeSelect();
        } else {
            intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);
        }
        this.userTypeSelect();
    }

    //ユーザタイプ選択
    private void userTypeSelect(){
        //ユーザタイプ選択画面生成
        setContentView(R.layout.activity_usertype_select);

        //一般人
        Button generalUser = findViewById(R.id.generalType);
        generalUser.setOnClickListener( v -> {


            //loginTypeを1(一般人)に設定
            StatusFlag flag = (StatusFlag) getApplication();
            status.setLoginTypeGen();

            Intent intent = new Intent(MainActivity.this, Setting.class);
            startActivity(intent);

        });


        //研究者
        Button researchUser = findViewById(R.id.researcherType);
        researchUser.setOnClickListener( v -> {
            //研究者ユーザログイン画面に移行
            status.setActivityStatus(1); //userTypeActivity
            Intent intent = new Intent(MainActivity.this, ResearcherLogin.class);
            startActivity(intent);
        });
    }
}