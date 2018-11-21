package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealTest {
    Meal meal = new Meal();
    Meal specificMeal = new Meal("Vancouver", "Poutine", "Meat", "Costco", "It'sGood", "fasd");
    @Test
    public void getLocation1() {
        assertEquals(meal.getLocation(), "Unknown");
    }

    public void getLocation2() {
        assertEquals(specificMeal.getLocation(), "Vancouver");
    }

    @Test
    public void setLocation() {
        meal.setLocation("Vancouver");
        assertEquals(meal.getLocation(), "Vancouver");
    }

    @Test
    public void getMeal1() {
        assertEquals(meal.getMeal(), "Unknown");
    }

    @Test
    public void getMeal2() {
        assertEquals(specificMeal.getMeal(), "Poutine");
    }

    @Test
    public void setMeal() {
        meal.setMeal("Poutine");
        assertEquals(meal.getMeal(), "Poutine");
    }

    @Test
    public void getProtein1() {
        assertEquals(meal.getTags(), "Unknown");
    }

    @Test
    public void getProtein2() {
        assertEquals(specificMeal.getTags(), "Meat");
    }

    @Test
    public void setProtein() {
        meal.setTags("Meat");
        assertEquals(meal.getTags(), "Meat");
    }

    @Test
    public void getRestaurant1() {
        assertEquals(meal.getRestaurant(), "Unknown");
    }

    @Test
    public void getRestaurant2() {
        assertEquals(specificMeal.getRestaurant(), "Costco");
    }

    @Test
    public void setRestaurant() {
        meal.setRestaurant("Costco");
        assertEquals(meal.getRestaurant(), "Costco");
    }

    @Test
    public void isValidMeal1() {
        assertFalse(meal.isValidMeal());
    }

    @Test
    public void isValidMeal2() {
        assertTrue(specificMeal.isValidMeal());
    }

    @Test
    public void getDescription1(){
        assertEquals(specificMeal.getDescription(),"It'sGood");
    }

    @Test
    public void getDescription2(){
        assertEquals(meal.getDescription(),"");
    }

    @Test
    public void setDescription1(){
        meal.setDescription("It'sGood");
        assertEquals(meal.getDescription(),"It'sGood");
    }

    @Test
    public void getMealPhoto1(){
        assertEquals(meal.getMealPhoto(), "");
    }

    @Test
    public void getMealPhoto2(){
        assertEquals(specificMeal.getMealPhoto(), "fasd");
    }

    @Test
    public void setMealPhoto1(){
        meal.setMealPhoto("fasd");
        assertEquals(meal.getMealPhoto(), "fasd");
    }

}