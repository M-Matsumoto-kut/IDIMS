package com.example.idims;

//検索条件を格納するクラス
public class SearchConditions {
    //種類チェックボタンが押されているかを格納するboolean型,押されている場合は真となる
    public boolean wave_On;
    public boolean landsride_On;
    public boolean thounder_On;

    //インストラクタ
    public SearchConditions(){}

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
