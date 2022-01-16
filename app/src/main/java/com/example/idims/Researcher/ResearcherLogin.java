package com.example.idims.Researcher;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.Authenticate;
import com.example.idims.R;
import com.example.idims.StatusFlag;
import com.example.idims.UserSetUp;

//研究者ログインページモジュール
public class ResearcherLogin extends AppCompatActivity {
    EditText userId;        //id
    EditText password;      //現在のパスワード

    int userIdInt;          //整数値に変換されたid
    String passwordStr;     //文字列に変換されたpassword

    ImageView eye1, eye2;   //パスワードの表示・非表示
    boolean state = false;  //表示・非表示の切り替えステータス

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loginResearcher();

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        StatusFlag flag = (StatusFlag) this.getApplication();
        int loginType = flag.getLoginType();
        if(loginType == 2) {
            //研究者ページに移行
            Intent intent = new Intent(ResearcherLogin.this, ResearcherPage.class);
            startActivity(intent);
        }
         */

        //this.loginResearcher();
    }

    //ログイン
    private void loginResearcher(){
        //userID/パスワード入力画面生成
        setContentView(R.layout.activity_researcher_login);

        //userIDとパスワードを紐付け
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);

        //eyeとIDを紐付け
        eye1 = findViewById(R.id.toggle_view1);
        eye2 = findViewById(R.id.toggle_view2);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> finish());


        //ログインボタンが押された時


        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener( v -> {


            //入力したIDを数値に変換
            userIdInt = Integer.parseInt(String.valueOf(userId.getText()));

            //入力したパスワードを文字列に変換
            passwordStr = password.getText().toString();

            //userIdとPasswordが正しいか

            if(Authenticate.loginAuthenticate(userIdInt, passwordStr)) {

                    //loginTypeを2（研究者）に更新
                    //IDをを保存し，自動ログインを実現

                StatusFlag flag = (StatusFlag) getApplication();
                flag.setLoginTypeRes();
                flag.setId(userIdInt);

                //設定画面に移動
                Intent intent = new Intent(getApplication(), UserSetUp.class);
                startActivity(intent);
            } else {
                //再入力を求める
                Intent intent = new Intent(getApplication(), ResearcherLogin.class);
                startActivity(intent);

            }


            //テスト----------------------------------------
            Intent intent = new Intent(getApplication(), ResearcherPage.class);
            startActivity(intent);
            //テスト----------------------------------------
        });

    }

    //パスワードの表示・非表示
    public void toggle1(View v){
        if(!state){
            userId.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            userId.setSelection(userId.getText().length());
            eye1.setImageResource(R.drawable.eye1);

        }
        else{
            userId.setTransformationMethod(PasswordTransformationMethod.getInstance());
            userId.setSelection(userId.getText().length());
            eye1.setImageResource(R.drawable.eye_off1);
        }
        state = !state;
    }

    public void toggle2(View v){
        if(!state){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
            eye2.setImageResource(R.drawable.eye2);

        }
        else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
            eye2.setImageResource(R.drawable.eye_off2);
        }
        state = !state;
    }

}
