package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealCountTest {
    MealCount mealCount = new MealCount();
    MealCount specificMealCount = new MealCount(2);

    @Test
    public void testGetMealCount1() {
        assertEquals(mealCount.getMealCount(), 0, 0.0001);
    }

    @Test
    public void testSetMealCount() {
        mealCount.setMealCount(2);
        assertEquals(mealCount.getMealCount(), 2, 0.0001);
    }

    @Test
    public void testGetMealCount2(){
        assertEquals(specificMealCount.getMealCount(), 2, 0.0001);
    }
}