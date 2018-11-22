package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AddMeal extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText mealField;
    private EditText proteinField;
    private EditText restaurantField;
    private EditText locationField;
    private Button submitMeal, backButton;
    private TextView addMeal;
    private String userId;
    private EditText description;
    private ConstraintLayout clickableLayout;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = mFirebaseUser.getUid();
        mealField = findViewById(R.id.meal);
        proteinField = findViewById(R.id.protein);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        description = findViewById(R.id.description);
        submitMeal = findViewById(R.id.submitPledgeButton);

        addMeal = findViewById(R.id.addMealTitle);
        addMeal.setText(getString(R.string.addMealActivity));

        submitMeal.setOnClickListener(v -> {
            // If the user somehow manages to get past authentication, notify them and don't accept any inputs
            if(userId == null){
                Toast.makeText(AddMeal.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
            }
            else {
                submitMealButton();
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clickableLayout = (ConstraintLayout) findViewById(R.id.addMealClickable);
        clickableLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


    }

    private void submitMealButton(){
        final String meal = mealField.getText().toString();
        final String protein = proteinField.getText().toString();
        final String restaurant = restaurantField.getText().toString();
        final String location = locationField.getText().toString();
        final String details = description.getText().toString();

        Map<String, Object> storage;

        if (meal.equals("") || protein.equals("") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            AddMealHelper mealToFirebase = new AddMealHelper();

            storage = mealToFirebase.addToFirebase(meal, protein, restaurant, location, details);
            mDatabase.child("users").child(userId).child("meal").setValue(storage);
            Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
