package com.example.idims;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

public class StatusFlag extends Application {
    private int activityStatus;     // 起動時のActivityの状態

    private int loginType;       // login時のユーザのタイプ
    private int id;              // (研究者)ログイン後IDを記録する
    private String password;        // (研究者)ログイン後パスワードを記録する

    @Override
    public void onCreate() {
        super.onCreate();
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
    public void setId(int id) {
        this.id = id;
    }

    //passwordを返す
    public String getPassword() {
        return this.password;
    }

    //passwordの値を更新
    public void setPassword(String password) {
        this.password = password;
    }
}
