package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/*
*   -Need to add a line of text that shows the total percentage of all the inputs, and require the
*    user to make sure it adds up to 100% (Just for now).
*   -Also need to incorporate a back button to go back to the main menu
*   -Need to preset the spinners to actual food items for testing purposes
*   -Change the spinners so they have unique food groups
 */

public class CalcActivity extends AppCompatActivity {

    EditText inputNum1, inputNum2, inputNum3, inputNum4, inputNum5;
    String number1, number2, number3, number4, number5;
    private Button mButtonSubmit;
    private Button mButtonClear;

    Spinner foodList, foodList2, foodList3, foodList4, foodList5;

    ArrayList<String> foods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        foodList = (Spinner) findViewById(R.id.foodList);
        foodList2 = (Spinner) findViewById(R.id.foodList2);
        foodList3 = (Spinner) findViewById(R.id.foodList3);
        foodList4 = (Spinner) findViewById(R.id.foodList4);
        foodList5 = (Spinner) findViewById(R.id.foodList5);

        foods.add("Select an item");
        foods.add("Beef");
        foods.add("Pork");
        foods.add("Chicken");
        foods.add("Fish");
        foods.add("Lentils");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(CalcActivity.this, android.R.layout.simple_spinner_dropdown_item, foods);

        foodList.setAdapter(adapter1);
        foodList2.setAdapter(adapter1);
        foodList3.setAdapter(adapter1);
        foodList4.setAdapter(adapter1);
        foodList5.setAdapter(adapter1);

//        ((ArrayAdapter<String>) foodList.getAdapter()).remove((String)foodList.getSelectedItem());
//        ((ArrayAdapter<String>) foodList.getAdapter()).notifyDataSetChanged();
//        ((ArrayAdapter<String>) foodList2.getAdapter()).remove((String)foodList2.getSelectedItem());
//        ((ArrayAdapter<String>) foodList2.getAdapter()).notifyDataSetChanged();

        inputNum1 = (EditText) findViewById(R.id.option1);
        inputNum2 = (EditText) findViewById(R.id.option2);
        inputNum3 = (EditText) findViewById(R.id.option3);
        inputNum4 = (EditText) findViewById(R.id.option4);
        inputNum5 = (EditText) findViewById(R.id.option5);

        foodList = (Spinner) findViewById(R.id.foodList);
        foodList2 = (Spinner) findViewById(R.id.foodList2);
        foodList3 = (Spinner) findViewById(R.id.foodList3);

        mButtonSubmit = (Button) findViewById(R.id.button_submit);
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

    }



    /*
     *   Send the user to the next Activity
     */

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CalcActivity.class);
        return intent;
    }

    /*
     *   Check the inputs the user has put in. If they are valid, send them to the next activity. If not, ask them to reenter values.
     */
    private void checkSubmission() {
        double checkVal1 = Double.valueOf(inputNum1.getText().toString());
        double checkVal2 = Double.valueOf(inputNum2.getText().toString());
        double checkVal3 = Double.valueOf(inputNum3.getText().toString());
        double checkVal4 = Double.valueOf(inputNum4.getText().toString());
        double checkVal5 = Double.valueOf(inputNum5.getText().toString());
        if (checkVal1 + checkVal2 + checkVal3 + checkVal4 + checkVal5 <= 100) {

            number1 = inputNum1.getText().toString();
            number2 = inputNum2.getText().toString();
            number3 = inputNum3.getText().toString();
            number4 = inputNum4.getText().toString();
            number5 = inputNum5.getText().toString();

            ArrayList<String> listDouble = new ArrayList<String>();
            listDouble.add(number1);
            listDouble.add(number2);
            listDouble.add(number3);
            listDouble.add(number4);
            listDouble.add(number5);
            Bundle b = new Bundle();
            b.putStringArrayList("arrayList", listDouble);
            Intent goToDisplay = new Intent(CalcActivity.this, SavingsActivity.class);
            goToDisplay.putExtras(b);
            startActivity(goToDisplay);
        } else {
            showToast("total of all % must be less then 100%!");
        }
    }

    /*
     *   Will reset all text fields and spinners to their default values
     */
    private void resetActivity() {
        foodList.setSelection(0);
        foodList2.setSelection(0);
        foodList3.setSelection(0);
        foodList4.setSelection(0);
        foodList5.setSelection(0);
        ((EditText) findViewById(R.id.option1)).setText("");
        ((EditText) findViewById(R.id.option2)).setText("");
        ((EditText) findViewById(R.id.option3)).setText("");
        ((EditText) findViewById(R.id.option4)).setText("");
        ((EditText) findViewById(R.id.option5)).setText("");
    }

    private void showToast(String text) {
        Toast.makeText(CalcActivity.this, text, Toast.LENGTH_SHORT).show();
    }

};


