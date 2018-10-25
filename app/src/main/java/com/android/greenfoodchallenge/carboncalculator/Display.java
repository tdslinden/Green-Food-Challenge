package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
    TextView prompt;
    Button footprintCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle storage = this.getIntent().getExtras();
        data = storage.getStringArrayList("User's Input");
        calories = storage.getDouble("dailyCalories");

        Calculator userCalculations = new Calculator(data, calories);

        // pie chart
        setUpChart();

        userFootprint = (TextView) findViewById(R.id.textbox1);
        prompt = (TextView) findViewById(R.id.textbox2);
        footprintCalculator = (Button) findViewById(R.id.button);

        footprint = userCalculations.totalFootprint();

        userFootprint.setText(getString(R.string.co2e1) + String.format("%.2f", footprint) + getString(R.string.co2e2));
        prompt.setText(R.string.prompt);
        footprintCalculator.setText(R.string.next);

        footprintCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFootprintCalculator();
            }
        });
    }

    public void openFootprintCalculator() {
        Bundle b = new Bundle();
        b.putDouble("calories", calories);
        b.putDouble("footprint", footprint);
        b.putStringArrayList("input", data);

        Intent goToSavings = new Intent(Display.this, SavingsActivity.class);
        goToSavings.putExtras(b);
        startActivity(goToSavings);
    }

      public void setUpChart() {
        ArrayList<String> groups = new ArrayList<>(Arrays.asList("Beef", "Pork", "Chicken", "Fish", "Eggs", "Beans", "Vegetables"));

        List<PieEntry> pieEntries = new ArrayList<>();
        Float temp;
        PieChart chart = findViewById(R.id.chart);

        for(int i = 0; i < data.size(); i++) {
            temp = Float.parseFloat(data.get(i));
            pieEntries.add(new PieEntry(temp, groups.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Your Diet");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.invalidate();
    }
}
