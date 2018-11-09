package com.android.greenfoodchallenge.carboncalculator;

public class MealPlans {
    private static final double MEAT_EATER = 1.10;
    private static final double LOW_MEAT = 1.25;
    private static final double VEGETARIAN = 1.50;
    private static final double VEGAN = 1.85;
    private static final double SCALE = Math.pow(10, 2);
    private double mCarbonFootprint;
    private double mPotentialCarbonFootprint;
    private int mMealPlan;
    private String mPotentialCarbonFootprintString;

    MealPlans (double carbonFootprint){
        mMealPlan = 0;
        mCarbonFootprint = carbonFootprint;
    }

    public void calculateSavedCarbonFootprint(){

        if(mMealPlan == 0){
            mPotentialCarbonFootprint = mCarbonFootprint/MEAT_EATER;
            mPotentialCarbonFootprint =  mCarbonFootprint - mPotentialCarbonFootprint;
        }
        else if(mMealPlan == 1){
            mPotentialCarbonFootprint = mCarbonFootprint/LOW_MEAT;
            mPotentialCarbonFootprint = mCarbonFootprint - mPotentialCarbonFootprint;
        }
        else if(mMealPlan == 2){
            mPotentialCarbonFootprint = mCarbonFootprint/VEGETARIAN;
            mPotentialCarbonFootprint = mCarbonFootprint - mPotentialCarbonFootprint;
        }
        else if(mMealPlan == 3){
            mPotentialCarbonFootprint = mCarbonFootprint/VEGAN;
            mPotentialCarbonFootprint = mCarbonFootprint - mPotentialCarbonFootprint;
        }
    }

    public String doubleToString(){
        mPotentialCarbonFootprint = Math.round(mPotentialCarbonFootprint  * SCALE) / SCALE;
        mPotentialCarbonFootprintString = Double.toString(mPotentialCarbonFootprint);
        return mPotentialCarbonFootprintString;
    }

    public double getCarbonFootprint() {
        return mCarbonFootprint;
    }

    public void setCarbonFootprint(double carbonFootprint) {
        mCarbonFootprint = carbonFootprint;
    }

    public int getMealPlan() {
        return mMealPlan;
    }

    public void setMealPlan(int mealPlan) {
        mMealPlan = mealPlan;
    }
}
