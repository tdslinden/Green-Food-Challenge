package com.android.greenfoodchallenge.carboncalculator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


// A class to create a map for adding the meal page
@IgnoreExtraProperties
public class MealMapForFirebase {


    public MealMapForFirebase() {
    }

    public Map<String, Object> addToFirebase(String meal, String protein, String restaurant, String location){
        Map<String, Object> storage = new HashMap<>();

        storage.put("Meal", meal);
        storage.put("Protein", protein);
        storage.put("Restaurant", restaurant);
        storage.put("Location", location);

        return storage;
    }

}

