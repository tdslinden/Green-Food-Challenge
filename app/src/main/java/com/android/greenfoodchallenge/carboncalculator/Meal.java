package com.android.greenfoodchallenge.carboncalculator;

public class Meal {
    private String Location;
    private String Meal;
    private String Protein;
    private String Restaurant;

    public Meal() {
        Location = "Unknown";
        Meal = "Unknown";
        Protein = "Unknown";
        Restaurant = "Unknown";
    }

    public Meal(String location, String meal, String protein, String restaurant) {
        Location = location;
        Meal = meal;
        Protein = protein;
        Restaurant = restaurant;
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

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }

    public boolean isValidMeal(){
        if (Location.equals("Unknown") && Meal.equals("Unknown") && Protein.equals("Unknown") && Restaurant.equals("Unknown")){
            return false;
        }
        return true;
    }
}
