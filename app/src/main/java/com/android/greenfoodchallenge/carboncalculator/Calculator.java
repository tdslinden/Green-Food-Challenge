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

    // Kg CO2 emissions per 1000 emissions
    private double beefEmissions = 13.78;
    private double porkEmissions = 4.45;
    private double chickenEmissions = 3.37;
    private double fishEmissions = 5.21;                    // average
    private double eggsEmissions = 3.06;
    private double beansEmissions = 1.09;                   // average
    private double vegetableEmissions = 3.24;               // average
    private double perCalorie = 1000;

    private void stringToDouble(ArrayList<String> data) {
        userInput = new ArrayList<>();
        double val;

        for(int i = 0; i < data.size(); i++) {
            val = Double.parseDouble(data.get(i));
            userInput.add(val);
        }
    }

    public double beefCarbonFootprint() {
        double beefProportion = userInput.get(0) / toDecimal;
        double beefCO2e = beefProportion * userCalories * beefEmissions;
        beefCO2e /= perCalorie;

        return beefCO2e;
    }

    private double porkCarbonFootprint() {
        double porkProportion = userInput.get(1) / toDecimal;
        double porkCO2e = porkProportion * userCalories * porkEmissions;
        porkCO2e /= perCalorie;

        return porkCO2e;
    }

    private double chickenCarbonFootprint() {
        double chickenProportion = userInput.get(2) / toDecimal;
        double chickenCO2e = chickenProportion * userCalories * chickenEmissions;
        chickenCO2e /= perCalorie;

        return chickenCO2e;
    }

    private double fishCarbonFootprint() {
        double fishProportion = userInput.get(3) / toDecimal;
        double fishCO2e = fishProportion * userCalories * fishEmissions;
        fishCO2e /= perCalorie;

        return fishCO2e;
    }

    private double eggsCarbonFootprint() {
        double eggsProportion = userInput.get(4) / toDecimal;
        double eggsCO2e = eggsProportion * userCalories * eggsEmissions;
        eggsCO2e /= perCalorie;

        return eggsCO2e;
    }

    private double beansCarbonFootprint() {
        double beansProportion = userInput.get(5) / toDecimal;
        double beansCO2e = beansProportion * userCalories * beansEmissions;
        beansCO2e /= perCalorie;

        return beansCO2e;
    }

    private double vegetablesCarbonFootprint() {
        double vegetablesProportion = userInput.get(6) / toDecimal;
        double vegetablesCO2e = vegetablesProportion * userCalories * vegetableEmissions;
        vegetablesCO2e /= perCalorie;

        return vegetablesCO2e;
    }

    public double totalFootprint() {
        double footprint = 0;
        footprint += beefCarbonFootprint();
        footprint += porkCarbonFootprint();
        footprint += chickenCarbonFootprint();
        footprint += fishCarbonFootprint();
        footprint += eggsCarbonFootprint();
        footprint += beansCarbonFootprint();
        footprint += vegetablesCarbonFootprint();
        footprint *= daysInAYear;

        return footprint;
    }
}

