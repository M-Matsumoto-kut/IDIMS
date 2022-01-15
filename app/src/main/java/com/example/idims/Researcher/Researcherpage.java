package com.example.idims.Researcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.idims.R;

public class Researcherpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_researcherpage);


        Button button1 = findViewById(R.id.next1activity);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Researcherpage.this, Sensor.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.next2activity);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Researcherpage.this, ResearcherPassword.class);
                startActivity(intent);
            }
        });

    }
}