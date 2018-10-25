package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {
    /*
    private int pageNumbers;
    private int currentPageNumber = 0;
    private List<Integer> pageIDList = new ArrayList<Integer>();
    private List<Integer> navigationButtonIDList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        populateIDList();
        pageNumbers = pageIDList.size();
        setupQuitButton();
        for(int buttonID : navigationButtonIDList){
            setupNavigationButton(buttonID);
        }
    }

    private void updatePage(int pageNumber){
        setContentView(pageIDList.get(pageNumber));
        setupQuitButton();
        for(int buttonID : navigationButtonIDList){
            setupNavigationButton(buttonID);
        }
    }

    private void populateIDList(){
        navigationButtonIDList.add(R.id.btnNextPage);
        navigationButtonIDList.add(R.id.btnPreviousPage);
        pageIDList.add(R.layout.activity_about);
        pageIDList.add(R.layout.activity_about2);
        pageIDList.add(R.layout.activity_about3);
        pageIDList.add(R.layout.activity_about4);
        pageIDList.add(R.layout.activity_about5);
    }

    private void setupNavigationButton(int buttonID){
        Button button = (Button) findViewById(buttonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pressedButtonID = v.getId();
                int newPageNumber = currentPageNumber;
                if (pressedButtonID == R.id.btnNextPage){
                    if (currentPageNumber < pageNumbers-1){
                        newPageNumber += 1;
                    }
                }
                else if (pressedButtonID == R.id.btnPreviousPage){
                    if (currentPageNumber > 0){
                        newPageNumber -= 1;
                    }
                }

                Log.i("APPDEBUGLOG", Integer.toString(newPageNumber));
                if (currentPageNumber != newPageNumber){
                    currentPageNumber = newPageNumber;
                    updatePage(currentPageNumber);
                }
            }
        });
    }

    private void setupQuitButton(){
        Button button = (Button) findViewById(R.id.btnQuit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }
    */
}
