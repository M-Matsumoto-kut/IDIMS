package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idims.R;
import com.example.idims.StatusFlag;

/*
    センサー確認モジュール
 */
public class SensorListActivity extends AppCompatActivity {

    private StatusFlag status;
    private int sensorNum;
    private String sensorName;
    private int textHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //毎動作時に実行
    @Override
    protected void onStart() {
        super.onStart();
        this.status = (StatusFlag) getApplication();
        sensorNum = 0;
        sensorName = "";
        textHeight = 0;
        this.sensorListActivity();
    }

    //センサー異常一覧を表示
    private void sensorListActivity() {

        //データベースからセンサー情報を取得


        //レイアウト生成
        setContentView(R.layout.activity_sensor);


        //戻るボタン->研究者ページ
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), ResearcherPageActivity.class);
            startActivity(intent);
        });

        TextView[] textView = new TextView[47];

        float dp = getResources().getDisplayMetrics().density;
        textHeight = (int)(60 * dp);

        LinearLayout linearLayout = findViewById(R.id.SensorListLinear);
        linearLayout.setGravity(Gravity.TOP);

        //一覧表示(8件まで）
        for(int i = 0; i < 8; i++) {
            sensorNum = status.getPrefectureNum(i);

            if(sensorNum > 46) {
                break;
            } else {
                //格納したprefectureNumをもとに都道府県の名前を取得
                sensorName = status.getPrefectureName(sensorNum);

                //表示
                textView[i] = new TextView(this);
                textView[i].setTextColor(0xffffffff);
                textView[i].setTextSize(10 * dp);

                if (i % 2 == 1) {
                    textView[i].setBackgroundColor(0xFF575757);
                } else {
                    textView[i].setBackgroundColor(0xFF454545);
                }

                textView[i].setText(sensorName);
                textView[i].setGravity(Gravity.CENTER);
                linearLayout.addView(textView[i],
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                textHeight));
            }
        }
    }
}