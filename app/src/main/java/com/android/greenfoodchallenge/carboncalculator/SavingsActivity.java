package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
//Displays user specific pledge information
public class SavingsActivity extends AppCompatActivity {

    private double mCarbonFootprint;
    private double mCalories;
    private String mSavedCarbonResultString;
    private String mFormatSavedCarbonResultString;
    private ArrayList<String> userInputFoodPercentages;
    private TextView beefPercentage;
    private TextView chickenPercentage;
    private TextView turkeyPercentage;
    private TextView brocolliPercentage;
    private TextView tofuPercentage;
    private TextView eggPercentage;
    private TextView lentilsPercentage;
    private TextView text_view;
    private SeekBar seek_Bar;
    private BottomNavigationView mBottomNavigation;
    private boolean isVegetarian;
    private MealPlans mMealPlan = new MealPlans(mCarbonFootprint);

    public void seekBar(){
        seek_Bar = (SeekBar)findViewById(R.id.seekBar);
        text_view = (TextView)findViewById(R.id.resultDescription);
        mMealPlan.setCarbonFootprint(mCarbonFootprint);
        mMealPlan.calculateSavedCarbonFootprint();
        mSavedCarbonResultString = mMealPlan.doubleToString();
        mFormatSavedCarbonResultString = getResources().getString(R.string.saving_calculator_result);
        mFormatSavedCarbonResultString = String.format(mFormatSavedCarbonResultString, mSavedCarbonResultString);
        text_view.setText(mFormatSavedCarbonResultString);
        setTableValues(0);

        seek_Bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        mMealPlan.setMealPlan(progressValue);
                        mMealPlan.calculateSavedCarbonFootprint();
                        mSavedCarbonResultString = mMealPlan.doubleToString();
                        mFormatSavedCarbonResultString = getResources().getString(R.string.saving_calculator_result);
                        mFormatSavedCarbonResultString = String.format(mFormatSavedCarbonResultString, mSavedCarbonResultString);
                        text_view.setText(mFormatSavedCarbonResultString);
                        setTableValues(progressValue);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        getCalculatedExtras();
        seekBar();

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent goToHome = new Intent(SavingsActivity.this, MainMenu.class);
                        startActivity(goToHome, ActivityOptions.makeSceneTransitionAnimation(SavingsActivity.this).toBundle());
                        break;

                    case R.id.nav_calculator:
                        Intent goToCalculator = CalorieCalc.makeIntent(SavingsActivity.this);
                        startActivity(goToCalculator);
                        break;

                    case R.id.nav_pledges:
                        Intent goToPledges = ViewPledgeActivity.makeIntent(SavingsActivity.this);
                        startActivity(goToPledges);
                        break;

                }
                return false;
            }
        });

    }

    public void getCalculatedExtras(){
        Bundle calculatorData = this.getIntent().getExtras();
        mCarbonFootprint = calculatorData.getDouble("Calories - CalculatorActivity");
        mCalories = calculatorData.getDouble("Footprint - CalculatorActivity");
        userInputFoodPercentages = calculatorData.getStringArrayList("Input - CalculatorActivity");
    }

    public void setTableValues(int planChoice){
        beefPercentage = (TextView)findViewById(R.id.rowright1);
        chickenPercentage = (TextView)findViewById(R.id.rowright2);
        turkeyPercentage = (TextView)findViewById(R.id.rowright3);
        brocolliPercentage = (TextView)findViewById(R.id.rowright4);
        tofuPercentage = (TextView)findViewById(R.id.rowright5);
        eggPercentage = (TextView)findViewById(R.id.rowright6);
        lentilsPercentage = (TextView)findViewById(R.id.rowright7);

        if(planChoice == 0){
            beefPercentage.setText("5");
            chickenPercentage.setText("55");
            turkeyPercentage.setText("10");
            brocolliPercentage.setText("5");
            tofuPercentage.setText("10");
            eggPercentage.setText("5");
            lentilsPercentage.setText("10");

        }else if(planChoice == 1){
            beefPercentage.setText("0");
            chickenPercentage.setText("30");
            turkeyPercentage.setText("10");
            brocolliPercentage.setText("20");
            tofuPercentage.setText("15");
            eggPercentage.setText("10");
            lentilsPercentage.setText("15");

        }else if(planChoice == 2){
            beefPercentage.setText("0");
            chickenPercentage.setText("0");
            turkeyPercentage.setText("0");
            brocolliPercentage.setText("20");
            tofuPercentage.setText("40");
            eggPercentage.setText("30");
            lentilsPercentage.setText("10");

        }else if(planChoice == 3){
            beefPercentage.setText("0");
            chickenPercentage.setText("0");
            turkeyPercentage.setText("0");
            brocolliPercentage.setText("10");
            tofuPercentage.setText("40");
            eggPercentage.setText("0");
            lentilsPercentage.setText("50");

        }

    }

}