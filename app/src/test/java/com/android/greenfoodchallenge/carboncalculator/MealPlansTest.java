package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealPlansTest {
    double scale = 100;
    double carbonFootprint = 10;
    MealPlans mealPlan = new MealPlans(carbonFootprint);

    @Test
    public void testGetCarbonFootprint(){
        assertEquals(mealPlan.getCarbonFootprint(), 10, 0.0000001);
    }

    @Test
    public void testSetCarbonFootprint(){
        mealPlan.setCarbonFootprint(1);
        assertEquals(mealPlan.getCarbonFootprint(), 1, 0.0000001);
    }

    @Test
    public void testGetMealPlan(){
        assertEquals(mealPlan.getMealPlan(), 0);
    }

    @Test
    public void testSetMealPlan(){
        mealPlan.setMealPlan(1);
        assertEquals(mealPlan.getMealPlan(), 1);
    }

    @Test
    public void testCalculateSavedCarbonFootprint1(){
        mealPlan.calculateSavedCarbonFootprint();
        assertEquals(mealPlan.doubleToString(), "0.91");
    }

    @Test
    public void testCalculateSavedCarbonFootprint2(){
        mealPlan.calculateSavedCarbonFootprint();
        double calculation = carbonFootprint - (carbonFootprint/1.1);
        calculation = Math.round(calculation * scale) / scale;
        String result = Double.toString(calculation);
        assertEquals(mealPlan.doubleToString(), result);
    }

    @Test
    public void testCalculateSavedCarbonFootprint3(){
        mealPlan.setMealPlan(1);
        mealPlan.calculateSavedCarbonFootprint();
        assertEquals(mealPlan.doubleToString(), "2.0");
    }

    @Test
    public void testCalculateSavedCarbonFootprint4(){
        mealPlan.setMealPlan(1);
        mealPlan.calculateSavedCarbonFootprint();
        double calculation = carbonFootprint - (carbonFootprint/1.25);
        calculation = Math.round(calculation * scale) / scale;
        String result = Double.toString(calculation);
        assertEquals(mealPlan.doubleToString(), result);
    }

    @Test
    public void testCalculateSavedCarbonFootprint5(){
        mealPlan.setMealPlan(2);
        mealPlan.calculateSavedCarbonFootprint();
        assertEquals(mealPlan.doubleToString(), "3.33");
    }

    @Test
    public void testCalculateSavedCarbonFootprint6(){
        mealPlan.setMealPlan(2);
        mealPlan.calculateSavedCarbonFootprint();
        double calculation = carbonFootprint - (carbonFootprint/1.50);
        calculation = Math.round(calculation * scale) / scale;
        String result = Double.toString(calculation);
        assertEquals(mealPlan.doubleToString(), result);
    }

    @Test
    public void testCalculateSavedCarbonFootprint7(){
        mealPlan.setMealPlan(3);
        mealPlan.calculateSavedCarbonFootprint();
        assertEquals(mealPlan.doubleToString(), "4.59");
    }

    @Test
    public void testCalculateSavedCarbonFootprint8(){
        mealPlan.setMealPlan(3);
        mealPlan.calculateSavedCarbonFootprint();
        double calculation = carbonFootprint - (carbonFootprint/1.85);
        calculation = Math.round(calculation * scale) / scale;
        String result = Double.toString(calculation);
        assertEquals(mealPlan.doubleToString(), result);
    }

}