package com.example.idims.Researcher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.Area.AreaListActivity;
import com.example.idims.Authenticate;
import com.example.idims.MainActivity;
import com.example.idims.Menu.MenuActivity;
import com.example.idims.R;
import com.example.idims.Setting;
import com.example.idims.StatusFlag;

//研究者ログインページモジュール
public class ResearcherLogin extends AppCompatActivity {
    EditText userId;        //id
    EditText password;      //現在のパスワード
    private StatusFlag status;

    int userIdInt;          //整数値に変換されたid
    String passwordStr;     //文字列に変換されたpassword

    ImageView eye1;   //パスワードの表示・非表示
    boolean state = false;  //表示・非表示の切り替えステータス

    private Authenticate authenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.authenticate = new Authenticate();
        this.status = (StatusFlag) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ///*
        int loginType = status.getLoginType();
        if(loginType == 2) {
            //研究者ページに移行
            Intent intent = new Intent(ResearcherLogin.this, ResearcherPageActivity.class);
            startActivity(intent);
        }
         //*/
        this.loginResearcher(0);
    }

    //ログイン
    private void loginResearcher(int error){
        //userID/パスワード入力画面生成
        setContentView(R.layout.activity_researcher_login);

        //userIDとパスワードを紐付け
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);

        //eyeとIDを紐付け
        eye1 = findViewById(R.id.toggle_view1);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> {

            int activityNum = status.getActivityStatus();
            if(activityNum == 1) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            } else if(activityNum == 2) {
                Intent intent = new Intent(getApplication(), MenuActivity.class);
                startActivity(intent);
            }
        });

        //エラーテキスト表示
        if(error == 1) {
            TextView errorView = (TextView) findViewById(R.id.errorText);
            String errorMessage = "IDまたはパスワードが間違っています";
            errorView.setText(errorMessage);
        }

        //ログインボタンが押された時
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener( v -> {

            //IDを文字列に変換

            String idStr = String.valueOf(userId.getText());

            //入力したパスワードを文字列に変換
            passwordStr = password.getText().toString();

            Log.i("id4",idStr);
            //userIdとPasswordが正しいか
            if(authenticate.passAuthenticate(idStr, passwordStr)) {

                StatusFlag flag = (StatusFlag) getApplication();
                flag.setLoginTypeRes();         //loginTypeを2（研究者）に更新
                flag.setId(idStr);          //IDをを保存し，自動ログインを実現
                authenticate.password = "";

                int activityNum = status.getActivityStatus();
                if(activityNum == 1) {
                    //設定画面に移動
                    Intent intent = new Intent(ResearcherLogin.this, Setting.class);
                    startActivity(intent);
                } else if(activityNum == 2) {
                    //研究者ページ画面に移動
                    Intent intent = new Intent(ResearcherLogin.this, ResearcherPageActivity.class);
                    startActivity(intent);
                }
            } else {
                //再入力を求める
                this.loginResearcher(1);
            }
        });
    }

    //パスワードの表示・非表示
    public void toggle1(View v){
        if(!state){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
            eye1.setImageResource(R.drawable.eye1);
        }
        else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
            eye1.setImageResource(R.drawable.eye_off1);
        }
        state = !state;
    }

}
