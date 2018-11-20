package com.android.greenfoodchallenge.carboncalculator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Display extends AppCompatActivity {
    // to pass to the next activity
    double footprint = 0;
    ArrayList<String> data;
    double calories = 0;

    TextView userFootprint;

    Button mButtonSavings;

    //User Understanding
    TextView TextResult;
    Button ButtonMenu;
    HorizontalBarChart BarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        // gets data from the last activity
        Bundle storage = this.getIntent().getExtras();
        data = storage.getStringArrayList("User's Input");
        calories = storage.getDouble("dailyCalories");

        Calculator userCalculations = new Calculator(data, calories);

        // pie chart
        setUpChart();

        // initialize the text views and buttons
        userFootprint = findViewById(R.id.user_footprint);
        mButtonSavings = findViewById(R.id.button_to_savings);
        mButtonSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSavingsCalculator();
            }
        });

        footprint = userCalculations.totalFootprint();

        // sets the textview and buttons
        userFootprint.setText(getString(R.string.co2e1) + String.format("%.2f", footprint) + getString(R.string.co2e2));
        //prompt.setText(R.string.prompt);

        // on click, open savings calculator
        //UU
        TextResult = (TextView)findViewById(R.id.userUnderstandingResultText);
        ButtonMenu = (Button)findViewById(R.id.button);
        BarChart = (HorizontalBarChart)findViewById(R.id.barChart);
        BarChart.setTouchEnabled(false);

        //Once the SavingActivity is done this will be replaced

        Resources res = getResources();
        EquivalenceCalculator calc= new EquivalenceCalculator();

        //Build car equivalence text
        String startText;
        String endText;
        //UI text depends on if the userCO2e>typical CO2e
        boolean isOver=calc.isOverAverage(footprint);

        if(isOver){
            startText = res.getString(R.string.userUnderstandingTitleOverAverage);
            endText = res.getString(R.string.userUnderstandingOverAverage);
        }
        else {
            startText = res.getString(R.string.userUnderstandingTitleUnderAverage);
            endText = res.getString(R.string.userUnderstandingUnderAverage);
        }

        String resultText = res.getString(R.string.userUnderstandingTitle, startText, Integer.toString(calc.getCarEquivalence((footprint/1000))),endText);
        TextResult.setText(resultText);

        /*
         * Bar chart formatting
         */

        //Bar chart data
        ArrayList<BarEntry> barValues = new ArrayList<>();
        //Vancouver Average
        barValues.add(new BarEntry((float)1, (float)1.5));
        //User value
        barValues.add(new BarEntry((float)2, (float)(footprint/1000)));
        //Goal
        barValues.add(new BarEntry((float)3, (float)1));

        //Set bar values
        BarDataSet set = new BarDataSet(barValues, "CO2E");
        BarData data = new BarData(set);
        BarChart.setData(data);
        BarChart.getDescription().setEnabled(false);

        //Set bar colors
        String userColor=calc.getUserColor((footprint/1000));
        int[] colorArray = new int[]{Color.parseColor("#DC143C"), Color.parseColor(userColor), Color.parseColor("#FF00FF73")};
        set.setColors(colorArray);
        set.setValueTextSize((float)10);

        //Axis formatting
        XAxis xAxis = BarChart.getXAxis();
        xAxis.setDrawGridLines(false);

        //Blank string here is required for proper formatting of the axis
        String[] xlabels = {"","Average ", "You ", "Goal"};
        BarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xlabels));

        //xAxis.setDrawLabels(false);
        YAxis leftAxis = BarChart.getAxisLeft();
        leftAxis.setSpaceBottom(45);
        leftAxis.setSpaceTop(50);
        YAxis rightAxis = BarChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        BarChart.animateY(1250, Easing.EasingOption.EaseInOutCubic);

        /// /Legend formatting
        Legend l = BarChart.getLegend();
        l.setEnabled(false);
    }


//     opens savings activity
    public void openSavingsCalculator() {
        Bundle b = new Bundle();
        b.putDouble("Calories - CalculatorActivity", calories);
        b.putDouble("Footprint - CalculatorActivity", footprint);
        b.putStringArrayList("Input - CalculatorActivity", data);

        Intent goToSavings = new Intent(Display.this, SavingsActivity.class);
        goToSavings.putExtras(b);
        startActivity(goToSavings,ActivityOptions.makeSceneTransitionAnimation(Display.this).toBundle());
    }

    // setting up pie chart
    // ref: https://www.youtube.com/watch?v=iS7EgKnyDeY
    public void setUpChart() {
        ArrayList<String> groups = new ArrayList<>(Arrays.asList(getString(R.string.calc_rMeat), getString(R.string.calc_poultry), getString(R.string.calc_seafood),
                                    getString(R.string.calc_dairy), getString(R.string.calc_grain), getString(R.string.calc_fruit), getString(R.string.food_vegetables)));

        List<PieEntry> pieEntries = new ArrayList<>();
        Float temp;
        PieChart chart = findViewById(R.id.chart);

        for(int i = 0; i < data.size(); i++) {
            temp = Float.parseFloat(data.get(i));
            if(temp != 0){
                pieEntries.add(new PieEntry(temp, groups.get(i)));
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Your Diet");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(10f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        chart.setDrawHoleEnabled(true);
        chart.animateY(1250, Easing.EasingOption.EaseInOutCubic);

        chart.setData(data);
        chart.invalidate();
        //Remove description text from the chart
        chart.getDescription().setEnabled(false);
        Legend p = chart.getLegend();
        p.setEnabled(false);
    }
}
