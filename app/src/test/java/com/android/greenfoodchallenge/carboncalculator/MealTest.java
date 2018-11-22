package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealTest {
    Meal meal = new Meal();
    Meal specificMeal = new Meal("Vancouver", "Poutine", "Meat", "Costco");
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
        assertEquals(meal.getProtein(), "Unknown");
    }

    @Test
    public void getProtein2() {
        assertEquals(specificMeal.getProtein(), "Meat");
    }

    @Test
    public void setProtein() {
        meal.setProtein("Meat");
        assertEquals(meal.getProtein(), "Meat");
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
}