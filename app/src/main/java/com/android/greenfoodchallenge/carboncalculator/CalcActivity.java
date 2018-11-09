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
        num1 = checkInputs(inputNum1);
        num2 = checkInputs(inputNum2);
        num3 = checkInputs(inputNum3);
        num4 = checkInputs(inputNum4);
        num5 = checkInputs(inputNum5);
        num6 = checkInputs(inputNum6);
        num7 = checkInputs(inputNum7);

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

    double checkInputs(EditText value){
        String check = value.getText().toString();
        if(check.equals("")){
            return 0;
        }
        else{
            return Double.parseDouble(check);
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
        ((TextView) findViewById(R.id.totalPerc)).setText("0.0");

    }
};


