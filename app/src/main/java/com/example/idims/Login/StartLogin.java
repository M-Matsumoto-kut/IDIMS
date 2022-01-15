package com.example.idims.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.idims.R;
import com.example.idims.Researcher.ResearcherPassword;
import com.example.idims.Researcher.ResearcherPage;
import com.example.idims.Menu.Menu;

public class StartLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userTypeSelect();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    //ユーザタイプ選択
    private void userTypeSelect(){
        //ユーザタイプ選択画面生成
        setContentView(R.layout.usertype_serect);

        //一般
        Button generalUser = findViewById(R.id.generalType);
        generalUser.setOnClickListener( v -> {
            //メニュー画面に移行
            Intent intent = new Intent(getApplication(), Menu.class);
            startActivity(intent);
        });

        //研究者
        Button researchUser = findViewById(R.id.researcherType);
        generalUser.setOnClickListener( v -> {
            //研究者ユーザログイン画面に移行
            Intent intent = new Intent(getApplication(), ResearcherPassword.class);
            startActivity(intent);
        });

        //戻るボタン
        Button backButton = findViewById(R.id.backactivity);
        backButton.setOnClickListener( v -> {
                finish(); //Activityを終了する（前の画面に戻る)
        });
    }
}

