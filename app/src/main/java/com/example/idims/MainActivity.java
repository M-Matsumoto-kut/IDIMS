package com.example.idims;

import android.os.Bundle;

import com.example.idims.Researcher.ResearcherLogin;
import com.example.idims.Researcher.ResearcherPage;
import com.example.idims.Researcher.ResearcherPassword;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;

import com.example.idims.Researcher.Sensor;
import com.example.idims.databinding.ActivityMainBinding;

import android.widget.Button;

//起動時
public class MainActivity extends AppCompatActivity {

    //なにこれ
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status（Activity状態）を取得
        StatusFlag flag = (StatusFlag) getApplication();
        this.userTypeSelect();
    }

    /*
        開始状態になると動作
        初起動時，flagはLoginStart(1)を返す
     */
    @Override
    protected void onStart(){
        super.onStart();

        //status（Activity状態）を取得
        StatusFlag flag = (StatusFlag) this.getApplication();
        int status = flag.getActivityStatus();

        /*
        statusの値によってActivityを変える（予定）
         */
        switch(status) {
            case 1: //初回ログイン
                this.userTypeSelect();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            default:
        }
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
            flag.setLoginTypeGen();

            /*
                設定画面に移行
                ただし，自動ログイン機能が実現できない場合はメニュー画面に移行
             */

            /*
            Intent intent = new Intent(getApplication(), UserSetUp.class);
            startActivity(intent);
             */

            //テスト-------------------------------------------------------
            Intent intent = new Intent(getApplication(), ResearcherPage.class);
            startActivity(intent);
            //テスト-------------------------------------------------------
        });


        //研究者
        Button researchUser = findViewById(R.id.researcherType);
        researchUser.setOnClickListener( v -> {
            //研究者ユーザログイン画面に移行
            Intent intent = new Intent(getApplication(), ResearcherLogin.class);
            startActivity(intent);
        });
    }

}