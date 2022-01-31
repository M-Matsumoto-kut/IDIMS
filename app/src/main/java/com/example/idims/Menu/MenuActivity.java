package com.example.idims.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.idims.Area.AreaListActivity;
import com.example.idims.DisasterSearchActivity;
import com.example.idims.HomeActivity;
import com.example.idims.R;

import com.example.idims.Researcher.ResearcherLogin;
import com.example.idims.Researcher.ResearcherPageActivity;
import com.example.idims.Setting;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //メニュー画面を閉じるボタン
        Button closeMenu = findViewById(R.id.buttonToCloseMenuPage);
        closeMenu.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);
        });

        //災害通知地域一覧へ遷移するボタン
        Button ToListSetArea = findViewById(R.id.buttonToListSetAreaPage);
        ToListSetArea.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), AreaListActivity.class);
            startActivity(intent);
        });

        //設定画面へ遷移するボタン
        Button toSetting = findViewById(R.id.buttonToSettingPage);
        toSetting.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), Setting.class);
            startActivity(intent);
        });

        //災害情報検索画面へ遷移するボタン
        Button toArchive = findViewById(R.id.buttonToArchivePage);
        toArchive.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), DisasterSearchActivity.class);
            startActivity(intent);
        });

        //研究者ページへ遷移するボタン
        Button toResearcherPage = findViewById(R.id.buttonToResearcherPage);
        toResearcherPage.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), ResearcherLogin.class);
            startActivity(intent);
        });


    }
}