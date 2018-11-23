package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void testRedMeatCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.redMeatCarbonFootprint(), 3.2575, 0.0001);
    }

    @Test
    public void testPoultryCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.poultryCarbonFootprint(), 1.0225, 0.0001);
    }

    @Test
    public void testSeafoodCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.seafoodCarbonFootprint(), 1.2875, 0.0001);
    }

    @Test
    public void testDairyCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.dairyCarbonFootprint(), 3.03, 0.0001);
    }

    @Test
    public void testGrainsCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.grainsCarbonFootprint(), 0.3575, 0.0001);
    }

    @Test
    public void testFruitsCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.fruitsCarbonFootprint(), 0.5225, 0.0001);
    }

    @Test
    public void testVegetablesCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.vegetablesCarbonFootprint(), 0.81, 0.0001);
    }

    @Test
    public void testTotalFootprint1(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("10", "10", "10", "40", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        double totalFootprint = (3.2575) +
                (1.0225) +
                (1.2875) +
                (3.03) +
                (0.3575) +
                (0.5225) +
                (0.81);
        totalFootprint *= 365;
        assertEquals(calculator.totalFootprint(), totalFootprint, 0.0001);
    }
}