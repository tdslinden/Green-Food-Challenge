package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
*   -Need to add a line of text that shows the total percentage of all the inputs, and require the
*    user to make sure it adds up to 100% (Just for now).
*   -Also need to incorporate a back button to go back to the main menu
 */

public class CalcActivity extends AppCompatActivity {

    TextView totalPercent;
    EditText inputNum1, inputNum2, inputNum3, inputNum4, inputNum5, inputNum6;
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

        double checkVal1 = Double.valueOf(inputNum1.getText().toString());
        double checkVal2 = Double.valueOf(inputNum2.getText().toString());
        double checkVal3 = Double.valueOf(inputNum3.getText().toString());
        double checkVal4 = Double.valueOf(inputNum4.getText().toString());
        double checkVal5 = Double.valueOf(inputNum5.getText().toString());
        double checkVal6 = Double.valueOf(inputNum6.getText().toString());
        double sumOfValues = checkVal1 + checkVal2 + checkVal3 + checkVal4 + checkVal5 + checkVal6;
        totalPercent.setText(String.valueOf(sumOfValues));
        if (sumOfValues == 100) {

            number1 = inputNum1.getText().toString();
            number2 = inputNum2.getText().toString();
            number3 = inputNum3.getText().toString();
            number4 = inputNum4.getText().toString();
            number5 = inputNum5.getText().toString();
            number6 = inputNum6.getText().toString();

            ArrayList<String> listDouble = new ArrayList<String>();
            listDouble.add(number1);
            listDouble.add(number2);
            listDouble.add(number3);
            listDouble.add(number4);
            listDouble.add(number5);
            listDouble.add(number6);
            Bundle b = new Bundle();
            b.putStringArrayList("User's Input", listDouble);
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
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");
    }
};


