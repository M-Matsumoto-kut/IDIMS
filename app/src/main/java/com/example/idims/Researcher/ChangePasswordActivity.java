package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idims.AWSConnect;
import com.example.idims.Authenticate;
import com.example.idims.R;
import com.example.idims.StatusFlag;
import com.google.android.material.snackbar.Snackbar;

/*
    パスワード更新モジュール
 */
public class ChangePasswordActivity extends AppCompatActivity implements AWSConnect.CallBackTask {

    EditText nowPassword; //現在のパスワード
    EditText newPassword; //現在のパスワード

    Authenticate authenticate;
    private StatusFlag status;

    String nowPasswordStr, newPasswordStr;

    ImageView eye1, eye2; //パスワードの表示・非表示

    boolean state = false;//表示・非表示の切り替えステータス

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.authenticate = new Authenticate();
        this.status = (StatusFlag) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.passwordChange(0);
    }

    public void CallBack(String str){

    }

    private void passwordChange(int error){
        //パスワード変更画面生成
        setContentView(R.layout.activity_researcherpassword);

        //エラーテキスト
        TextView errorView;
        if(error == 1){
            errorView = (TextView) findViewById(R.id.errorText);
            errorView.setText("パスワードが間違っています");
        }else if(error == 2){
            String errorMessage = "8~16文字の英数字を入力してください";
            errorView = (TextView) findViewById(R.id.errorText);
            errorView.setText(errorMessage);
        }

        //現在のパスワードと新しいパスワードを紐付け
        nowPassword = findViewById(R.id.nowPassword);
        newPassword = findViewById(R.id.newPassword);

        //eyeとIDを紐付け
        eye1 = findViewById(R.id.toggle_view1);
        eye2 = findViewById(R.id.toggle_view2);

        //戻るボタン
        Button backButton = findViewById(R.id.backActivity);
        backButton.setOnClickListener( v -> {
            Intent intent = new Intent(ChangePasswordActivity.this, ResearcherPageActivity.class);
            startActivity(intent);
        });


        //更新ボタンが押された時
        findViewById(R.id.changePassword).setOnClickListener( v -> {

            //入力したパスワードを文字列に変換
            nowPasswordStr = nowPassword.getText().toString();
            newPasswordStr = newPassword.getText().toString();

            //フラグからIDを取得
            String id = status.getId();

            //現在のパスワードが正しいか
            if(authenticate.passAuthenticate(id, nowPasswordStr)) {

                //現在のパスワードが半角英数字でかつ8文字以上16文字以下か
                if(authenticate.newPasswordAuthenticate(newPasswordStr)){
                    //データベースにアクセスし，パスワードを更新

                    //AWSConnectを用いてPHPファイルに接続しSQL文の結果を返す
                    AWSConnect con = new AWSConnect(); //AWSConnectインスタンスの呼び出し
                    ////phpファイルの置いてある場所の指定
                    String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/adminexchange.php";
                    //データベースに転送する文字列の転送
                    String dist = "value=" + id + "," + newPasswordStr;
                    //CallBackの設定...コールバック関数内でデータベースからの返信(SQL探索結果)を受け取る
                    con.setOnCallBack(this);
                    //実行
                    con.execute(url, dist);

                    Snackbar.make(v, "パスワードを変更しました",  Snackbar.LENGTH_SHORT).show();

                    //研究者ページに戻る
                    Intent intent = new Intent(ChangePasswordActivity.this, ResearcherPageActivity.class);
                    startActivity(intent);
                } else {
                    //再入力を求める
                    this.passwordChange(2);
                }

            } else {
                //再入力を求める
                this.passwordChange(1);
            }
        });
    }

    //パスワードの表示・非表示
    public void toggle1(View v){
        if(!state){
            nowPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            nowPassword.setSelection(nowPassword.getText().length());
            eye1.setImageResource(R.drawable.eye1);

        }
        else{
            nowPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            nowPassword.setSelection(nowPassword.getText().length());
            eye1.setImageResource(R.drawable.eye_off1);
        }
        state = !state;
    }

    public void toggle2(View v){
        if(!state){
            newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            newPassword.setSelection(newPassword.getText().length());
            eye2.setImageResource(R.drawable.eye2);

        }
        else{
            newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            newPassword.setSelection(newPassword.getText().length());
            eye2.setImageResource(R.drawable.eye_off2);
        }
        state = !state;
    }

}