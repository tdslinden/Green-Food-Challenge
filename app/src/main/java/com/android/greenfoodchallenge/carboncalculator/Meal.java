package com.android.greenfoodchallenge.carboncalculator;

public class Meal {
    private String Meal;
    private String myTags;
    private String Restaurant;
    private String Location;
    private String mealDescription;
    private String mealPhotoPath;

    public Meal() {
        Meal = "Unknown";
        myTags = "Unknown";
        Restaurant = "Unknown";
        Location = "Unknown";
        mealDescription = "Unknown";
        mealPhotoPath = "Unknown";
    }

    public Meal(String location, String meal, String tags, String restaurant, String description, String path) {
        Meal = meal;
        myTags = tags;
        Restaurant = restaurant;
        Location = location;
        mealDescription = description;
        mealPhotoPath = path;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMeal() {
        return Meal;
    }

    public void setMeal(String meal) {
        Meal = meal;
    }

    public String getMyTags() {
        return myTags;
    }

    public void setMyTags(String tags) {
        myTags = tags;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String description) {
        mealDescription = description;
    }

    public String getMealPhotoPath() {
        return mealPhotoPath;
    }

    public void setMealPhotoPath(String path) {
        mealPhotoPath = path;
    }

    public boolean isValidMeal(){
        if (Location.equals("Unknown") && Meal.equals("Unknown") && myTags.equals("Unknown") && Restaurant.equals("Unknown")){
            return false;
        }

        return true;
    }
}
