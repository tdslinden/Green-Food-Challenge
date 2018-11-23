package com.android.greenfoodchallenge.carboncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME = 2500;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
              loggedInChecker();
            }
        }, SPLASH_TIME);
    }

    public void loggedInChecker(){
        if(mFirebaseAuth.getCurrentUser() != null){
            finish();
            Intent goToHome = new Intent(Splash.this, HomeDashboard.class);
            goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(goToHome);
            overridePendingTransition(0,0);
        }else{
            finish();
            Intent goToHome = new Intent(Splash.this, authenticationActivity.class);
            goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(goToHome);
            overridePendingTransition(0,0);
        }
    }
}
