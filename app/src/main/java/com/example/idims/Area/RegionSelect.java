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

    //private LinearLayout linearLayout;
    private TextView textView;
    //private ScrollView scrollView;

    private final String[] regions = {"北海道", "東北地方", "関東地方", "中部地方", "近畿地方",
            "中国地方", "四国地方", "九州地方"};

    private int addSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart() {
        super.onStart();
        addSel = 99;
        this.regionSelect();
    }

    //地方選択
    private void regionSelect(){

        //地方選択画面表示
        setContentView(R.layout.activity_region_select);

        //リニアレイアウトの設定
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // ScrollView に LinearLayout を追加
        //scrollView.addView(layout);

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
        int buttonWidth = (int)(420 * dp);
        int buttonHeight = (int)(80 * dp);

        // マージン 10dp に設定
        int margins = (int)(0 * dp);

        // TextViewの設定
        textView = new TextView(this);
        String str = "TextView";
        textView.setText(str);
        textView.setTextColor(0xffffff);
        textView.setTextSize(60 * dp);
        layout.addView(textView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        for(int i=0; i < 8; i++) {
            Button btn = new Button(this);

            // Tag を設定する
            //btn.setTag(String.valueOf(i));
            //btn.setText(String.format(Locale.US, "Button %d", regions[i]));
            btn.setText(regions[i]);

            LinearLayout.LayoutParams buttonLayoutParams =
                    //new LinearLayout.LayoutParams(buttonWidth,
                    //        ViewGroup.LayoutParams.WRAP_CONTENT);
                    new LinearLayout.LayoutParams(buttonWidth,
                        buttonHeight);
            buttonLayoutParams.setMargins(margins, margins, margins, margins);

            btn.setLayoutParams(buttonLayoutParams);
            layout.addView(btn);

            // Listenerをセット
            // lambda型
            btn.setOnClickListener( v -> {
                // View からTagを取り出す
                /*
                textView.setText(String.format(Locale.US,
                        "Button: %s", v.getTag().toString()));

                 */

                //選択したボタンに対応する地方をStatusFlagに記録
                /*
                StatusFlag.setAddSel(i);

                if(addSel != 99) {

                }
                 */
                //都道府県選択画面へ移行
                Intent intent = new Intent(getApplication(), PrefectureSelect.class);
                startActivity(intent);
            });
        }
    }
}
