package com.android.greenfoodchallenge.carboncalculator;

public class RetrieveData {
    private String userMeal;
    private String userTags;
    private String userRestaurant;
    private String userLocation;
    private String userDescription;
    private String userMealPhoto;
    private Integer userMealCount;

    public RetrieveData(String meal, String tags, String restaurant, String location, String description, String mealPhoto, Integer mealCount) {
        userMeal = meal;
        userTags = tags;
        userRestaurant = restaurant;
        userLocation = location;
        userDescription = description;
        userMealPhoto = mealPhoto;
        userMealCount = mealCount;
    }

    public Integer getUserMealCount() {
        return userMealCount;
    }
}
