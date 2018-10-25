package com.android.greenfoodchallenge.carboncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;


public class SavingsActivity extends AppCompatActivity {

    private double mCarbonFootprint;
    private double mCalories;
    private String mSavedCarbonResultString;
    private String mFormatSavedCarbonResultString;
    private ArrayList<String> userInputFoodPercentages;


    private Button mBackButton;
    private Button mContinueButton;
    private SeekBar seek_Bar;
    private TextView text_view;

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

        mBackButton = (Button)findViewById(R.id.prevButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackButton.setText(Double.toString(mCarbonFootprint));
                finish();
            }
        });

        mContinueButton = (Button)findViewById(R.id.continueButton);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUserUnderstandingActivity = UserUnderstandingActivity.makeIntent(SavingsActivity.this, mCarbonFootprint);
                startActivity(goToUserUnderstandingActivity);
            }
        });

    }

    public void getCalculatedExtras(){
        Bundle calculatorData = this.getIntent().getExtras();
        mCarbonFootprint = calculatorData.getDouble("Footprint");
        mCalories = calculatorData.getDouble("Calorie");
        userInputFoodPercentages = calculatorData.getStringArrayList("User Data");
    }

}