package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

// After estimating the user’s current food carbon footprint, the carbon footprint calculator
// should offer a way to see how much the user could save by changing the relative
// proportions of their diet.

public class SavingsActivity extends AppCompatActivity {

    private Button mButtonMenu;
    private static SeekBar seek_Bar;
    private static TextView text_view;

    public void seekBar(){
        seek_Bar = (SeekBar)findViewById(R.id.seekBar);
        text_view = (TextView)findViewById(R.id.textView);
        text_view .setText("Coverage: " + seek_Bar.getProgress() + " / " + seek_Bar.getMax());
        seek_Bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        text_view .setText("Coverage: " + progress + " / " + seek_Bar.getMax());
                        Toast.makeText(SavingsActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(SavingsActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_view .setText("Coverage: " + progressValue + " / " + seek_Bar.getMax());
                        Toast.makeText(SavingsActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        seekBar();

        mButtonMenu = (Button)findViewById(R.id.button_menu);
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(SavingsActivity.this, MenuActivity.class);
                startActivity(goToMenu);
            }
        });
    }
}