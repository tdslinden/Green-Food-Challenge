package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddMeal extends AppCompatActivity {

    public static Intent makeIntent(Context context){
        Intent intent =new Intent(context, AddMeal.class);
        return intent;
    }

    private DatabaseReference mDatabase;
    private EditText mealField;
    private EditText proteinField;
    private EditText restaurantField;
    private EditText locationField;
    private Button submitMeal;
    private TextView addMeal;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setupViewPledgeButton();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getAuthExtras();

        mealField = findViewById(R.id.meal);
        proteinField = findViewById(R.id.protein);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        submitMeal = findViewById(R.id.submitPledgeButton);

        addMeal = findViewById(R.id.textbox1);
        addMeal.setText(getString(R.string.addMealActivity));

        submitMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the user somehow manages to get on to the pledge page without signing in, notify them and don't accept any inputs
                if(userId == null){
                    Toast.makeText(AddMeal.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                }
                else {
                    submitMealButton();
                }
            }
        });
    }

    private void submitMealButton(){
        final String meal = mealField.getText().toString();
        final String protein = proteinField.getText().toString();
        final String restaurant = restaurantField.getText().toString();
        final String location = locationField.getText().toString();

        Map<String, Object> storage = new HashMap<>();

        if (meal.equals("") || protein.equals("") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            MealMapForFirebase mealToFirebase = new MealMapForFirebase();

            storage = mealToFirebase.addToFirebase(meal, protein, restaurant, location);
            mDatabase.child("users").child(userId).setValue(storage);
            Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();
        }
    }

    //Gets user ID from authentication
    public void getAuthExtras(){
        Bundle authData = this.getIntent().getExtras();
        userId = authData.getString("userId");
    }

    private void setupViewPledgeButton(){
        Button button = findViewById(R.id.viewPledgeButton);
        button.setOnClickListener(v -> {
            Intent intent = ViewPledgeActivity.makeIntentWithUID(AddMeal.this, userId);
            startActivity(intent);
        });

    }
}
