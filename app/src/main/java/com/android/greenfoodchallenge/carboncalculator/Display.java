package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Display extends AppCompatActivity {
//    Bundle storage = this.getIntent().getExtras();
//    ArrayList<String> data = (ArrayList<String>) Objects.requireNonNull(storage).getStringArrayList("User's Input");
//    double calories = storage.getDouble("User's Input");

    // test
    ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
    double calories = 2500;

    // to pass to the next activity
    double footprint = 0;

    public Calculator userCalculations = new Calculator(data, calories);

    TextView userFootprint;
    TextView prompt;
    Button footprintCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

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
        b.putDouble("User Data", footprint);

        Intent goToSavings = new Intent(Display.this, SavingsActivity.class);
        goToSavings.putExtras(b);
        startActivity(goToSavings);

//        // test
//        this.finish();
    }
}
