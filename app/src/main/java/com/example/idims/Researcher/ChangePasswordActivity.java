package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.idims.Authenticate;
import com.example.idims.R;

/*
    パスワード更新モジュール
 */
public class ChangePasswordActivity extends AppCompatActivity {

    EditText nowPassword; //現在のパスワード
    EditText newPassword; //現在のパスワード

    Authenticate authenticate;

    String nowPasswordStr, newPasswordStr;

    ImageView eye1, eye2; //パスワードの表示・非表示

    boolean state = false;//表示・非表示の切り替えステータス

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticate = new Authenticate();
        this.passwordChange();
    }
    /*
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "パスワードを変更しました",  Snackbar.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ResearcherPassword.this, ResearcherPage.class);
                        startActivity(intent);
                    }
                }, 1000);
            }
     */

    private void passwordChange(){
        //パスワード変更画面生成
        setContentView(R.layout.activity_researcherpassword);

        //現在のパスワードと新しいパスワードを紐付け
        nowPassword = findViewById(R.id.nowPassword);
        newPassword = findViewById(R.id.newPassword);

        //eyeとIDを紐付け
        eye1 = findViewById(R.id.toggle_view1);
        eye2 = findViewById(R.id.toggle_view2);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> {
            Intent intent = new Intent(ChangePasswordActivity.this, ResearcherPageActivity.class);
            startActivity(intent);
        });

        //更新ボタンが押された時
        findViewById(R.id.changePassword).setOnClickListener( v -> {

            //入力したパスワードを文字列に変換
            nowPasswordStr = nowPassword.getText().toString();
            newPasswordStr = newPassword.getText().toString();

            //フラグからIDを取得
            int id = 1230344;

            //現在のパスワードが正しいか
            if(authenticate.nowPasswordAuthenticate(id, nowPasswordStr)) {

                //現在のパスワードが半角英数字でかつ8文字以上16文字以下か
                if(Authenticate.newPasswordAuthenticate(newPasswordStr)){
                    //データベースにアクセスし，パスワードを更新


                    //研究者ページに戻る
                    Intent intent = new Intent(ChangePasswordActivity.this, ResearcherPageActivity.class);
                    startActivity(intent);
                } else {
                    //再入力を求める
                    this.passwordChange();
                }

            } else {
                //再入力を求める
                this.passwordChange();
            }

            //test-------------------------------------------------------------
            Intent intent = new Intent(ChangePasswordActivity.this, ResearcherPageActivity.class);
            startActivity(intent);
            //test-------------------------------------------------------------
        });
    }

    //パスワードの表示・非表示
    public void toggle1(View v){
        if(!state){
            nowPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            nowPassword.setSelection(nowPassword.getText().length());
            eye1.setImageResource(R.drawable.eye1);

        }
        else{
            nowPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            nowPassword.setSelection(nowPassword.getText().length());
            eye1.setImageResource(R.drawable.eye_off1);
        }
        state = !state;
    }

    public void toggle2(View v){
        if(!state){
            newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            newPassword.setSelection(newPassword.getText().length());
            eye2.setImageResource(R.drawable.eye2);

        }
        else{
            newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            newPassword.setSelection(newPassword.getText().length());
            eye2.setImageResource(R.drawable.eye_off2);
        }
        state = !state;
    }

}