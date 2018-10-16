package com.android.greenfoodchallenge.carboncalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private List<Integer> buttonIDList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        populateIDList();
        for (int buttonID : buttonIDList) {
            setupButton(buttonID);
        }
    }

    private void populateIDList(){
        buttonIDList.add(R.id.btnAboutActivity);
        buttonIDList.add(R.id.btnCalculatorActivity);
    }

    private void setupButton(int buttonID){
        Button button = (Button) findViewById(buttonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pressedButtonID = v.getId();
                if (pressedButtonID == R.id.btnAboutActivity){
                    Intent intent = AboutActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                }
                else if (pressedButtonID == R.id.btnCalculatorActivity){
                    Toast.makeText(getApplicationContext(),"Calculator Activity Button Pressed!",Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = CalcActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                }
            }
        });
    }
}
