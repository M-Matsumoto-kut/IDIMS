package com.example.idims;

import java.util.ArrayList;

public class debugDisasterSearchData {

    private double latlist[] = new double[20]; //緯度
    private double lnglist[] = new double[20]; //経度
    private int levellist[] = new int[20]; //災害レベル
    private int condislist[] = new int[20]; //災害種類
    private String timelist[] = new String[20]; //発生時刻


    public debugDisasterSearchData(){
        setLatList();
        setLngList();
        setLevelList();
        setConDisList();
        setTimeList();
    }

    private void setLatList(){
        latlist[0] = 33;
        latlist[1] = 33;
        latlist[2] = 33.33;
        latlist[3] = 40;
        latlist[4] = 35;
        latlist[5] = 35;
        latlist[6] = 32;
        latlist[7] = 33;
        latlist[8] = 33;
        latlist[9] = 33;
        latlist[10] = 33;
        latlist[11] = 33;
        latlist[12] = 33;
        latlist[13] = 33;
        latlist[14] = 33;
        latlist[15] = 33;
        latlist[16] = 33;
        latlist[17] = 33.33;
        latlist[18] = 33.33;
        latlist[19] = 33.33;

    }

    private void setLngList(){
        lnglist[0] = 133;
        lnglist[1] = 133;
        lnglist[2] = 133.38;
        lnglist[3] = 142.1;
        lnglist[4] = 135;
        lnglist[5] = 137;
        lnglist[6] = 131.333;
        lnglist[7] = 132;
        lnglist[8] = 133;
        lnglist[9] = 133;
        lnglist[10] = 133;
        lnglist[11] = 133;
        lnglist[12] = 133;
        lnglist[13] = 133;
        lnglist[14] = 133;
        lnglist[15] = 133;
        lnglist[16] = 133;
        lnglist[17] = 133;
        lnglist[18] = 133.33;
        lnglist[19] = 133;

    }

    private void setLevelList(){
        levellist[0] = 1;
        levellist[1] = 2;
        levellist[2] = 3;
        levellist[3] = 4;
        levellist[4] = 5;
        levellist[5] = 5;
        levellist[6] = 3;
        levellist[7] = 3;
        levellist[8] = 2;
        levellist[9] = 4;
        levellist[10] = 4;
        levellist[11] = 2;
        levellist[12] = 1;
        levellist[13] = 2;
        levellist[14] = 3;
        levellist[15] = 5;
        levellist[16] = 6;
        levellist[17] = 1;
        levellist[18] = 2;
        levellist[19] = 3;
    }

    private void setConDisList(){
        condislist[0] = 1;
        condislist[1] = 1;
        condislist[2] = 1;
        condislist[3] = 2;
        condislist[4] = 2;
        condislist[5] = 3;
        condislist[6] = 2;
        condislist[7] = 3;
        condislist[8] = 3;
        condislist[9] = 3;
        condislist[10] = 2;
        condislist[11] = 1;
        condislist[12] = 1;
        condislist[13] = 2;
        condislist[14] = 3;
        condislist[15] = 2;
        condislist[16] = 1;
        condislist[17] = 1;
        condislist[18] = 2;
        condislist[19] = 3;

    }

    private void setTimeList(){
        timelist[0] = "20220116040543";
        timelist[1] = "20210106080221";
        timelist[2] = "20220715094555";
        timelist[3] = "20211103112449";
        timelist[4] = "20210928073349";
        timelist[5] = "20210101234401";
        timelist[6] = "20220228030059";
        timelist[7] = "20220303151919";
        timelist[8] = "20210304114515";
        timelist[9] = "20220614033925";
        timelist[10] = "20210506056827";
        timelist[11] = "20220101234839";
        timelist[12] = "20220806174203";
        timelist[13] = "20220909224455";
        timelist[14] = "20220101001234";
        timelist[15] = "20220305034732";
        timelist[16] = "20220108164044";
        timelist[17] = "20211030223030";
        timelist[18] = "20210721072119";
        timelist[19] = "20210815020248";

    }

    public double getLatList(int i){
        return latlist[i];
    }

    public double getLngList(int i){
        return lnglist[i];
    }

    public int getLevelList(int i){
        return levellist[i];
    }

    public int getConDisList(int i){
        return condislist[i];
    }

    public String getTimeList(int i){
        return timelist[i];
    }
}
