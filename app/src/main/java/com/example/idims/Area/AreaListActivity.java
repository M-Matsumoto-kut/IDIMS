package com.example.idims.Area;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.MainActivity;
import com.example.idims.R;
import com.example.idims.StatusFlag;

//閲覧地域一覧モジュール
public class AreaListActivity extends AppCompatActivity {

    private StatusFlag status;
    private int areaNum;
    private String areaName;
    private int textHeight;

    //初動作時に実行
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (StatusFlag) getApplication();

        //インスタンス初期化
        areaNum = 0;
        textHeight = 0;
        areaName = "";
    }

    //毎動作時に実行
    @Override
    protected void onStart() {
        super.onStart();
        this.regionListActivity();
    }

    //閲覧地域一覧を表示
    private void regionListActivity() {

        //レイアウト生成
        setContentView(R.layout.activity_area_list);

        //戻るボタン->設定画面
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        });

        /* 追加ボタンが押された時→地方地域選択画面に移行 */
        Button addButton = findViewById(R.id.addArea);
        addButton.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), RegionSelectActivity.class);
            startActivity(intent);
        });


        TextView[] areaView = new TextView[47];

        float dp = getResources().getDisplayMetrics().density;
        textHeight = (int)(60 * dp);


        LinearLayout linearLayout = findViewById(R.id.AreaListLinear);
        linearLayout.setGravity(Gravity.TOP);


        //地域ボタンを一覧表示する(現在は8件まで）
        for(int i = 0; i < 8; i++) {
            areaNum = status.getPrefectureNum(i);

            if(areaNum > 46) {
                break;
            } else {
                //格納したprefectureNumをもとに都道府県の名前を取得
                areaName = status.getPrefectureName(areaNum);

                //表示
                areaView[i] = new TextView(this);
                areaView[i].setTextColor(0xffffffff);
                areaView[i].setTextSize(10 * dp);

                if (i % 2 == 1) {
                    areaView[i].setBackgroundColor(0xFF575757);
                } else {
                    areaView[i].setBackgroundColor(0xFF454545);
                }

                areaView[i].setText(areaName);
                areaView[i].setGravity(Gravity.CENTER);
                linearLayout.addView(areaView[i],
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                textHeight));
            }
        }



        //削除ボタンを押したら，各地域ごとに削除ボタンを生成
        Button deleteButton = findViewById(R.id.deleteArea);
        deleteButton.setOnClickListener( v -> {
            Button[] button = new Button[47];
        });
    }
}
