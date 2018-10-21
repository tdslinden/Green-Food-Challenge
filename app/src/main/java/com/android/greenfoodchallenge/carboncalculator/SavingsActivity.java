package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SavingsActivity extends AppCompatActivity {

    private double mCarbonFootprint = 12.34;
    private String savedCarbonResultString;
    private String formatSavedCarbonResultString;
    private Button mBackButton;
    private static SeekBar seek_Bar;
    private static TextView text_view;

    private MealPlans mMealPlan = new MealPlans(mCarbonFootprint);

    public void seekBar(){
        seek_Bar = (SeekBar)findViewById(R.id.seekBar);
        text_view = (TextView)findViewById(R.id.resultDescription);
        mMealPlan.calculateSavedCarbonFootprint();
        savedCarbonResultString = mMealPlan.doubleToString();
        formatSavedCarbonResultString = getResources().getString(R.string.saving_calculator_result);
        formatSavedCarbonResultString = String.format(formatSavedCarbonResultString, savedCarbonResultString);
        text_view.setText(formatSavedCarbonResultString);
        seek_Bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        mMealPlan.setMealPlan(progressValue);
                        mMealPlan.calculateSavedCarbonFootprint();
                        savedCarbonResultString = mMealPlan.doubleToString();
                        formatSavedCarbonResultString = getResources().getString(R.string.saving_calculator_result);
                        formatSavedCarbonResultString = String.format(formatSavedCarbonResultString, savedCarbonResultString);
                        text_view.setText(formatSavedCarbonResultString);
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
        seekBar();

        mBackButton = (Button)findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}