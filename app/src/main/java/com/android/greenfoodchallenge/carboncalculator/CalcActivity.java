package com.android.greenfoodchallenge.carboncalculator;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;


public class CalcActivity extends AppCompatActivity {

    TextView totalPercent;
    EditText inputNum1, inputNum2, inputNum3, inputNum4, inputNum5, inputNum6, inputNum7;
    String number1, number2, number3, number4, number5, number6, number7;
    private Button mButtonSubmit;
    private Button mButtonClear;
    private Button mButtonBack;
    private BottomNavigationView mBottomNavigation;
    double calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        Bundle storage = this.getIntent().getExtras();
        calories = storage.getDouble("calculatedCalories");

        ((EditText) findViewById(R.id.option1)).setText("0");
        ((EditText) findViewById(R.id.option2)).setText("0");
        ((EditText) findViewById(R.id.option3)).setText("0");
        ((EditText) findViewById(R.id.option4)).setText("0");
        ((EditText) findViewById(R.id.option5)).setText("0");
        ((EditText) findViewById(R.id.option6)).setText("0");
        ((EditText) findViewById(R.id.option7)).setText("0");
        ((EditText) findViewById(R.id.totalCal)).setText("");
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");

        inputNum1 = (EditText) findViewById(R.id.option1);
        inputNum2 = (EditText) findViewById(R.id.option2);
        inputNum3 = (EditText) findViewById(R.id.option3);;
        inputNum4 = (EditText) findViewById(R.id.option4);
        inputNum5 = (EditText) findViewById(R.id.option5);
        inputNum6 = (EditText) findViewById(R.id.option6);
        inputNum7 = (EditText) findViewById(R.id.option7);

        totalPercent = (TextView) findViewById(R.id.totalPerc);

        mButtonSubmit = (Button) findViewById(R.id.button_calculate);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSubmission();
            }
        });

        mButtonClear = (Button) findViewById(R.id.button_clear);
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetActivity();
            }
        });

        mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);

        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent goHome = new Intent(CalcActivity.this, MenuActivity.class);
                        finish();
                        startActivity(goHome, ActivityOptions.makeSceneTransitionAnimation(CalcActivity.this).toBundle());
                        break;

                    case R.id.nav_calculator:
                        Intent goMenu = new Intent(CalcActivity.this, MainMenu.class);
                        startActivity(goMenu);
                        break;

                    case R.id.nav_pledges:
                        break;

                }
                return false;
            }
        });

    }


    /*
    *   Send the user to the next Activity
    */
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CalcActivity.class);
        return intent;
    }


    /*
    *   Check the inputs the user submitted for calculation. If they are valid such that the sum,
    *   of all percentages add up to 100%, it will send them to the next activity. If not, it will
    *   not continue until the values have been fixed.
    */
    private void checkSubmission() {
        double num1, num2, num3, num4, num5, num6, num7;
        String val1 = inputNum1.getText().toString();
        if(val1.equals("")){
            num1 = 0;
        }
        else{
            num1 = Double.parseDouble(val1);
        }
        String val2 = inputNum2.getText().toString();
        if(val2.equals("")){
            num2 = 0;
        }
        else{
            num2 = Double.parseDouble(val2);
        }
        String val3 = inputNum3.getText().toString();
        if(val3.equals("")){
            num3 = 0;
        }
        else{
            num3 = Double.parseDouble(val3);
        }
        String val4 = inputNum4.getText().toString();
        if(val1.equals("")){
            num4 = 0;
        }
        else{
            num4 = Double.parseDouble(val4);
        }
        String val5 = inputNum5.getText().toString();
        if(val1.equals("")){
            num5 = 0;
        }
        else{
            num5 = Double.parseDouble(val5);
        }
        String val6 = inputNum6.getText().toString();
        if(val1.equals("")){
            num6 = 0;
        }
        else{
            num6 = Double.parseDouble(val6);
        }
        String val7 = inputNum7.getText().toString();
        if(val1.equals("")){
            num7 = 0;
        }
        else{
            num7 = Double.parseDouble(val7);
        }
        double sumOfValues = num1 + num2 + num3 + num4 + num5 + num6 + num7;

        totalPercent.setText(String.valueOf(sumOfValues));
        if (sumOfValues == 100) {

            number1 = String.valueOf(num1);
            number2 = String.valueOf(num2);
            number3 = String.valueOf(num3);
            number4 = String.valueOf(num4);
            number5 = String.valueOf(num5);
            number6 = String.valueOf(num6);
            number7 = String.valueOf(num7);

            ArrayList<String> listDouble = new ArrayList<String>();
            listDouble.add(number1);
            listDouble.add(number2);
            listDouble.add(number3);
            listDouble.add(number4);
            listDouble.add(number5);
            listDouble.add(number6);
            listDouble.add(number7);
            Bundle b = new Bundle();
            b.putStringArrayList("User's Input", listDouble);
            b.putDouble("dailyCalories", calories);
            Intent goToDisplay = new Intent(CalcActivity.this, Display.class);
            goToDisplay.putExtras(b);
            startActivity(goToDisplay);
        }
    }


    /*
    *   Will reset all EditText & TextView fields to their default values
    */
    private void resetActivity() {

        ((EditText) findViewById(R.id.option1)).setText("0");
        ((EditText) findViewById(R.id.option2)).setText("0");
        ((EditText) findViewById(R.id.option3)).setText("0");
        ((EditText) findViewById(R.id.option4)).setText("0");
        ((EditText) findViewById(R.id.option5)).setText("0");
        ((EditText) findViewById(R.id.option6)).setText("0");
        ((EditText) findViewById(R.id.option7)).setText("0");
        ((EditText) findViewById(R.id.totalCal)).setText("");
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");

    }
};


