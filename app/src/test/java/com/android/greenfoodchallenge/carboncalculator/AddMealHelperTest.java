package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AddMealHelperTest {
    AddMealHelper storage = new AddMealHelper();
    @Test
    public void addToFirebase() {
        Map<String, Object> testContainer = new HashMap<>();

        String meal = "my meal";
        String tags = "my tags";
        String restaurant = "my restaurant";
        String location = "my location";
        String description = "my description";
        String photo = "my photo";

        testContainer.put("Meal", meal);
        testContainer.put("Tags", tags);
        testContainer.put("Restaurant", restaurant);
        testContainer.put("Location", location);
        testContainer.put("Description", description);
        testContainer.put("MealPhoto", photo);

        assertEquals(testContainer, storage.addToFirebase(meal, tags, restaurant, location, description, photo));
    }
}
