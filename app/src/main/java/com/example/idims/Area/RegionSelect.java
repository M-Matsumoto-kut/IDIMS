package com.example.idims.Area;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.R;

//地方選択画面モジュール
public class RegionSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.regtionSelect();
    }

    //地方選択画面モジュール
    private void regtionSelect() {

        //地方選択画面表示
        setContentView(R.layout.activity_region_select);


        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> {
            finish();
        });


        //リストのボタンが押された時

    }


}
