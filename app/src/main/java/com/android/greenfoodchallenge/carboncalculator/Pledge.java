package com.android.greenfoodchallenge.carboncalculator;

public class Pledge {
    private String Name;
    private long Pledge;

    public Pledge(String name, long pledge) {
        this.Name = name;
        this.Pledge = pledge;
    }

    public Pledge() {
        this.Name = "none";
        this.Pledge = 0;
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
