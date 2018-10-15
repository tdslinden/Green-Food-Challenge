package com.android.greenfoodchallenge.carboncalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Button mButtonCalc;
    private Button mButtonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mButtonCalc = (Button)findViewById(R.id.button_calc);
        mButtonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCalc = new Intent(MenuActivity.this, CalcActivity.class);
                startActivity(goToCalc);
            }
        });

        mButtonAbout = (Button)findViewById(R.id.button_about);
        mButtonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAbout = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(goToAbout);
            }
        });
    }
}
