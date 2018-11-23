package com.android.greenfoodchallenge.carboncalculator;

/*
* A class for calculating the car equivalence of your food intake
* Some activity specific helper functions for userUnderstanding
*/

//typical passenger car emits about 4.7 metric tons/year
//typical person emits 1.5 metric tonnes of co2e from food in Vancouver
//goal value is a 33% reduction in current levels

public class EquivalenceCalculator {
    private double typical_car_emission, goal_co2e, vancouver_co2e;

    //Vancouver population is 2.46 million
    private int num_people;

    EquivalenceCalculator(){
        typical_car_emission=4.7;
        vancouver_co2e=1.5;
        goal_co2e=1;
        num_people=2460000;
    }

    //number of cars taken off the road in thousands
    public int getCarEquivalence(double user_co2e) {
        return (int)Math.abs(((num_people*(user_co2e-vancouver_co2e))/(typical_car_emission*1000)));
    }

    public boolean isOverAverage(double user_co2e){
        if(user_co2e>vancouver_co2e){
            return true;
        }
        else {
            return false;
        }
    }

    //Color based if the user CO2e is greater/less than goal/average amounts
    public String getUserColor(double user_co2e){
        if(user_co2e>vancouver_co2e){
            return "#4d0000"; //Dark Red
        }
        else if(user_co2e<goal_co2e){
            return "#A0C25A"; //Light Green
        }
        else{
            return "#ff9933"; //Orange
        }
    }
}
