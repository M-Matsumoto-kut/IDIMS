package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idims.AWSConnect;
import com.example.idims.MainActivity;
import com.example.idims.Menu.MenuActivity;
import com.example.idims.R;
import com.example.idims.StatusFlag;

/*
    センサー確認モジュール
 */
public class SensorListActivity extends AppCompatActivity implements AWSConnect.CallBackTask{

    private StatusFlag status;
    private int sensorNum;
    private String sensorName;
    private int textHeight;
    private int listLength;
    private String[] sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (StatusFlag) getApplication();
        sensorNum = 0;
        sensorName = "";
        textHeight = 0;
        listLength = 0;

        //レイアウト生成
        setContentView(R.layout.activity_sensor);

        //戻るボタン->研究者ページ
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SensorListActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }

    //毎動作時に実行
    @Override
    protected void onStart() {
        super.onStart();
        sensorNum = 0;
        sensorName = "";
        textHeight = 0;
        listLength = 0;

        //データベースからセンサー情報を取得
        //AWSConnectを用いてPHPファイルに接続しSQL文の結果を返す
        AWSConnect con = new AWSConnect(); //AWSConnectインスタンスの呼び出し
        ////phpファイルの置いてある場所の指定
        String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/sensor.php";
        //データベースに転送する文字列の転送
        String str = "n";

        //CallBackの設定...コールバック関数内でデータベースからの返信(SQL探索結果)を受け取る
        con.setOnCallBack(this);
        //実行
        con.execute(url,str);
    }



    //コールバックメソッド:パスワードが返ってくるはず？だからパスワードを受け取る
    public void CallBack(String str){
        sensorList = str.split(",");
        listLength = sensorList.length; //センサーリストの長さを取得
        this.sensorListActivity();
    }

    //センサー異常一覧を表示
    private void sensorListActivity() {


        TextView[] textView = new TextView[47];

        float dp = getResources().getDisplayMetrics().density;
        textHeight = (int)(60 * dp);

        LinearLayout linearLayout = findViewById(R.id.SensorListLinear);
        linearLayout.setGravity(Gravity.TOP);

        //一覧表示(8件まで）
        for(int i = 0; i < 4; i++) {
            if(listLength == 0) {
                //表示
                textView[i] = new TextView(this);
                textView[i].setTextColor(0xffffffff);
                textView[i].setTextSize(10 * dp);

                textView[i].setBackgroundColor(0xFF575757);

                textView[i].setText("全センサーは正常に稼働しています");
                textView[i].setGravity(Gravity.CENTER);
                linearLayout.addView(textView[i],
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                textHeight));
            }

            if(i >= listLength) {
                break;
            } else {
                //格納したprefectureNumをもとに都道府県の名前を取得

                //表示
                textView[i] = new TextView(this);
                textView[i].setTextColor(0xffffffff);
                textView[i].setTextSize(10 * dp);

                if (i % 2 == 1) {
                    textView[i].setBackgroundColor(0xFF575757);
                } else {
                    textView[i].setBackgroundColor(0xFF454545);
                }

                textView[i].setText("【No." + sensorList[i] + "】");
                textView[i].setGravity(Gravity.CENTER);
                linearLayout.addView(textView[i],
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                textHeight));
            }
        }
    }
}