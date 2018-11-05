package com.android.greenfoodchallenge.carboncalculator;

import java.util.ArrayList;

public class Calculator {
    private double userCalories;
    private ArrayList<Double> userInput;

    Calculator(ArrayList<String> data, double calories) {
        stringToDouble(data);

        userCalories = calories;
    }

    private int toDecimal = 100;
    private int daysInAYear = 365;

    // Average kg CO2 emissions per 1000 emissions
    private double redMeatEmissions = 13.03;
    private double poultryEmissions = 4.09;
    private double seafoodEmissions = 5.15;
    private double dairyEmissions = 3.03;
    private double grainsEmissions = 1.43;
    private double fruitsEmissions = 2.09;
    private double vegetableEmissions = 3.24;
    private double perCalorie = 1000;

    // copies string arraylist into a double arraylist
    private void stringToDouble(ArrayList<String> data) {
        userInput = new ArrayList<>();
        double val;

        for(int i = 0; i < data.size(); i++) {
            val = Double.parseDouble(data.get(i));
            userInput.add(val);
        }
    }

    // the next 7 functions calculate their food specific co2e
    public double redMeatCarbonFootprint() {
        double beefProportion = userInput.get(0) / toDecimal;
        double beefCO2e = beefProportion * userCalories * redMeatEmissions;
        beefCO2e /= perCalorie;

        return beefCO2e;
    }

    public double poultryCarbonFootprint() {
        double porkProportion = userInput.get(1) / toDecimal;
        double porkCO2e = porkProportion * userCalories * poultryEmissions;
        porkCO2e /= perCalorie;

        return porkCO2e;
    }

    public double seafoodCarbonFootprint() {
        double chickenProportion = userInput.get(2) / toDecimal;
        double chickenCO2e = chickenProportion * userCalories * seafoodEmissions;
        chickenCO2e /= perCalorie;

        return chickenCO2e;
    }

    public double dairyCarbonFootprint() {
        double fishProportion = userInput.get(3) / toDecimal;
        double fishCO2e = fishProportion * userCalories * dairyEmissions;
        fishCO2e /= perCalorie;

        return fishCO2e;
    }

    public double grainsCarbonFootprint() {
        double eggsProportion = userInput.get(4) / toDecimal;
        double eggsCO2e = eggsProportion * userCalories * grainsEmissions;
        eggsCO2e /= perCalorie;

        return eggsCO2e;
    }

    public double fruitsCarbonFootprint() {
        double beansProportion = userInput.get(5) / toDecimal;
        double beansCO2e = beansProportion * userCalories * fruitsEmissions;
        beansCO2e /= perCalorie;

        return beansCO2e;
    }

    public double vegetablesCarbonFootprint() {
        double vegetablesProportion = userInput.get(6) / toDecimal;
        double vegetablesCO2e = vegetablesProportion * userCalories * vegetableEmissions;
        vegetablesCO2e /= perCalorie;

        return vegetablesCO2e;
    }

    // finds the total co2e per year
    public double totalFootprint() {
        double footprint = 0;
        footprint += redMeatCarbonFootprint();
        footprint += poultryCarbonFootprint();
        footprint += seafoodCarbonFootprint();
        footprint += dairyCarbonFootprint();
        footprint += grainsCarbonFootprint();
        footprint += fruitsCarbonFootprint();
        footprint += vegetablesCarbonFootprint();
        footprint *= daysInAYear;

        return footprint;
    }
}

