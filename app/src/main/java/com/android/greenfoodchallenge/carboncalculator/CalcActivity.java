package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CalcActivity extends AppCompatActivity {

    private Button mButtonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        mButtonSave = (Button)findViewById(R.id.button_savings);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSave = new Intent(CalcActivity.this, SavingsActivity.class);
                startActivity(goToSave);
            }
        });
    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, CalcActivity.class);
        return intent;
    }
}
