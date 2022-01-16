package com.example.idims;

import android.app.Application;
import java.util.Arrays;

public class StatusFlag extends Application {
    private int activityStatus;     // 起動時のActivityの状態
    private int loginType;          // login時のユーザのタイプ
    private int id;                 // (研究者)ログイン後IDを記録する
    private int[] selectRegionNum = new int[8];       //　選択地方を番号で格納（添字がregionsと対応)
    private String[] regions = {"北海道", "東北地方", "関東地方", "中部地方", "近畿地方",
            "中国地方", "四国地方", "九州地方"};
    private int[] selectPrefectureNum = new int[47]; //　選択都道府県を番号で格納
    private String[] prefectures = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県",
                                    "福島県", "東京都", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県",
                                    "神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県",
                                     "長野県", "岐阜県", "静岡県", "愛知県", "京都府", "大阪府",
                                    "三重県", "滋賀県", "兵庫県", "奈良県", "和歌山県", "鳥取県",
                                     "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県",
                                    "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県", "大分県",
                                    "熊本県", "宮崎県", "鹿児島県", "沖縄県"};
    private int countReg; // 選択地方数をカウント
    private int countPre; // 選択都道府県数をカウント


    @Override
    public void onCreate() {
        super.onCreate();

        //値の初期化
        Arrays.fill(selectRegionNum, 99);
        Arrays.fill(selectPrefectureNum,99);
        countPre = 0;
        countReg = 0;

        this.setActivityStatus(1);
    }

    /*
        activityStatusの値を返す
        1:StartLogin
        2:
        3:
     */
    public int getActivityStatus(){
        return this.activityStatus;
    }

    //Activityの状態を更新
    public void setActivityStatus(int status) {
        this.activityStatus = status;
    }

    /*
        loginTypeの値を返す
        1:generalUser
        2:researcherUser
     */
    public int getLoginType() {
        return this.loginType;
    }

    //loginTypeの値を更新（一般人）
    public void setLoginTypeGen() {
        this.loginType = 1;
    }

    //loginTypeの値を更新(研究者）
    public void setLoginTypeRes() {
        this.loginType = 2;
    }

    //idを返す
    public int getId() {
        return this.id;
    }

    //idの値を更新
    public void setId(int id) { this.id = id; }

    //regions（地方リスト）から特定の地方名を返す
    public String getRegionName(int num) { return regions[num]; };

    /*
    選択地域を設定(selectRegionNumに地方に対応した配列番号numを格納）
    例：北海道 -> 0, ~, 沖縄県 -> 46

     */
    public void setRegion(int num) {
        this.selectRegionNum[this.countReg] = num;
        this.countReg ++; //
    }

    /*
    //passwordを返す
    public String getPassword() {
        return this.password;
    }

    //passwordの値を更新
    public void setPassword(String password) {
        this.password = password;
    }
    */
}
