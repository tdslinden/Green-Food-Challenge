package com.android.greenfoodchallenge.carboncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

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
    private TextView text_view, explainDiet;
    private SeekBar seek_Bar;
    private BottomNavigationView mBottomNavigation;
    private MealPlans mMealPlan = new MealPlans(mCarbonFootprint);
    private ImageView meatIcon, chickenIcon, veggieIcon, veganIcon;
    private Button mCloseButton;

    public void seekBar(){
        meatIcon = (ImageView)findViewById(R.id.meat_eater_icon);
        chickenIcon = (ImageView)findViewById(R.id.low_meat_icon);
        veggieIcon = (ImageView)findViewById(R.id.vegetarian_icon);
        veganIcon = (ImageView)findViewById(R.id.vegan_icon);
        seek_Bar = (SeekBar)findViewById(R.id.seekBar);
        text_view = (TextView)findViewById(R.id.resultDescription);
        explainDiet = (TextView) findViewById(R.id.diet_explanation);
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

        mCloseButton = (Button) findViewById(R.id.button_back);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent goToHome = new Intent(SavingsActivity.this, HomeDashboard.class);
                goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                goToHome.addFlags(goToHome.FLAG_ACTIVITY_CLEAR_TASK);
                goToHome.addFlags(goToHome.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goToHome);
                overridePendingTransition(0,0);
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
            meatIcon.setVisibility(View.VISIBLE);
            chickenIcon.setVisibility(View.GONE);
            veggieIcon.setVisibility(View.GONE);
            veganIcon.setVisibility(View.GONE);
            explainDiet.setText(R.string.diet_meat_eater);

        }else if(planChoice == 1){
            beefPercentage.setText("0");
            chickenPercentage.setText("30");
            turkeyPercentage.setText("10");
            brocolliPercentage.setText("20");
            tofuPercentage.setText("15");
            eggPercentage.setText("10");
            lentilsPercentage.setText("15");
            meatIcon.setVisibility(View.GONE);
            chickenIcon.setVisibility(View.VISIBLE);
            veggieIcon.setVisibility(View.GONE);
            veganIcon.setVisibility(View.GONE);
            explainDiet.setText(R.string.diet_low_meat);

        }else if(planChoice == 2){
            beefPercentage.setText("0");
            chickenPercentage.setText("0");
            turkeyPercentage.setText("0");
            brocolliPercentage.setText("20");
            tofuPercentage.setText("40");
            eggPercentage.setText("30");
            lentilsPercentage.setText("10");
            meatIcon.setVisibility(View.GONE);
            chickenIcon.setVisibility(View.GONE);
            veggieIcon.setVisibility(View.VISIBLE);
            veganIcon.setVisibility(View.GONE);
            explainDiet.setText(R.string.diet_vegetarian);

        }else if(planChoice == 3){
            beefPercentage.setText("0");
            chickenPercentage.setText("0");
            turkeyPercentage.setText("0");
            brocolliPercentage.setText("10");
            tofuPercentage.setText("40");
            eggPercentage.setText("0");
            lentilsPercentage.setText("50");
            meatIcon.setVisibility(View.GONE);
            chickenIcon.setVisibility(View.GONE);
            veggieIcon.setVisibility(View.GONE);
            veganIcon.setVisibility(View.VISIBLE);
            explainDiet.setText(R.string.diet_vegan);
        }

    }

}
