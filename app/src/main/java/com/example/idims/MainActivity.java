package com.example.idims;

import android.os.Bundle;

import com.example.idims.Area.AreaListActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import com.example.idims.Researcher.ResearcherPage;
import com.example.idims.Researcher.SensorListActivity;

import android.widget.Button;

//起動時
public class MainActivity extends AppCompatActivity {

    //なにこれ
    //private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;

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

        //loginType（ログイン状態）を取得
        StatusFlag flag = (StatusFlag) this.getApplication();
        int loginType = flag.getLoginType();


        //loginTypeの値によってActivityを変える（予定）
        Intent intent;
        switch(loginType) {
            case 0: //初回ログイン
                this.userTypeSelect();
                break;
            case 1: //２回目以降のログイン(一般ユーザ）
                intent = new Intent(getApplication(), HomeActivity.class);
                startActivity(intent);
                break;
            case 2: //２回目以降のログイン(一般ユーザ）
                intent = new Intent(getApplication(), ResearcherPage.class);
                startActivity(intent);
                break;
            default: //エラー
            intent = new Intent(getApplication(), Error.class);
            startActivity(intent);

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
            Intent intent = new Intent(getApplication(), Setting.class);
            startActivity(intent);
             */

            //テスト-------------------------------------------------------
            Intent intent = new Intent(getApplication(), SensorListActivity.class);
            startActivity(intent);
            //テスト-------------------------------------------------------
        });


        //研究者
        Button researchUser = findViewById(R.id.researcherType);
        researchUser.setOnClickListener( v -> {
            //研究者ユーザログイン画面に移行
            Intent intent = new Intent(getApplication(), AreaListActivity.class);
            startActivity(intent);
        });
    }

}