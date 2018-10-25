package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

/*
*
* Quantifies the effects of the user's diet on CO2e production
* Gives a visual comparison of the effects of the user's diet compared to
* the goal levels and the current average
*
 */

public class UserUnderstandingActivity extends AppCompatActivity {

    private static final String CARBON_FOOTPRINT = "UserUnderstandingActivity - carbonFootprint";

    public static Intent makeIntent(Context context){
        Intent intent =new Intent(context, UserUnderstandingActivity.class);
        return intent;
    }

    TextView TextResult;
    Button ButtonMenu;
    HorizontalBarChart BarChart;

    private double mCarbonFootprint;
    private double mCalories;
    private ArrayList<String> userInputFoodPercentages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_understanding);

        TextResult = (TextView)findViewById(R.id.userUnderstandingResultText);
        ButtonMenu = (Button)findViewById(R.id.button);
        BarChart = (HorizontalBarChart)findViewById(R.id.barChart);

        getCalculatedExtras();

        //Once the SavingActivity is done this will be replaced

        Resources res = getResources();
        EquivalenceCalculator calc= new EquivalenceCalculator();

        //Build car equivalence text
        String startText;
        String endText;
        //UI text depends on if the userCO2e>typical CO2e
        boolean isOver=calc.isOverAverage(mCarbonFootprint);

        if(isOver){
            startText = res.getString(R.string.userUnderstandingTitleOverAverage);
            endText = res.getString(R.string.userUnderstandingOverAverage);
        }
        else {
            startText = res.getString(R.string.userUnderstandingTitleUnderAverage);
            endText = res.getString(R.string.userUnderstandingUnderAverage);
        }

        String resultText = res.getString(R.string.userUnderstandingTitle, startText, Integer.toString(calc.getCarEquivalence(mCarbonFootprint)),endText);
        TextResult.setText(resultText);

        /*
        *
        * Bar chart formatting
        *
        */

        //Bar chart data
        ArrayList<BarEntry> barValues = new ArrayList<>();
        //Vancouver Average
        barValues.add(new BarEntry((float)1, (float)1.5));
        //User value
        barValues.add(new BarEntry((float)2, (float)mCarbonFootprint));
        //Goal
        barValues.add(new BarEntry((float)3, (float)1));

        //Set bar values
        BarDataSet set = new BarDataSet(barValues, "CO2E");
        BarData data = new BarData(set);
        BarChart.setData(data);
        BarChart.getDescription().setEnabled(false);

        //Set bar colors
        String userColor=calc.getUserColor(mCarbonFootprint);
        int[] colorArray = new int[]{Color.parseColor("#8B0000"), Color.parseColor(userColor), Color.parseColor("#008000")};
        set.setColors(colorArray);

        //Axis formatting
        XAxis xAxis = BarChart.getXAxis();
        xAxis.setDrawGridLines(false);

        //Blank string here is required for proper formatting of the axis
        String[] xlabels = {"","Avg ", "You ", "Goal"};
        BarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xlabels));

        //xAxis.setDrawLabels(false);

        YAxis leftAxis = BarChart.getAxisLeft();
        leftAxis.setSpaceBottom(45);
        leftAxis.setSpaceTop(50);
        YAxis rightAxis = BarChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        /// /Legend formatting
        Legend l = BarChart.getLegend();
        l.setEnabled(false);


        //Menu Button
        ButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSavingsActivity();
            }
        });

    }

    public void getCalculatedExtras(){
        Bundle calculatorData = this.getIntent().getExtras();
        mCarbonFootprint = calculatorData.getDouble("footprint");
        mCalories = calculatorData.getDouble("calories");
        userInputFoodPercentages = calculatorData.getStringArrayList("input");
    }

    public void openSavingsActivity() {
        Bundle b = new Bundle();
        b.putDouble("Calories - CalculatorActivity", mCalories);
        b.putDouble("Footprint - CalculatorActivity", mCarbonFootprint);
        b.putStringArrayList("Input - CalculatorActivity", userInputFoodPercentages);

        Intent goToSavings = new Intent(UserUnderstandingActivity.this, SavingsActivity.class);
        goToSavings.putExtras(b);
        startActivity(goToSavings);
    }
}
