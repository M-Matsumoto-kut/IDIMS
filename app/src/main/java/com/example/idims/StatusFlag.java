package com.example.idims;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.Arrays;

//　全てモジュールにおいて，保持する必要があるデータを格納するクラス
public class StatusFlag extends Application {
    private int activityStatus;             // 起動時のActivityの状態
    private int loginType;                  // login時のユーザのタイプ
    private String id;                         // 研究者ID記録
    private static final int USER_ID = 0;   // (研究者)ログイン後IDを保存する
    final private String[] regions = {"北海道地方", "東北地方", "関東地方", "中部地方", "近畿地方",
            "中国地方", "四国地方", "九州地方"};
    private int[] selectPrefectureNum = new int[47]; //　選択都道府県を番号で格納
    final private String[] prefectures = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県",
                                    "福島県", "東京都", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県",
                                    "神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県",
                                     "長野県", "岐阜県", "静岡県", "愛知県", "京都府", "大阪府",
                                    "三重県", "滋賀県", "兵庫県", "奈良県", "和歌山県", "鳥取県",
                                     "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県",
                                    "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県", "大分県",
                                    "熊本県", "宮崎県", "鹿児島県", "沖縄県"};
    private int countPre;           // 選択都道府県数をカウント
    private int selRegion;          // どの地方を選択したか記録
    private int gpsPermission;      // GPSの許可状態
    private int noticePermission;   // 通知の許可状態
    private int alertPermission;    // アラートの許可状態
    private int tsunamiStatus;      // 津波のの通知許可状態
    private int landslideStatus;    // 土砂崩れの通知許可状態
    private int thunderStatus;      // 雷の通知許可状態

    @Override
    public void onCreate() {
        super.onCreate();

        //値の初期化
        //Arrays.fill(selectRegionNum, 99);
        Arrays.fill(selectPrefectureNum,99);
        countPre = 0;
        loginType = 0;
        selRegion = 99;
        id = "";
        gpsPermission = 0;
        noticePermission = 0;
        alertPermission = 0;
        tsunamiStatus = 0;
        landslideStatus = 0;
        thunderStatus = 0;


        this.setActivityStatus(1);
    }


    /*
        直前のアクティビティを記録
     */
    public int getActivityStatus() { return this.activityStatus; }

    //Activityの状態を更新
    public void setActivityStatus(int status) {
        this.activityStatus = status;
    }

    /*
        loginTypeの値を返す
            0:nonSelect（未選択）
            1:generalUser(一般ユーザ)
            2:researcherUser(研究者ユーザ)
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

    //研究者ユーザのidを返す
    public String getId() {
        return this.id;
    }

    //研究者ユーザのidの値を更新して保存
    public void setId(String id) {
        this.id = id;
    }

    //prefectures（都道府県リスト）から番号に対応した都道府県名を返す
    public String getPrefectureName(int num) { return prefectures[num]; }

    /*
        selRegion（選択した地方の番号）を返す
            0:北海道
            1:東北
            2:関東
            3:中部
            4:近畿
            5:中国
            6:四国
            7:九州
     */
    public int getSelRegion() {
        return this.selRegion;
    }

    //selRegion（選択した地方の番号）を更新
    public void setSelRegion(int selRegion) { this.selRegion = selRegion; }

    //selectPrefectureNum(選択した都道府県の番号）を返す
    public int getPrefectureNum(int i) {
        return this.selectPrefectureNum[i];
    }

    /*
    選択地域を設定(selectPrefectureNumに都道府県に対応した配列番号numを格納）
        例：北海道 -> 0, ~, 沖縄県 -> 46
     */
    public void setPrefectureNum(int num) {
        this.selectPrefectureNum[this.countPre] = num;
        this.countPre ++; //カウント
    }

    //GPSの許可状態を取得(0:無許可，1:許可)
    public int getGpsPermission() { return this.gpsPermission; }

    //GPSの許可状態を更新(0:無許可，1:許可)
    public void setGpsPermission(int i) {
        this.gpsPermission = i;
    }

    //通知の許可状態を取得(0:無許可，1:許可)
    public int getNoticePermission() { return this.noticePermission; }

    //通知の許可状態を更新(0:無許可，1:許可)
    public void setNoticePermission(int j) {
        this.noticePermission = j;
    }

    //アラートの許可状態を取得(0:無許可，1:許可)
    public int getAlertPermission() { return this.alertPermission; }

    //アラートの許可状態を更新(0:無許可，1:許可)
    public void setAlertPermission(int k) {
        this.alertPermission = k;
    }

    //津波の通知許可状態を取得(0:無許可，1:許可)
    public int getTsunamiStatus() { return this.tsunamiStatus; }

    //津波の通知許可状態を更新(0:無許可，1:許可)
    public void setTsunamiStatus(int i) {
        this.tsunamiStatus = i;
    }

    //土砂崩れの通知許可状態を取得(0:無許可，1:許可)
    public int getLandslideStatus() { return this.landslideStatus; }

    //土砂崩れの通知許可状態を更新(0:無許可，1:許可)
    public void setLandslideStatus(int i) {
        this.landslideStatus = i;
    }

    //雷の通知許可状態を取得(0:無許可，1:許可)
    public int getThunderStatus() { return this.thunderStatus; }

    //雷の通知許可状態を更新(0:無許可，1:許可)
    public void setThunderStatus(int i) {
        this.thunderStatus = i;
    }


}
