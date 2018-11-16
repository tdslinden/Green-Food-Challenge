package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AddMeal extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText mealField;
    private EditText tagsField;
    private EditText restaurantField;
    private EditText locationField;
    private Button submitMeal;
    private TextView addMeal;
    private String userId;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setupViewPledgeButton();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getUserId();

        mealField = findViewById(R.id.meal);
        tagsField = findViewById(R.id.tags);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        description = findViewById(R.id.description);
        submitMeal = findViewById(R.id.submitPledgeButton);

        addMeal = findViewById(R.id.textbox1);
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
    }

    private void submitMealButton(){
        final String meal = mealField.getText().toString();
        final String tags = tagsField.getText().toString();
        final String restaurant = restaurantField.getText().toString();
        final String location = locationField.getText().toString();
        final String details = description.getText().toString();

        Map<String, Object> storage;

        if (meal.equals("") || tags.equals("") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            AddMealHelper mealToFirebase = new AddMealHelper();

            storage = mealToFirebase.addToFirebase(meal, tags, restaurant, location, details);
            mDatabase.child("users").child(userId).child("meal").setValue(storage);
            Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();
        }
    }

    //Gets user ID from authentication
    public void getUserId(){
        Bundle pledgeUserId = this.getIntent().getExtras();
        userId = pledgeUserId.getString("userId");
    }

    private void setupViewPledgeButton(){
        Button button = findViewById(R.id.viewPledgeButton);
        button.setOnClickListener(v -> {
            Intent intent = ViewPledgeActivity.makeIntentWithUID(AddMeal.this, userId);
            startActivity(intent);
        });
    }
}
