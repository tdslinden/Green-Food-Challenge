package com.android.greenfoodchallenge.carboncalculator;

public class Pledge {
    private String Name;
    private long Pledge;
    private String Region;
    private String Icon;
    private Meal meal;

    public Pledge(String name, long pledge, String region, String icon, Meal Meal) {
        this.Name = name;
        this.Pledge = pledge;
        this.Region = region;
        this.Icon = icon;
        this.meal = Meal;
    }

    public Pledge() {
        this.Name = "Unknown";
        this.Pledge = 0;
        this.Region = "Unknown";
        this.Icon = "none";
        this.meal = new Meal();
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

}
