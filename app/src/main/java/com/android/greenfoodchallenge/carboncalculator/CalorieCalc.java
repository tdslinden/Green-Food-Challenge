package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.view.View.VISIBLE;

public class CalorieCalc extends AppCompatActivity {

    EditText inputCalories, inputWeight, inputFeet, inputInches;
    private Button mButtonContinue, mButtonBack;
    private BottomNavigationView mBottomNavigation;
    private ToggleButton no_Exercise, light_Exercise, mod_Exercise, active_Exercise;
    private CheckBox cb_male, cb_female, cb_young, cb_adult, cb_old, cb_senior;
    Switch changeFields;
    ConstraintLayout inputFields, check1, check2, weight, height, clickableLayout;
    ConstraintLayout button1, button2;
    TextView intro;
    static final double weightModm = 6.3;
    static final double weightModf = 4.3;
    static final double heightModm = 12.9;
    static final double heightModf = 4.7;
    static final double BMR_sedentary = 1.2;
    static final double BMR_light_active = 1.375;
    static final double BMR_mod_active = 1.55;
    static final double BMR_very_active = 1.725;
    static final double ageYoungModm = 159.8;
    static final double ageAdultModm = 265.2;
    static final double ageOldModm = 363.8;
    static final double ageSeniorModm = 431.8;
    static final double ageYoungModf = 110.45;
    static final double ageAdultModf = 183.3;
    static final double ageOldModf = 251.45;
    static final double ageSeniorModf = 298.45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        intro = (TextView) findViewById(R.id.calorie_intro);

        clickableLayout = (ConstraintLayout) findViewById(R.id.focus_layout2);
        clickableLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inputCalories = (EditText) findViewById(R.id.input_calorie);
        inputCalories.setText("0");
        inputWeight = (EditText) findViewById(R.id.weight_input);
        inputFeet = (EditText) findViewById(R.id.ft_input);

        inputInches = (EditText) findViewById(R.id.inch_input);

        no_Exercise = (ToggleButton) findViewById(R.id.button_lazy);
        no_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(no_Exercise.isChecked()){
                    no_Exercise.setChecked(true);
                    changeToggleButtons(light_Exercise, mod_Exercise, active_Exercise);
                }
            }
        });
        light_Exercise = (ToggleButton) findViewById(R.id.button_walking);
        light_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light_Exercise.isChecked()){
                    changeToggleButtons(no_Exercise, mod_Exercise, active_Exercise);
                }
            }
        });
        mod_Exercise = (ToggleButton) findViewById(R.id.button_jogging);
        mod_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mod_Exercise.isChecked()){
                    changeToggleButtons(no_Exercise, light_Exercise, active_Exercise);
                }
            }
        });
        active_Exercise = (ToggleButton)findViewById(R.id.button_running);
        active_Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active_Exercise.isChecked()){
                    changeToggleButtons(no_Exercise, mod_Exercise, light_Exercise);
                }
            }
        });

        cb_male = (CheckBox) findViewById(R.id.checkBoxMale);
        cb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_female.setChecked(false);
                }
            }
        });
        cb_female = (CheckBox) findViewById(R.id.checkBoxFemale);
        cb_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_male.setChecked(false);
                }
            }
        });
        cb_young = (CheckBox) findViewById(R.id.checkBoxAge1);
        cb_young.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeAgeCheckBox(cb_adult, cb_old, cb_senior);
                }
            }
        });
        cb_adult = (CheckBox) findViewById(R.id.checkBoxAge2);
        cb_adult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeAgeCheckBox(cb_young, cb_old, cb_senior);
                }
            }
        });
        cb_old = (CheckBox) findViewById(R.id.checkBoxAge3);
        cb_old.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeAgeCheckBox(cb_adult, cb_young, cb_senior);
                }
            }
        });
        cb_senior = (CheckBox) findViewById(R.id.checkBoxAge4);
        cb_senior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeAgeCheckBox(cb_adult, cb_old, cb_young);
                }
            }
        });

        inputFields = (ConstraintLayout) findViewById(R.id.input_cal_field);
        check1 = (ConstraintLayout) findViewById(R.id.checkLayout1);
        check2 = (ConstraintLayout) findViewById(R.id.checkLayout2);
        weight = (ConstraintLayout) findViewById(R.id.weightLayout);
        height = (ConstraintLayout) findViewById(R.id.heightLayout);
        button1 = (ConstraintLayout) findViewById(R.id.buttonLayout1);
        button2 = (ConstraintLayout) findViewById(R.id.buttonLayout2);

        changeFields = (Switch) findViewById(R.id.switch_fields);
        changeFields.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    disableConstraintFields(inputFields);
                    inputFields.setVisibility(View.GONE);
                    changeFields.setText("Field View");
                    check1.setVisibility(VISIBLE);
                    check2.setVisibility(VISIBLE);
                    weight.setVisibility(VISIBLE);
                    height.setVisibility(VISIBLE);
                    button1.setVisibility(VISIBLE);
                    button2.setVisibility(VISIBLE);
                    intro.setVisibility(View.GONE);

                } else {
                    enableConstraintFields(inputFields);
                    inputFields.setVisibility(View.VISIBLE);
                    changeFields.setText("Input View");
                    check1.setVisibility(View.GONE);
                    check2.setVisibility(View.GONE);
                    weight.setVisibility(View.GONE);
                    height.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    button2.setVisibility(View.GONE);
                    intro.setVisibility(View.VISIBLE);
                }
            }
        });

        mButtonContinue = (Button) findViewById(R.id.button_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkWeight = inputWeight.getText().toString();
                String checkFeet = inputFeet.getText().toString();
                if(changeFields.isChecked()){
                    if(checkWeight.equals("")){
                        return;
                    }
                    else if(checkFeet.equals("")){
                        return;
                    }
                    else if(!no_Exercise.isChecked() && !light_Exercise.isChecked() && !mod_Exercise.isChecked() && !active_Exercise.isChecked()){
                        return;
                    }
                    double calories = calculateCalories();
                    inputCalories.setText("0");
                    sendToCarbonCalc(calories);
                }else{
                    String checkCal = inputCalories.getText().toString();
                    if((checkCal.equals("") || checkCal.equals("0")) && !changeFields.isChecked()) {
                    }
                    else{
                        double inputCals = Double.parseDouble(inputCalories.getText().toString());
                        sendToCarbonCalc(inputCals);
                    }
                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CalorieCalc.class);
        return intent;
    }

    void changeAgeCheckBox(CheckBox box1, CheckBox box2, CheckBox box3){
        box1.setChecked(false);
        box2.setChecked(false);
        box3.setChecked(false);
    }

    void changeToggleButtons(ToggleButton button1, ToggleButton button2, ToggleButton button3){
        button1.setChecked(false);
        button2.setChecked(false);
        button3.setChecked(false);
    }

    void enableConstraintFields(ConstraintLayout fieldsR){
        for ( int i = 0; i < fieldsR.getChildCount();  i++ ){
            View view = fieldsR.getChildAt(i);
            view.setEnabled(true);
        }
    }
    //
    void disableConstraintFields(ConstraintLayout fieldsR){
        for ( int i = 0; i < fieldsR.getChildCount();  i++ ){
            View view = fieldsR.getChildAt(i);
            view.setEnabled(false);
        }
    }

    void sendToCarbonCalc(double calorieValue){
        Bundle b = new Bundle();
        b.putDouble("calculatedCalories", calorieValue);
        Intent sendToCarbonCalc = new Intent(CalorieCalc.this, CalcActivity.class);
        sendToCarbonCalc.putExtras(b);
        startActivity(sendToCarbonCalc);
        finish();
    }

    double calculateCalories(){
        //Check if Male or Female, then send to appropriate function
        double maleBMR, femaleBMR;
        double dailyCalories;
        if(cb_male.isChecked()){
            maleBMR = genderCalculateBMR(true);
            dailyCalories = exerciseModifier(maleBMR);
        }
        else{
            femaleBMR = genderCalculateBMR(false);
            dailyCalories = exerciseModifier(femaleBMR);
        }
        return dailyCalories;
    }

    double genderCalculateBMR(boolean male){
        double weight = Double.parseDouble(inputWeight.getText().toString());
        double feet = Double.parseDouble(inputFeet.getText().toString());
        String checkInch = inputInches.getText().toString();
        double inches;
        if(checkInch.equals("")){
            inches = 0;
        }
        else{
            inches = Double.parseDouble(checkInch);
        }
        double age;
        inches = (feet*12.0) + inches;

        if(male == true){
            if(cb_young.isChecked()){
                age = ageYoungModm;
            }
            else if (cb_adult.isChecked()){
                age = ageAdultModm;
            }
            else if (cb_old.isChecked()){
                age = ageOldModm;
            }
            else{
                age = ageSeniorModm;
            }
            double bmr_male = 66 + (weightModm*weight) + (heightModm*inches) - age;
            return bmr_male;
        }
        else{
            if(cb_young.isChecked()){
                age = ageYoungModf;
            }
            else if (cb_adult.isChecked()){
                age = ageAdultModf;
            }
            else if (cb_old.isChecked()){
                age = ageOldModf;
            }
            else{
                age = ageSeniorModf;
            }
            double bmr_female = 655 + (weightModf*weight) + (heightModf*inches) - age;
            return bmr_female;
        }

    }

    double exerciseModifier(double bmr){
        double dailyCalories;
        if(no_Exercise.isChecked()){
            dailyCalories = bmr*BMR_sedentary;
        }
        else if(light_Exercise.isChecked()){
            dailyCalories = bmr*BMR_light_active;
        }
        else if(mod_Exercise.isChecked()){
            dailyCalories = bmr*BMR_mod_active;
        }
        else{
            dailyCalories = bmr*BMR_very_active;
        }
        return dailyCalories;
    }

}

