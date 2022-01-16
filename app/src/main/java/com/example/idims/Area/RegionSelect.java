package com.example.idims.Area;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.idims.R;

import java.util.Locale;

//地方選択画面モジュール
public class RegionSelect extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ボタン配列
        Button[] buttons = new Button[47];

        //リニアレイアウトの設定
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        // レイアウト中央寄せ
        layout.setGravity(Gravity.CENTER);

        // 背景色
        layout.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));

        setContentView(layout);

        // dp単位を取得
        float dp = getResources().getDisplayMetrics().density;
        // Button 幅を250dpに設定
        int buttonWidth = (int)(400 * dp);
        // マージン 10dp に設定
        int margins = (int)(1 * dp);

        // TextViewの設定
        textView = new TextView(this);
        String str = "TextView";
        textView.setText(str);
        textView.setTextColor(0xff000000);
        textView.setTextSize(20*dp);
        layout.addView(textView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        int i = 0;
        for(Button btn : buttons) {
            btn = new Button(this);
            i++;

            // Tag を設定する
            btn.setTag(String.valueOf(i));
            btn.setText(String.format(Locale.US, "Button %d", i));

            LinearLayout.LayoutParams buttonLayoutParams =
                    new LinearLayout.LayoutParams(buttonWidth,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(margins, margins, margins, margins);

            btn.setLayoutParams(buttonLayoutParams);
            layout.addView(btn);

            // Listenerをセット
            // lambda型
            btn.setOnClickListener( v -> {
                // View からTagを取り出す
                textView.setText(String.format(Locale.US,
                        "Button: %s", v.getTag().toString()));
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //this.regtionSelect();
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
