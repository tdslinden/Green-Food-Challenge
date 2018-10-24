package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/*
*   -Need to add a line of text that shows the total percentage of all the inputs, and require the
*    user to make sure it adds up to 100% (Just for now).
*   -Also need to incorporate a back button to go back to the main menu
 */

public class CalcActivity extends AppCompatActivity {

    TextView totalPercent;
    EditText inputNum1, inputNum2, inputNum3, inputNum4, inputNum5, inputNum6, inputCal;
    String number1, number2, number3, number4, number5, number6;
    private Button mButtonSubmit;
    private Button mButtonClear;
    private Button mButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");

        inputNum1 = (EditText) findViewById(R.id.option1);
        inputNum2 = (EditText) findViewById(R.id.option2);
        inputNum3 = (EditText) findViewById(R.id.option3);
        inputNum4 = (EditText) findViewById(R.id.option4);
        inputNum5 = (EditText) findViewById(R.id.option5);
        inputNum6 = (EditText) findViewById(R.id.option6);
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
     *   Check the inputs the user has put in. If they2020 are valid, send them to the next activity. If not, ask them to reenter values.
     */
    private void checkSubmission() {
        double val1 = Double.valueOf(inputNum1.getText().toString());
        double val2 = Double.valueOf(inputNum2.getText().toString());
        double val3 = Double.valueOf(inputNum3.getText().toString());
        double val4 = Double.valueOf(inputNum4.getText().toString());
        double val5 = Double.valueOf(inputNum5.getText().toString());
        double val6 = Double.valueOf(inputNum6.getText().toString());
        double sumOfValues = val1 + val2 + val3 + val4 + val5 + val6;

        totalPercent.setText(String.valueOf(sumOfValues));
        if (sumOfValues == 100) {

            number1 = inputNum1.getText().toString();
            number2 = inputNum2.getText().toString();
            number3 = inputNum3.getText().toString();
            number4 = inputNum4.getText().toString();
            number5 = inputNum5.getText().toString();
            number6 = inputNum6.getText().toString();

            double calories = Double.valueOf(inputCal.getText().toString());

            ArrayList<String> listDouble = new ArrayList<String>();
            listDouble.add(number1);
            listDouble.add(number2);
            listDouble.add(number3);
            listDouble.add(number4);
            listDouble.add(number5);
            listDouble.add(number6);
            Bundle b = new Bundle();
            b.putStringArrayList("arrayList", listDouble);
            b.putDouble("dailyCalories", calories);
            Intent goToDisplay = new Intent(CalcActivity.this, Display.class);
            goToDisplay.putExtras(b);
            startActivity(goToDisplay);
        }
    }

    /*
     *   Will reset all text fields and spinners to their default values
     */

    private void resetActivity() {
        ((EditText) findViewById(R.id.option1)).setText("");
        ((EditText) findViewById(R.id.option2)).setText("");
        ((EditText) findViewById(R.id.option3)).setText("");
        ((EditText) findViewById(R.id.option4)).setText("");
        ((EditText) findViewById(R.id.option5)).setText("");
        ((EditText) findViewById(R.id.option6)).setText("");
        ((EditText) findViewById(R.id.totalCal)).setText("");
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");
    }

    public void checkUserInputs() {

        String str1 = inputNum1.getText().toString();
        String str2 = inputNum2.getText().toString();
        String str3 = inputNum3.getText().toString();
        String str4 = inputNum4.getText().toString();
        String str5 = inputNum5.getText().toString();
        String str6 = inputNum6.getText().toString();
        String str7 = inputCal.getText().toString();
        if (str1.matches("") || str2.matches("") || str3.matches("") || str4.matches("") || str5.matches("") || str6.matches("") || str7.matches("")) {

            Toast.makeText(this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            checkSubmission();
        }
    }
};


