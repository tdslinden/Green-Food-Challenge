package com.android.greenfoodchallenge.carboncalculator;

public class MealCount {
    long mealCount;

    public long getMealCount() {
        return mealCount;
    }

    public void setMealCount(long mealCount) {
        this.mealCount = mealCount;
    }

    public MealCount(long mealCount) {
        this.mealCount = mealCount;
    }

    public MealCount(){
        this.mealCount = 0;
    }
}
