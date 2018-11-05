package com.android.greenfoodchallenge.carboncalculator;

public class Pledge {
    private String Name;
    private long Pledge;
    private String Region;

    public Pledge(String name, long pledge, String region) {
        this.Name = name;
        this.Pledge = pledge;
        this.Region = region;
    }

    public Pledge() {
        this.Name = "none";
        this.Pledge = 0;
        this.Region = "none";
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setPledge(long pledge) {
        this.Pledge = pledge;
    }

    public String getName() {
        return Name;
    }

    public long getPledge() {
        return Pledge;
    }
}
