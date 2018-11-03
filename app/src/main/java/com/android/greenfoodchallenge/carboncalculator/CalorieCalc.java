package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CalorieCalc extends AppCompatActivity {

    EditText inputCalories, inputWeight, inputFeet, inputInches;
    private Button mButtonContinue;
    private ToggleButton no_Exercise, light_Exercise, mod_Exercise, active_Exercise;
    private CheckBox cb_male, cb_female, cb_young, cb_adult, cb_old, cd_senior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        inputCalories = (EditText) findViewById(R.id.input_calorie);
        inputWeight = (EditText) findViewById(R.id.weight_input);
        inputFeet = (EditText) findViewById(R.id.ft_input);
        inputInches = (EditText) findViewById(R.id.inch_input);

        no_Exercise = (ToggleButton) findViewById(R.id.button_lazy);
//        no_Exercise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        light_Exercise = (ToggleButton) findViewById(R.id.button_walking);
        mod_Exercise = (ToggleButton) findViewById(R.id.button_jogging);
        active_Exercise = (ToggleButton)findViewById(R.id.button_running);


    };

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CalorieCalc.class);
        return intent;
    }
}
