package com.android.greenfoodchallenge.carboncalculator;

public class Meal {
    private String Meal;
    private String Tags;
    private String Restaurant;
    private String Location;
    private String Description;
    private String MealPhoto;

    public Meal() {
        Meal = "Unknown";
        Tags = "Unknown";
        Restaurant = "Unknown";
        Location = "Unknown";
        Description = "";
        MealPhoto = "";
    }

    public Meal(String location, String meal, String tags, String restaurant, String description, String path) {
        Meal = meal;
        Tags = tags;
        Restaurant = restaurant;
        Location = location;
        Description = description;
        MealPhoto = path;
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

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMealPhoto() {
        return MealPhoto;
    }

    public void setMealPhoto(String path) {
        MealPhoto = path;
    }

    public boolean isValidMeal(){
        if (Location.equals("Unknown") && Meal.equals("Unknown") && Tags.equals("Unknown") && Restaurant.equals("Unknown")){
            return false;
        }

        return true;
    }
}
