package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

public class CalorieCalc extends AppCompatActivity {

    EditText inputCalories, inputWeight, inputFeet, inputInches;
    private Button mButtonContinue;
    private ToggleButton no_Exercise, light_Exercise, mod_Exercise, active_Exercise;
    private CheckBox cb_male, cb_female, cb_young, cb_adult, cb_old, cb_senior;
    Switch changeFields;
    LinearLayout inputFields, check1, check2, weight, height;
    RelativeLayout button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        inputCalories = (EditText) findViewById(R.id.input_calorie);
        inputWeight = (EditText) findViewById(R.id.weight_input);
        inputFeet = (EditText) findViewById(R.id.ft_input);
        inputInches = (EditText) findViewById(R.id.inch_input);

        no_Exercise = (ToggleButton) findViewById(R.id.button_lazy);
        no_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(no_Exercise.isChecked()){
                    no_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.lazy_selected));
                    light_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.walking));
                    mod_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.active));
                    active_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.very_active));
                } else {
                    no_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.lazy));
                }
            }
        });
        light_Exercise = (ToggleButton) findViewById(R.id.button_walking);
        light_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light_Exercise.isChecked()){
                    no_Exercise.isChecked();
                    light_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.walking_selected));
                    no_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.lazy));
                    mod_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.active));
                    active_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.very_active));
                } else {
                    light_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.walking));
                }
            }
        });
        mod_Exercise = (ToggleButton) findViewById(R.id.button_jogging);
        mod_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mod_Exercise.isChecked()){
                    mod_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.active_selected));
                    light_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.walking));
                    no_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.lazy));
                    active_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.very_active));
                } else {
                    mod_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.active));
                }
            }
        });
        active_Exercise = (ToggleButton)findViewById(R.id.button_running);
        active_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active_Exercise.isChecked()){
                    active_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.very_active_selected));
                    mod_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.active));
                    light_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.walking));
                    no_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.lazy));
                } else {
                    active_Exercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.very_active));
                }
            }
        });

        cb_male = (CheckBox) findViewById(R.id.checkBoxMale);
        cb_female = (CheckBox) findViewById(R.id.checkBoxFemale);
        cb_young = (CheckBox) findViewById(R.id.checkBoxAge1);
        cb_adult = (CheckBox) findViewById(R.id.checkBoxAge2);
        cb_old = (CheckBox) findViewById(R.id.checkBoxAge3);
        cb_senior = (CheckBox) findViewById(R.id.checkBoxAge4);

        cb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_female.setChecked(false);
                }
            }
        });
        cb_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_male.setChecked(false);
                }
            }
        });
        cb_young.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_adult.setChecked(false);
                    cb_old.setChecked(false);
                    cb_senior.setChecked(false);
                }
            }
        });
        cb_adult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_young.setChecked(false);
                    cb_old.setChecked(false);
                    cb_senior.setChecked(false);
                }
            }
        });
        cb_old.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_adult.setChecked(false);
                    cb_young.setChecked(false);
                    cb_senior.setChecked(false);
                }
            }
        });
        cb_senior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_adult.setChecked(false);
                    cb_old.setChecked(false);
                    cb_young.setChecked(false);
                }
            }
        });

        inputFields = (LinearLayout) findViewById(R.id.input_cal_field);
        check1 = (LinearLayout) findViewById(R.id.checkLayout1);
        check2 = (LinearLayout) findViewById(R.id.checkLayout2);
        weight = (LinearLayout) findViewById(R.id.weightLayout);
        height = (LinearLayout) findViewById(R.id.heightLayout);
        button1 = (RelativeLayout) findViewById(R.id.buttonLayout1);
        button2 = (RelativeLayout) findViewById(R.id.buttonLayout2);

        changeFields = (Switch) findViewById(R.id.switch_fields);
        changeFields.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for ( int i = 0; i < inputFields.getChildCount();  i++ ){
                        View view = inputFields.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < check1.getChildCount();  i++ ){
                        View view = check1.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < check2.getChildCount();  i++ ){
                        View view = check2.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < weight.getChildCount();  i++ ){
                        View view = weight.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < height.getChildCount();  i++ ){
                        View view = height.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < button1.getChildCount();  i++ ){
                        View view = button1.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < button2.getChildCount();  i++ ){
                        View view = button2.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                } else {
                    for ( int i = 0; i < inputFields.getChildCount();  i++ ){
                        View view = inputFields.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < check1.getChildCount();  i++ ){
                        View view = check1.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < check2.getChildCount();  i++ ){
                        View view = check2.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < weight.getChildCount();  i++ ){
                        View view = weight.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < height.getChildCount();  i++ ){
                        View view = height.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < button1.getChildCount();  i++ ){
                        View view = button1.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                    for ( int i = 0; i < button2.getChildCount();  i++ ){
                        View view = button2.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                }
            }
        });

    };



    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CalorieCalc.class);
        return intent;
    }

}
