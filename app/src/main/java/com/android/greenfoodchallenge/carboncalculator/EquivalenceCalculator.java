package com.android.greenfoodchallenge.carboncalculator;

public class EquivalenceCalculator {
    //typical passenger car emits about 4.7 metric tons/year
    //typical person emits 1.5 metric tonnes of co2e from food
    private double typical_car_emission, typical_co2e_from_food;

    //Vancouver population is 2.46 million
    private int num_people;

    //on instantiation add a parameter for the user_co2e
    EquivalenceCalculator(){
        typical_car_emission=4.7;
        typical_co2e_from_food=1.5;
        num_people=2460000;
    }

    //number of cars taken off the road in thousands
    private int getCarEquivalence(double user_co2e) {
        return (int)Math.abs(((num_people*(user_co2e-typical_co2e_from_food))/(typical_car_emission*1000)));
    }

    //
    public String getResultString(double user_co2e){
        String result;
        if(user_co2e<typical_co2e_from_food){
            result="If everyone had the same diet as you in Vancouver, the amount of CO2e reduced would be equivalent to taking " + getCarEquivalence(user_co2e) +
                    " thousand cars off the road";
        }
        else {
            result="If everyone had the same diet as you in Vancouver, the amount of extra CO2e produced would be equivalent to putting " + getCarEquivalence(user_co2e) +
                    " thousand cars on the road";
        }
        return result;
    }
}
