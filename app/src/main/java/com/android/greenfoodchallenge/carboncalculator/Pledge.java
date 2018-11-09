package com.android.greenfoodchallenge.carboncalculator;

//Plege class format used for the firebase database
public class Pledge {
    private String Name;
    private long Pledge;
    private String Region;
    private String Icon;

    public Pledge(String name, long pledge, String region, String icon) {
        this.Name = name;
        this.Pledge = pledge;
        this.Region = region;
        this.Icon = icon;
    }

    public Pledge() {
        this.Name = "Unknown";
        this.Pledge = 0;
        this.Region = "Unknown";
        this.Icon = "none";
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

}
