package com.android.greenfoodchallenge.carboncalculator;

public class Pledge {
    private String Name;
    private long Pledge;
    private long MealCount;
    private String Region;
    private String Icon;
    private Meal meal;
    private String Description;
    private String Tags;
    private String MealPhoto;

    public Pledge(String name, long pledge, long mealCount, String region, String icon, Meal meal, String description, String tags, String mealPhoto) {
        Name = name;
        Pledge = pledge;
        MealCount = mealCount;
        Region = region;
        Icon = icon;
        this.meal = meal;
        Description = description;
        Tags = tags;
        MealPhoto = mealPhoto;
    }

    public Pledge() {
        this.Name = "Unknown";
        this.Pledge = 0;
        this.MealCount = 0;
        this.Region = "Unknown";
        this.Icon = "none";
        this.meal = new Meal();
        this.Description = "";
        this.Tags = "";
        this.MealPhoto = "";
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public long getPledge() {
        return Pledge;
    }

    public void setPledge(long pledge) {
        this.Pledge = pledge;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public Meal getMeal() {
        return meal;
    }

    public long getMealCount(){
        return MealCount;
    }

}
