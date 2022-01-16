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

//import com.example.idims.MainActivity;
import com.example.idims.StatusFlag;

import java.util.Locale;

public class PrefectureSelect extends AppCompatActivity {
    private TextView textView;
    private StatusFlag status;

    private final String[] prefectures = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県",
            "福島県", "東京都", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県",
            "神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県",
            "長野県", "岐阜県", "静岡県", "愛知県", "京都府", "大阪府",
            "三重県", "滋賀県", "兵庫県", "奈良県", "和歌山県", "鳥取県",
            "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県",
            "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県", "大分県",
            "熊本県", "宮崎県", "鹿児島県", "沖縄県"};

    private int addSel;
    private int num;
    private int start;  //都道府県表示の初めの位置
    private int end;    //都道府県表示の最後の位置
    private int addedSell;

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
        start = 0;
        end = 0;
        addedSell = 0;
        this.regionSelect();
    }

    //地方選択
    private void regionSelect(){

        Button[] button = new Button[prefectures.length];

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

        //StatusFlagに記録したaddSel(記録した地方に対応した番号）を取得する
        addSel = status.getAddSel();

        //地方によって表示する都道府県の範囲を選択
        switch(addSel){
            case 0: //北海道
                start = 0;
                end = 0;
                break;
            case 1: //東北
                start = 1;
                end = 6;
                break;
            case 2: //関東
                start = 7;
                end = 13;
                break;
            case 3: //中部
                start = 14;
                end = 22;
                break;
            case 4: //近畿
                start = 23;
                end = 29;
                break;
            case 5: //中国
                start = 30;
                end = 34;
                break;
            case 6: //四国
                start = 35;
                end = 38;
                break;
            case 7: //九州
                start = 39;
                end = 46;
                break;
            default: //エラー
                finish();
        }

        for(Button btn : button) {
            btn = new Button(this);

            num = i;
            // Tag を設定する


            //表示する都道府県の表示範囲を選択
            if (start <= num && num <= end) {
                // Tag を設定する
                btn.setTag(String.valueOf(i));
                btn.setText(String.format(Locale.US, "%s", prefectures[num]));
                //btn.setTextColor(0xffffff);

                LinearLayout.LayoutParams buttonLayoutParams =
                        new LinearLayout.LayoutParams(buttonWidth,
                                buttonHeight);
                buttonLayoutParams.setMargins(margins, margins, margins, margins);

                //ViewGroup.LayoutParams.WRAP_CONTENT

                btn.setLayoutParams(buttonLayoutParams);
                layout.addView(btn);
            }

            btn.setOnClickListener( v -> {
                // View からTagを取り出す
                //textView.setText(String.format(Locale.US,
                 //       "Button: %s", v.getTag().toString()));

                //tagを数値で取得

                addSel = Integer.parseInt(String.valueOf(v.getTag()));

                this.registerArea(addSel);
            });
            i++;
        }
    }

    private void registerArea(int x) {

        for(int j = 0; j < 47; j++) {
            addedSell = status.getPrefectureNum(j);

            //すでに登録済み
            if(x == addedSell) {
                finish();
            }
        }

        finish();

        //選択した都道府県を登録する
        status.setPrefectureNum(addSel);
        //都道府県選択画面へ移行
        Intent intent = new Intent(getApplication(), AreaList.class);
        startActivity(intent);

    }
}
