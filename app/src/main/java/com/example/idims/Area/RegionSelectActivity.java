package com.example.idims.Area;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.StatusFlag;

import java.util.Locale;

//地方選択画面モジュール
public class RegionSelectActivity extends AppCompatActivity {
    private TextView textView;
    private StatusFlag status;

    private final String[] regions = {"北海道地方", "東北地方", "関東地方", "中部地方", "近畿地方",
            "中国地方", "四国地方", "九州地方"};

    private int addSel;
    private int num;
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

        Button[] button = new Button[regions.length];

        // リニアレイアウトの設定
        LinearLayout layout = new LinearLayout(this);
        //LinearLayout layout = findViewById(R.id.regionLinear);


        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        // レイアウト中央寄せ
        layout.setGravity(Gravity.CENTER);

        // 背景色
        layout.setBackgroundColor(Color.rgb(0x00, 0x00, 0x00));


        setContentView(layout);

        // dp単位を取得
        float dp = getResources().getDisplayMetrics().density;
        // Button 幅を250dpに設定
        int buttonWidth = (int)(380 * dp);
        int buttonHeight = (int)(60 * dp);
        // マージン 10dp に設定
        int margins = (int)(5 * dp);

        // TextViewの設定
        textView = new TextView(this);
        String str = "TextView";
        textView.setText(str);
        textView.setTextColor(0xffffff);
        textView.setTextSize(10*dp);
        layout.addView(textView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        int i = 0;
        int num = 0;
        for(Button btn : button) {
            btn = new Button(this);

            num = i;
            // Tag を設定する
            btn.setTag(String.valueOf(i));
            btn.setText(String.format(Locale.US, "%s", regions[num]));

            LinearLayout.LayoutParams buttonLayoutParams =
                    new LinearLayout.LayoutParams(buttonWidth,
                            buttonHeight);
            buttonLayoutParams.setMargins(margins, margins, margins, margins);


            btn.setLayoutParams(buttonLayoutParams);
            layout.addView(btn);

            btn.setOnClickListener( v -> {
                // View からTagを取り出す
                textView.setText(String.format(Locale.US,
                        "Button: %s", v.getTag().toString()));

                //tagを数値で取得
                addSel = Integer.parseInt(String.valueOf(v.getTag()));


                    //選択したボタンに対応する地方をStatusFlagに記録
                    status.setSelRegion(addSel);
                    //都道府県選択画面へ移行
                    Intent intent = new Intent(getApplication(), PrefectureSelectActivity.class);
                    startActivity(intent);

            });
            i++;
        }


        // TextViewの設定

        /*
        textView = new TextView(this);
        String str = "TextView";
        textView.setText(str);
        textView.setTextColor(0xff000000);
        textView.setTextSize(10*dp);
        layout.addView(textView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        //地方選択ボタンを配置
        for(int i=0; i < regions.length; i++) {
            //ボタン生成
            Button btn = new Button(this);

            //地方名表示
            btn.setTag(String.valueOf(i)); //各ボタンごとにタグづけ
            btn.setText(regions[i]);

            LinearLayout.LayoutParams buttonLayoutParams =
                    new LinearLayout.LayoutParams(buttonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

            buttonLayoutParams.setMargins(margins, margins, margins, margins);


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
        */


        //戻るボタン
        //Button backButton = findViewById(R.id.backActivity);
        //backButton.setOnClickListener( v -> finish());
    }
}
