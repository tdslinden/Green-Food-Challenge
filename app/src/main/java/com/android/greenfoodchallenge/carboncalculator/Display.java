package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
    // test
    double calories = 2500;

    // to pass to the next activity
    double footprint = 0;

    TextView userFootprint;
    TextView prompt;
    Button footprintCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle storage = this.getIntent().getExtras();
        ArrayList<String> data = (ArrayList<String>) storage.getStringArrayList("User's Input");
//        double calories = storage.getDouble("User's Input");

        Calculator userCalculations = new Calculator(data, calories);

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
    }
}
