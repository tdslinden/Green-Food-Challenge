package com.android.greenfoodchallenge.carboncalculator;

import android.graphics.Color;

public class EquivalenceCalculator {
    //typical passenger car emits about 4.7 metric tons/year
    //typical person emits 1.5 metric tonnes of co2e from food
    private double typical_car_emission, typical_co2e_from_food, goal_co2e;

    //Vancouver population is 2.46 million
    private int num_people;

    EquivalenceCalculator(){
        typical_car_emission=4.7;
        typical_co2e_from_food=1.5;
        goal_co2e=1;
        num_people=2460000;
    }

    //number of cars taken off the road in thousands
    private int getCarEquivalence(double user_co2e) {
        return (int)Math.abs(((num_people*(user_co2e-typical_co2e_from_food))/(typical_car_emission*1000)));
    }
    //Color based if the user CO2e is greater/less than goal/average amounts
    public int getUserColor(double user_co2e){
        if(user_co2e>typical_co2e_from_food){
            return Color.parseColor("#4d0000");
        }
        else if(user_co2e<goal_co2e){
            return Color.parseColor("#99ff66");
        }
        else{
            return Color.parseColor("#ff9933");
        }
    }

    public String getResultString(double user_co2e){
        String result;
        if(user_co2e<typical_co2e_from_food){
            result="If everyone in Vancouver had the same diet as you, the amount of CO2e reduced would be equivalent to taking " + getCarEquivalence(user_co2e) +
                    " thousand cars off of the road";
        }
        else {
            result="If everyone in Vancouver had the same diet as you, the amount of extra CO2e produced would be equivalent to putting " + getCarEquivalence(user_co2e) +
                    " thousand cars on the road";
        }
        return result;
    }
}
