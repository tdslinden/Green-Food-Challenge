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

    private TextView beefPercentage;
    private TextView chickenPercentage;
    private TextView turkeyPercentage;
    private TextView brocolliPercentage;
    private TextView tofuPercentage;
    private TextView eggPercentage;
    private TextView lentislPercentage;

//    private TextView beefPercentage = (TextView)findViewById(R.id.rowright1);
//    private TextView chickenPercentage = (TextView)findViewById(R.id.rowright2);
//    private TextView turkeyPercentage = (TextView)findViewById(R.id.rowright3);
//    private TextView brocolliPercentage = (TextView)findViewById(R.id.rowright4);
//    private TextView tofuPercentage = (TextView)findViewById(R.id.rowright5);
//    private TextView eggPercentage = (TextView)findViewById(R.id.rowright6);
//    private TextView lentislPercentage = (TextView)findViewById(R.id.rowright7);

    private Button mBackButton;
    private Button mMenuButton;
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

        mBackButton = (Button)findViewById(R.id.prevButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMenuButton = (Button)findViewById(R.id.menuButton);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(SavingsActivity.this, MenuActivity.class);
                startActivity(goToMenu);
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
        lentislPercentage = (TextView)findViewById(R.id.rowright7);

        if(planChoice == 0){
            beefPercentage.setText("5");
            chickenPercentage.setText("55");
            turkeyPercentage.setText("10");
            brocolliPercentage.setText("5");
            tofuPercentage.setText("10");
            eggPercentage.setText("5");
            lentislPercentage.setText("10");

        }else if(planChoice == 1){
            beefPercentage.setText("0");
            chickenPercentage.setText("30");
            turkeyPercentage.setText("10");
            brocolliPercentage.setText("20");
            tofuPercentage.setText("15");
            eggPercentage.setText("10");
            lentislPercentage.setText("15");

        }else if(planChoice == 2){
            beefPercentage.setText("0");
            chickenPercentage.setText("0");
            turkeyPercentage.setText("0");
            brocolliPercentage.setText("20");
            tofuPercentage.setText("40");
            eggPercentage.setText("30");
            lentislPercentage.setText("10");

        }else if(planChoice == 3){
            beefPercentage.setText("0");
            chickenPercentage.setText("0");
            turkeyPercentage.setText("0");
            brocolliPercentage.setText("10");
            tofuPercentage.setText("40");
            eggPercentage.setText("0");
            lentislPercentage.setText("50");

        }

    }

}