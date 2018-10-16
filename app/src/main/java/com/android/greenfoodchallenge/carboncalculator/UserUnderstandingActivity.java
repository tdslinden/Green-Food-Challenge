package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserUnderstandingActivity extends AppCompatActivity {


    TextView TextResult;
    Button ButtonMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_understanding);

        TextResult = (TextView)findViewById(R.id.textView);
        ButtonMenu=(Button)findViewById(R.id.button);

        //Once the SavingActivity is done this will be replaced
        double temp=2.6;

        EquivalenceCalculator calc= new EquivalenceCalculator();
        TextResult.setText(calc.getResultString(temp));
        ButtonMenu.setText("Menu");
        ButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(UserUnderstandingActivity.this, MenuActivity.class);
                startActivity(goToMenu);
            }

        });
    }
}
