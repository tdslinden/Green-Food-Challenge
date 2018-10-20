package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SavingsActivity extends AppCompatActivity {

    private Button mButtonMenu;
//    String num1, num2, num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
//        Bundle bundle = getIntent().getExtras();
//        ArrayList<String> listDouble = (ArrayList<String>) bundle.getStringArrayList("arraylist");
//        num1 = listDouble.get(1);
//        num2 = listDouble.get(2);
//        num3 = listDouble.get(3);
//          showToast(String.valueOf(listDouble.get(1)));
//        showToast(String.valueOf(num2));
//        showToast(String.valueOf(num3));

        mButtonMenu = (Button)findViewById(R.id.button_menu);
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(SavingsActivity.this, MenuActivity.class);
                startActivity(goToMenu);
            }
        });

    }
    private void showToast(String text){
        Toast.makeText(SavingsActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}