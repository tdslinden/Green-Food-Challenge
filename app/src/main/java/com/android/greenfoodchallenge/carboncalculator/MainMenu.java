package com.android.greenfoodchallenge.carboncalculator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenu extends AppCompatActivity{

    private BottomNavigationView mBottomNavigation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_menu);

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            List<CardItem> mCardItems = new ArrayList<>();
            mCardItems.add(
                    new CardItem(
                            R.drawable.healthy1,
                            "Check Your Carbon Footprint",
                            "Calculator"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.pig,
                            "Check Your Carbon Footprint",
                            "Calculator"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.pig,
                            "Check Your Carbon Footprint",
                            "Calculator"));

            CardItemAdapter mCardItemAdapter = new CardItemAdapter(this, mCardItems);
            recyclerView.setAdapter(mCardItemAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);

            mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.nav_home:
                            break;

                        case R.id.nav_calculator:
                            Intent goToCalculator = new Intent(MainMenu.this, CalcActivity.class);
                            startActivity(goToCalculator, ActivityOptions.makeSceneTransitionAnimation(MainMenu.this).toBundle());
                            break;

                        case R.id.nav_profile:
                            break;

                    }
                    return false;
                }
            });




        }

}
