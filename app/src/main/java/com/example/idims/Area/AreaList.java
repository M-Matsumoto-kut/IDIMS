package com.example.idims.Area;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.MainActivity;
import com.example.idims.R;

//閲覧地域一覧モジュール
public class AreaList extends AppCompatActivity {

    int region;
    int prefecture;

    //初動作時に実行
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //インスタンス初期化
        region = 0;
        prefecture = 0;

    }

    //毎動作時に実行
    @Override
    protected void onStart() {
        super.onStart();
        this.regionList();
    }

    //閲覧地域一覧モジュール
    private void regionList() {

        //閲覧地域一覧画面生成
        setContentView(R.layout.activity_area_list);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> finish());

        /* 追加ボタンが押された時→地方地域選択画面に移行 */
        Button addButton = findViewById(R.id.addArea);
        addButton.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), RegionSelect.class);
            startActivity(intent);
        });

    }

}
