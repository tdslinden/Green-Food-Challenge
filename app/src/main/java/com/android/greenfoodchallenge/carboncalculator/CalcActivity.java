package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CalcActivity extends AppCompatActivity {

    EditText inputNum1, inputNum2, inputNum3;
    String number1, number2, number3;
    private Button mButtonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        inputNum1 = (EditText) findViewById(R.id.option1);
        inputNum2 = (EditText) findViewById(R.id.option2);
        inputNum3 = (EditText) findViewById(R.id.option3);

        mButtonSave = (Button)findViewById(R.id.button_submit);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1 = inputNum1.getText().toString();
                number2 = inputNum2.getText().toString();
                number3 = inputNum3.getText().toString();
                showToast(String.valueOf(number1));
                showToast(String.valueOf(number2));
                showToast(String.valueOf(number3));

                ArrayList<String> listDouble = new ArrayList<String>();
                listDouble.add(number1);
                listDouble.add(number2);
                listDouble.add(number3);
            Bundle b = new Bundle();
            b.putStringArrayList("arrayList", listDouble);
                Intent goToSave = new Intent(CalcActivity.this, SavingsActivity.class);
                goToSave.putExtras(b);
                startActivity(goToSave);
            }
        });
    }

    private void showToast(String text){
        Toast.makeText(CalcActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}

