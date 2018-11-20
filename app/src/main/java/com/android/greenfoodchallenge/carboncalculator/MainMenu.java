package com.android.greenfoodchallenge.carboncalculator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenu extends AppCompatActivity{

    private BottomNavigationView mBottomNavigation;
    private Button mAboutButton;
    private String userId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_menu);

            mFirebaseAuth = FirebaseAuth.getInstance();
            if(mFirebaseAuth.getCurrentUser() == null){
                finish();
                Intent goToHome = new Intent(MainMenu.this, Registration.class);
                goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToHome);
                overridePendingTransition(0,0);
            }

            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            List<CardItem> mCardItems = new ArrayList<>();

            mCardItems.add(
                    new CardItem(
                            R.drawable.testpledge,
                            "Calculator",
                            "",
                            "Calculate"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.testpledge,
                            "Pledge Now",
                            "Make your pledge now.",
                            "Pledge Now"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.fruitsonside,
                            "View Pledges",
                            "See all pledges.",
                            "View Pledges"));
            mCardItems.add(
                    new CardItem(
                            R.drawable.testlogin,
                            "Share",
                            "",
                            "Send Invites"));

            CardItemAdapter mCardItemAdapter = new CardItemAdapter(this, mCardItems);
            recyclerView.setAdapter(mCardItemAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            mAboutButton = (Button) findViewById(R.id.aboutButton);
            mAboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToHome = new Intent(MainMenu.this, AboutSwipeActivity.class);
                    startActivity(goToHome, ActivityOptions.makeSceneTransitionAnimation(MainMenu.this).toBundle());
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

                        case R.id.nav_addmeal:
                            Intent goToAddMeal = new Intent(MainMenu.this, Registration.class);
                            goToAddMeal.addFlags(goToAddMeal.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(goToAddMeal);
                            overridePendingTransition(0,0);

//                            Bundle storage = new Bundle();
//                            storage.putString("userId", userId);
//                            Intent goToAddMeal = new Intent(MainMenu.this, AddMeal.class);
//                            goToAddMeal.addFlags(goToAddMeal.FLAG_ACTIVITY_NO_ANIMATION);
//                            goToAddMeal.putExtras(storage);
//                            startActivity(goToAddMeal);
//                            overridePendingTransition(0,0);

                        case R.id.nav_profile:
                            Intent goToProfile = new Intent(MainMenu.this, HomeDashboard.class);
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


