package com.example.idims.Researcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.Menu.MenuActivity;
import com.example.idims.R;
import com.example.idims.StatusFlag;

/*
    研究者ページモジュール
 */
public class ResearcherPageActivity extends AppCompatActivity {
    private StatusFlag status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (StatusFlag) getApplication();
    }

    //毎動作時に実行
    @Override
    protected void onStart() {
        super.onStart();
        /*
        研究者ユーザなら研究者ページへ
        そうでなければ研究者ログイン画面へ移行
         */
        int loginType = status.getLoginType();
        if(loginType == 2) {
            this.ResearcherPage();
        } else {
            Intent intent = new Intent(ResearcherPageActivity.this, ResearcherLogin.class);
            startActivity(intent);
        }
    }

    private void ResearcherPage() {
        setContentView(R.layout.activity_researcherpage);

        //センサー異常の画面に移動
        Button button1 = findViewById(R.id.next1Activity);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ResearcherPageActivity.this, SensorListActivity.class);
                startActivity(intent);
            }
        });

        //パスワード変更の画面に移動
        Button button2 = findViewById(R.id.next2Activity);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ResearcherPageActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        //戻るボタン　
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResearcherPageActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}