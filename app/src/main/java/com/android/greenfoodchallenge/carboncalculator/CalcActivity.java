package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class CalcActivity extends AppCompatActivity {

    TextView totalPercent;
    EditText inputNum1, inputNum2, inputNum3, inputNum4, inputNum5, inputNum6, inputNum7, inputCal;
    String number1, number2, number3, number4, number5, number6, number7;
    private Button mButtonSubmit;
    private Button mButtonClear;
    private Button mButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ((EditText) findViewById(R.id.option1)).setText("");
        ((EditText) findViewById(R.id.option2)).setText("");
        ((EditText) findViewById(R.id.option3)).setText("");
        ((EditText) findViewById(R.id.option4)).setText("");
        ((EditText) findViewById(R.id.option5)).setText("");
        ((EditText) findViewById(R.id.option6)).setText("");
        ((EditText) findViewById(R.id.option7)).setText("");
        ((EditText) findViewById(R.id.totalCal)).setText("");
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");

        inputNum1 = (EditText) findViewById(R.id.option1);
        inputNum2 = (EditText) findViewById(R.id.option2);
        inputNum3 = (EditText) findViewById(R.id.option3);;
        inputNum4 = (EditText) findViewById(R.id.option4);
        inputNum5 = (EditText) findViewById(R.id.option5);
        inputNum6 = (EditText) findViewById(R.id.option6);
        inputNum7 = (EditText) findViewById(R.id.option7);
        inputCal = (EditText) findViewById(R.id.totalCal);

        totalPercent = (TextView) findViewById(R.id.totalPerc);

        mButtonSubmit = (Button) findViewById(R.id.button_calculate);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserInputs();
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
        double val1 = Double.valueOf(inputNum1.getText().toString());
        double val2 = Double.valueOf(inputNum2.getText().toString());
        double val3 = Double.valueOf(inputNum3.getText().toString());
        double val4 = Double.valueOf(inputNum4.getText().toString());
        double val5 = Double.valueOf(inputNum5.getText().toString());
        double val6 = Double.valueOf(inputNum6.getText().toString());
        double val7 = Double.valueOf(inputNum7.getText().toString());
        double sumOfValues = val1 + val2 + val3 + val4 + val5 + val6 + val7;

        totalPercent.setText(String.valueOf(sumOfValues));
        if (sumOfValues == 100) {

            number1 = inputNum1.getText().toString();
            number2 = inputNum2.getText().toString();
            number3 = inputNum3.getText().toString();
            number4 = inputNum4.getText().toString();
            number5 = inputNum5.getText().toString();
            number6 = inputNum6.getText().toString();
            number7 = inputNum7.getText().toString();

            double calories = Double.valueOf(inputCal.getText().toString());
            if(calories == 0){
                Toast.makeText(this, "Daily Calorie intake must be greater than zero!", Toast.LENGTH_SHORT).show();
                return;
            }

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

        ((EditText) findViewById(R.id.option1)).setText("");
        ((EditText) findViewById(R.id.option2)).setText("");
        ((EditText) findViewById(R.id.option3)).setText("");
        ((EditText) findViewById(R.id.option4)).setText("");
        ((EditText) findViewById(R.id.option5)).setText("");
        ((EditText) findViewById(R.id.option6)).setText("");
        ((EditText) findViewById(R.id.option7)).setText("");
        ((EditText) findViewById(R.id.totalCal)).setText("");
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");

    }


    /*
    *   Checks all EditText fields to make sure there none of them are empty. If atleast one is
    *   empty, the user will be notified to fill in all the fields.  Once all fields are filled
    *   it carries over to checkSubmission().
    */
    public void checkUserInputs() {

        String str1 = inputNum1.getText().toString();
        String str2 = inputNum2.getText().toString();
        String str3 = inputNum3.getText().toString();
        String str4 = inputNum4.getText().toString();
        String str5 = inputNum5.getText().toString();
        String str6 = inputNum6.getText().toString();
        String str7 = inputNum6.getText().toString();
        String str8 = inputCal.getText().toString();
        if (str1.matches("") || str2.matches("") || str3.matches("") || str4.matches("") || str5.matches("") || str6.matches("") || str7.matches("") || str8.matches("")) {

            Toast.makeText(this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            checkSubmission();
        }
    }
};


