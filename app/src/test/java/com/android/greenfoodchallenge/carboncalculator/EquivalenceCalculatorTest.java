package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class EquivalenceCalculatorTest {
    EquivalenceCalculator equivalenceCalcultor = new EquivalenceCalculator();

    //User CO2e > typical CO2e
    @Test
    public void testGetCarEquivalence1(){
        int realCarEquiv = 366;
        double user_co2e=2.2;
        equivalenceCalcultor.getCarEquivalence(user_co2e);
        assertEquals(realCarEquiv, equivalenceCalcultor.getCarEquivalence(user_co2e));
    }

    //User CO2e < typical CO2e
    @Test
    public void testGetCarEquivalence2(){
        int realCarEquiv = 523;
        double user_co2e=.5;
        equivalenceCalcultor.getCarEquivalence(user_co2e);
        assertEquals(realCarEquiv, equivalenceCalcultor.getCarEquivalence(user_co2e));
    }

    //User CO2e > typical CO2e
    @Test
    public void testisOverAverage1(){
        boolean isOver=true;
        double user_co2e=1.6;
        equivalenceCalcultor.isOverAverage(user_co2e);
        assertEquals(isOver, equivalenceCalcultor.isOverAverage(user_co2e));
    }

    //User CO2e < typical CO2e
    @Test
    public void testisOverAverage2(){
        boolean isOver=false;
        double user_co2e=1.4;
        equivalenceCalcultor.isOverAverage(user_co2e);
        assertEquals(isOver, equivalenceCalcultor.isOverAverage(user_co2e));
    }

    //User CO2e > typical CO2e
    @Test
    public void testgetUserColor1(){
        String colorString="#4d0000";
        double user_co2e=1.6;
        equivalenceCalcultor.getUserColor(user_co2e);
        assertEquals(colorString, equivalenceCalcultor.getUserColor(user_co2e));
    }
    //User CO2e < GOAL CO2e
    @Test
    public void testgetUserColor2(){
        String colorString="#A0C25A";
        double user_co2e=.9;
        equivalenceCalcultor.getUserColor(user_co2e);
        assertEquals(colorString, equivalenceCalcultor.getUserColor(user_co2e));
    }
    //typical CO2e < User CO2e < GOAL CO2e
    @Test
    public void testgetUserColor3(){
        String colorString="#ff9933";
        double user_co2e=1.4;
        equivalenceCalcultor.getUserColor(user_co2e);
        assertEquals(colorString, equivalenceCalcultor.getUserColor(user_co2e));
    }
}