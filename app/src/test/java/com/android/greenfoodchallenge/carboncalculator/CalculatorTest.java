package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CalculatorTest {
/*
    @Test
    public void testBeefCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.beefCarbonFootprint(), 0.013 * calories * 13.78 / 1000, 0.0001);
    }

    @Test
    public void testPorkCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.porkCarbonFootprint(), 0.102 * calories * 4.45 / 1000, 0.0001);
    }

    @Test
    public void testChickenCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.chickenCarbonFootprint(), 0.1002 * calories * 3.37 / 1000, 0.0001);
    }

    @Test
    public void testFishCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.fishCarbonFootprint(), 0.0505 * calories * 5.21 / 1000, 0.0001);
    }

    @Test
    public void testEggsCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.eggsCarbonFootprint(), 0.10 * calories * 3.06 / 1000, 0.0001);
    }

    @Test
    public void testBeansCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.beansCarbonFootprint(), 0.10 * calories * 1.09/ 1000, 0.0001);
    }

    @Test
    public void testVegetablesCarbonFootprint(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        assertEquals(calculator.vegetablesCarbonFootprint(), 0.10 * calories * 3.24/ 1000, 0.0001);
    }

    @Test
    public void testTotalFootprint1(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        double totalFootprint = (0.013 * calories * 13.78 / 1000) +
                                (0.102 * calories * 4.45 / 1000) +
                                (0.1002 * calories * 3.37 / 1000) +
                                (0.0505 * calories * 5.21 / 1000) +
                                (0.10 * calories * 3.06 / 1000) +
                                (0.10 * calories * 1.09/ 1000) +
                                (0.10 * calories * 3.24/ 1000);
        totalFootprint *= 365;
        assertEquals(calculator.totalFootprint(), totalFootprint, 0.0001);
    }

    @Test
    public void testTotalFootprint2(){
        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.3", "10.2", "10.02", "5.05", "10.0", "10.0", "10.0"));
        double calories = 2500;
        Calculator calculator = new Calculator(data, calories);
        double totalFootprint = calculator.beefCarbonFootprint() +
                                calculator.porkCarbonFootprint() +
                                calculator.chickenCarbonFootprint() +
                                calculator.fishCarbonFootprint() +
                                calculator.eggsCarbonFootprint() +
                                calculator.beansCarbonFootprint() +
                                calculator.vegetablesCarbonFootprint();
        totalFootprint *= 365;
        assertEquals(calculator.totalFootprint(), totalFootprint, 0.0001);
    }

*/
}