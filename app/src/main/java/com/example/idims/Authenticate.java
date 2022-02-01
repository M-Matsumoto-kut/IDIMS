package com.example.idims;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//認証モジュール
public class Authenticate extends AppCompatActivity implements AWSConnect.CallBackTask{

    private String password;


    //コールバックメソッド:パスワードが返ってくるはず？だからパスワードを受け取る
    public void CallBack(String str){
        this.password = str;
    }


    //IDとパスワードが一致しているか検証(true：一致，false:不一致)
    public boolean loginAuthenticate(int id, String inputPassword) {
        //boolean match = false;

        //idを元にパスワードを取得
        //String password = "jjjjj"; //テスト

        //AWSConnectを用いてPHPファイルに接続しSQL文の結果を返す
        AWSConnect con = new AWSConnect(); //AWSConnectインスタンスの呼び出し
        ////phpファイルの置いてある場所の指定
        String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/admin.php";
        //データベースに転送する文字列の転送
        String str = String.valueOf(id);

        //CallBackの設定...コールバック関数内でデータベースからの返信(SQL探索結果)を受け取る
        con.setOnCallBack(this);
        //実行
        con.execute(url, str);


        //入力されたパスワードとpasswordが一致しているか
        return this.matchPassword(inputPassword);

    }

    //DBから買ってきたパスワードと入力したパスワードが一致しているか
    private boolean matchPassword(String inputPassword) {
        return inputPassword.equals(this.password);
    }

    //現在のパスワードが一致しているか検証(true：一致，false:不一致)
    public boolean nowPasswordAuthenticate(int id, String nowPassword) {
        //boolean match = false;

        //idを元にパスワードを取得
        //String password = "unko"; //テスト

        //AWSConnectを用いてPHPファイルに接続しSQL文の結果を返す
        AWSConnect con = new AWSConnect(); //AWSConnectインスタンスの呼び出し
        ////phpファイルの置いてある場所の指定
        String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/admin.php";
        //データベースに転送する文字列の転送
        String str = String.valueOf(id);

        //CallBackの設定...コールバック関数内でデータベースからの返信(SQL探索結果)を受け取る
        con.setOnCallBack(this);
        //実行
        con.execute(url, str);

        //nowPasswordとpasswordが一致しているか
        return this.matchPassword(nowPassword);

    }

    //新しいパスワードが規定通りか検証(true：一致，false:不一致)
    public static boolean newPasswordAuthenticate(String newPassword) {
        boolean match = false;

        //パスワードが空じゃないか
        if( newPassword == null || newPassword.isEmpty() ) return false ;

        //パスワードが8文字以上でかつ16文字以下か
        int len = newPassword.length();
        if(!(8 <= len && len <= 16)) return false;

        //パスワードが半角英数字で構成されているか
        String regex = "^[A-Za-z0-9]+$" ; // 半角英数字のみ

        // 3. 引数に指定した正規表現regexがnewPasswordにマッチするか確認する
        Pattern p1 = Pattern.compile(regex); // 正規表現パターンの読み込み
        Matcher m1 = p1.matcher(newPassword); // パターンと検査対象文字列の照合
        match = m1.matches(); // 照合結果をtrueかfalseで取得

        return match;
    }

}


























