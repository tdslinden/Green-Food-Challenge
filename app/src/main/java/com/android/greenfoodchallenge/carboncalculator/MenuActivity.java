package com.android.greenfoodchallenge.carboncalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
        buttonIDList.add(R.id.btnTemp);
        buttonIDList.add(R.id.btnShare);
    }

    private void setupButton(int buttonID){
        Button button = (Button) findViewById(buttonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pressedButtonID = v.getId();
                if (pressedButtonID == R.id.btnAboutActivity){
                    Intent intent = AboutSwipeActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                }
                else if (pressedButtonID == R.id.btnCalculatorActivity){
                    Intent intent = CalorieCalc.makeIntent(MenuActivity.this);
                    startActivity(intent);
                }
                else if(pressedButtonID == R.id.btnTemp){
                    Intent intent = authenticationActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                } else if(pressedButtonID == R.id.btnShare) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    // change the msg here
                    intent.setType("text/plain");
                    String shareBody = "Join the green food challenge";
                    intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(intent, "Share using"));
                }
            }
        });
    }
}
