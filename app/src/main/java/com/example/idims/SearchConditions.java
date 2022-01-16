package com.example.idims;

//検索条件を格納するクラス
public class SearchConditions {
    //種類チェックボタンが押されているかを格納するboolean型,押されている場合は真となる
    public boolean wave_On = false;
    public boolean landsride_On = false;
    public boolean thounder_On = false;
    //一定期間において全ての期間が選ばれているかを判定するbool型
    public boolean allConstant = false;


    //インストラクタ
    public SearchConditions(){}

    //期間設定において一定期間を選択した際にどの期間にするかを決定する
    public void settingConstant(String str) {
        String all = "全て";
        String a_month = "過去１か月";
        String half_year = "過去半年";
        String a_year = "過去１年";
        String five_year = "過去５年";
        if (str.equals(all)) {
            allConstant = true;
        } else {
            allConstant = false;
            if (str.equals(a_month)) {
            } else if (str.equals(half_year)) {
            } else if (str.equals(a_year)) {
            } else if (str.equals(five_year)) {
            }
        }
    }

    //期間設定において自由期間を選択した際の期間選択
    public void settingFree(String startYear, String startMonth, String endYear, String endMonth){
        allConstant = false;

    }

    //ここから下は検索条件に入っているかを判定するbool型のセット関数の集まり
    //津波
    public void setWave_on(boolean wave_On){
        this.wave_On = wave_On;
    }
    //地すべり
    public void setLandsride_On(boolean landsride_On){
        this.landsride_On = landsride_On;
    }
    //雷
    public void setThounder_On(boolean thounder_On){
        this.thounder_On = thounder_On;
    }

}
