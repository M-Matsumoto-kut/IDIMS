package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class Researcherpassword extends AppCompatActivity {

    EditText pass1, pass2;
    String editTextTextPassword1, editTextTextPassword2;
    ImageView eye1, eye2;
    boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.researcherpassword);
        pass1 = findViewById(R.id.editTextTextPassword1);
        pass2 = findViewById(R.id.editTextTextPassword2);
        eye1 = findViewById(R.id.toggle_view1);
        eye2 = findViewById(R.id.toggle_view2);
        editTextTextPassword1 = "";
        editTextTextPassword1 = pass1.getText().toString();
        editTextTextPassword2 = "";
        editTextTextPassword2 = pass2.getText().toString();


        Button button1 = findViewById(R.id.backactivity);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Researcherpassword.this, Researcherpage.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.changeactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "パスワードを変更しました",  Snackbar.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Researcherpassword.this, Researcherpage.class);
                        startActivity(intent);
                    }
                }, 1000);
            }
        });
    }

    public void toggle1(View v){
        if(!state){
            pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass1.setSelection(pass1.getText().length());
            eye1.setImageResource(R.drawable.eye1);

        }
        else{
            pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass1.setSelection(pass1.getText().length());
            eye1.setImageResource(R.drawable.eye_off1);
        }
        state = !state;
    }

    public void toggle2(View v){
        if(!state){
            pass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass2.setSelection(pass2.getText().length());
            eye2.setImageResource(R.drawable.eye2);

        }
        else{
            pass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass2.setSelection(pass2.getText().length());
            eye2.setImageResource(R.drawable.eye_off2);
        }
        state = !state;
    }

}