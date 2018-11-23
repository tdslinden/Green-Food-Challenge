package com.android.greenfoodchallenge.carboncalculator;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


// A class to create a map for adding the meal page
@IgnoreExtraProperties
public class AddMealHelper {

    public AddMealHelper() {
    }

    public Map<String, Object> addToFirebase(String meal, String tags, String restaurant, String location, String description, String mealPhoto){
        Map<String, Object> storage = new HashMap<>();

        storage.put("Meal", meal);
        storage.put("Tags", tags);
        storage.put("Restaurant", restaurant);
        storage.put("Location", location);
        storage.put("Description", description);
        storage.put("MealPhoto", mealPhoto);

        return storage;
    }
}

