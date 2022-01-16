package com.example.idims.Area;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.R;
import com.example.idims.StatusFlag;

import java.util.Locale;

//地方選択画面モジュール
public class RegionSelect extends AppCompatActivity {
    private StatusFlag status;

    private final String[] regions = {"北海道", "東北地方", "関東地方", "中部地方", "近畿地方",
            "中国地方", "四国地方", "九州地方"};

    private int addSel;
    private ViewGroup Linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (StatusFlag) getApplication();

    }

    @Override
    protected void onStart() {
        super.onStart();
        addSel = 99;
        this.regionSelect();
    }

    //地方選択
    private void regionSelect(){

        LinearLayout layout = new LinearLayout(this);

        //地方選択画面表示
        setContentView(R.layout.activity_region_select);

        //地方選択ボタンを配置
        for(int i=0; i < 8; i++) {
            //ボタン生成
            Button btn = new Button(this);

            //地方名表示
            btn.setText(regions[i]);

            LinearLayout.LayoutParams buttonLayoutParams =
                    new LinearLayout.LayoutParams(100, 100);

            buttonLayoutParams.setMargins(0, 0, 0, 0);

            btn.setLayoutParams(buttonLayoutParams);
            layout.addView(btn);

            //地方選択ボタンが押された時
            btn.setOnClickListener( v -> {
                //選択したボタンに対応する地方をStatusFlagに記録
                //status.setAddSel(i);

                if(addSel != 99) {

                }
                //都道府県選択画面へ移行
                Intent intent = new Intent(getApplication(), PrefectureSelect.class);
                startActivity(intent);
            });


        }
    }
}
