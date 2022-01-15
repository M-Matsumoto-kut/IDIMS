package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.idims.R;

/*
    センサー確認モジュール
 */
public class Sensor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //データベースからセンサー情報を取得し,表示
        setContentView(R.layout.activity_sensor);

        //戻るボタン
        Button backButton = findViewById(R.id.backactivity);
        backButton.setOnClickListener( v -> {
            finish();
        });




    }
}