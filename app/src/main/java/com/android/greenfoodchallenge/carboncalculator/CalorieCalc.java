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
import android.widget.Toast;
import android.widget.ToggleButton;

public class CalorieCalc extends AppCompatActivity {

    EditText inputCalories, inputWeight, inputFeet, inputInches;
    private Button mButtonContinue;
    private ToggleButton no_Exercise, light_Exercise, mod_Exercise, active_Exercise;
    private CheckBox cb_male, cb_female, cb_young, cb_adult, cb_old, cb_senior;
    Switch changeFields;
    LinearLayout inputFields, check1, check2, weight, height;
    RelativeLayout button1, button2;
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

        mButtonContinue = (Button) findViewById(R.id.button_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkWeight = inputWeight.getText().toString();
                String checkFeet = inputFeet.getText().toString();
                if(changeFields.isChecked()){
                    if(checkWeight.equals("")){
                        Toast.makeText(CalorieCalc.this, "A Weight input is required!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(checkFeet.equals("")){
                        Toast.makeText(CalorieCalc.this, "A Height input is required!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(light_Exercise.isChecked()){
                        Toast.makeText(CalorieCalc.this, "Please choose your level of activity!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double calories = calculateCalories();
                    Bundle b = new Bundle();
                    b.putDouble("calculatedCalories", calories);
                    Intent sendToCarbonCalc = new Intent(CalorieCalc.this, CalcActivity.class);
                    sendToCarbonCalc.putExtras(b);
                    startActivity(sendToCarbonCalc);
                }
                String checkCal = inputCalories.getText().toString();
                if(checkCal.equals("") && !changeFields.isChecked()) {
                    Toast.makeText(CalorieCalc.this, "A Calorie input is required!", Toast.LENGTH_SHORT).show();
                }
                else{
                    double inputCals = Double.parseDouble(inputCalories.getText().toString());
                    Bundle b = new Bundle();
                    b.putDouble("calculatedCalories", inputCals);
                    Intent sendToCarbonCalc = new Intent(CalorieCalc.this, CalcActivity.class);
                    sendToCarbonCalc.putExtras(b);
                    startActivity(sendToCarbonCalc);
                }
            }
        });

    };

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CalorieCalc.class);
        return intent;
    }

    double calculateCalories(){
       //Check if Male or Female, then send to appropriate function
        double maleBMR, femaleBMR;
        double dailyCalories;
        if(cb_male.isChecked()){
            maleBMR = maleCalculateBMR();
            dailyCalories = exerciseModifier(maleBMR);
        }
        else{
            femaleBMR = femaleCalculateBMR();
            dailyCalories = exerciseModifier(femaleBMR);
        }
        return dailyCalories;
    }

    double maleCalculateBMR(){
        double weight = Double.parseDouble(inputWeight.getText().toString());
        double feet = Double.parseDouble(inputFeet.getText().toString());
        double inches = Double.parseDouble(inputInches.getText().toString());
        if(inputInches.equals("")){
            inches = 0;
        }
        double age;
        inches = (feet*12) + inches;
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

    double femaleCalculateBMR(){
        double weight = Double.parseDouble(inputWeight.getText().toString());
        double feet = Double.parseDouble(inputFeet.getText().toString());
        double inches = Double.parseDouble(inputInches.getText().toString());
        double age;
        inches = (feet*12) + inches;
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

    double exerciseModifier(double bmr){
        double dailyCalories;
        if(no_Exercise.isChecked()){
            dailyCalories = bmr*BMR_sedentary;
            Toast.makeText(CalorieCalc.this, "Please choose your level of activity!", Toast.LENGTH_SHORT).show();
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
