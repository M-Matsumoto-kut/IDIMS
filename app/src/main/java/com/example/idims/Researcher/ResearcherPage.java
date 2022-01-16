package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.idims.R;

/*
    研究者ページモジュール
 */
public class ResearcherPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_researcherpage);

        //センサー異常の画面に移動
        Button button1 = findViewById(R.id.next1activity);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ResearcherPage.this, Sensor.class);
                startActivity(intent);
            }
        });

        //パスワード変更の画面に移動
        Button button2 = findViewById(R.id.next2activity);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ResearcherPage.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        //戻るボタン　
        Button backButton = findViewById(R.id.backactivity);
        backButton.setOnClickListener( v -> {
            finish();
        });

    }
}