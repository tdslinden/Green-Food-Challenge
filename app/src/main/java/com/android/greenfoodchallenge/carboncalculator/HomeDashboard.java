package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeDashboard extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;
    private CardView calculatorCard;
    private CardView pledgeCard;
    private CardView viewPledgesCard;
    private CardView findRestaurantCard;
    private CardView tipsCard;
    private CardView shareCard;
    private Button aboutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        aboutButton = (Button) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAbout = new Intent(HomeDashboard.this, AboutSwipeActivity.class);
                goToAbout.addFlags(goToAbout.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToAbout);
                overridePendingTransition(0,0);
            }
        });

        calculatorCard = (CardView) findViewById(R.id.calculatorCard);
        calculatorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCalculator = new Intent(HomeDashboard.this, CalorieCalc.class);
                goToCalculator.addFlags(goToCalculator.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToCalculator);
                overridePendingTransition(0,0);
            }
        });

        pledgeCard = (CardView) findViewById(R.id.pledgeCard);
        pledgeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPledge = new Intent(HomeDashboard.this, pledgeActivity.class);
                goToPledge.addFlags(goToPledge.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToPledge);
                overridePendingTransition(0,0);
            }
        });

        viewPledgesCard = (CardView) findViewById(R.id.viewPledgeCard);
        viewPledgesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToViewPledges = new Intent(HomeDashboard.this, ViewPledgeActivity.class);
                goToViewPledges.addFlags(goToViewPledges.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToViewPledges);
                overridePendingTransition(0,0);
            }
        });

        findRestaurantCard = (CardView) findViewById(R.id.findRestaurantCard);
        findRestaurantCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFindRestaurant = new Intent(HomeDashboard.this, MapsActivity.class);
                goToFindRestaurant.addFlags(goToFindRestaurant.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToFindRestaurant);
                overridePendingTransition(0,0);
            }
        });

        shareCard = (CardView) findViewById(R.id.shareCard);
        shareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                // change the msg here
                intent.setType("text/plain");
                String shareBody = "Join the green food challenge and see how you can save the planet.";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);
        mBottomNavigation.getMenu().findItem(R.id.nav_home).setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        break;

                    case R.id.nav_viewmeal:
                        Intent goToViewMeal = new Intent(HomeDashboard.this, ViewMealActivity.class);
                        goToViewMeal.addFlags(goToViewMeal.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToViewMeal);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_addmeal:
                        Intent goToAddMeal = new Intent(HomeDashboard.this, AddMeal.class);
                        goToAddMeal.addFlags(goToAddMeal.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToAddMeal);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_profile:
                        Intent goToProfile = new Intent(HomeDashboard.this, ProfileActivity.class);
                        goToProfile.addFlags(goToProfile.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToProfile);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }
}
