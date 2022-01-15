package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authenticate extends AppCompatActivity {

    //IDとパスワードが一致しているか検証(true：一致，false:不一致)
    public static boolean loginAuthenticate(String id, String inputPassword) {
        //boolean match = false;

        //idを元にパスワードを取得
        String password = "jjjjj"; //テスト

        //入力されたパスワードとpasswordが一致しているか
        return inputPassword.equals(password);


    }

    //現在のパスワードが一致しているか検証(true：一致，false:不一致)
    public static boolean nowPasswordAuthenticate(int id, String nowPassword) {
        //boolean match = false;

        //idを元にパスワードを取得
        String password = "unko"; //テスト

        //nowPasswordとpasswordが一致しているか
        return nowPassword.equals(password);

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


























