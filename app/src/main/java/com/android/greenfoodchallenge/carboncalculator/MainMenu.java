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
                            R.drawable.testpledge,
                            "Pledge Now",
                            "Make your pledge now.",
                            "Pledge Now"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.testshare,
                            "Share",
                            "",
                            "Send Invites"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.testshare,
                            "Share",
                            "Make your pledge now.",
                            "Pledge Now"));

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
                            Intent goToCalculator = CalorieCalc.makeIntent(MainMenu.this);
                            startActivity(goToCalculator);
                            break;

                        case R.id.nav_pledges:
                            Intent goToPledges = ViewPledgeActivity.makeIntent(MainMenu.this);
                            startActivity(goToPledges);
                            break;
                    }
                    return false;
                }
            });




        }

}
