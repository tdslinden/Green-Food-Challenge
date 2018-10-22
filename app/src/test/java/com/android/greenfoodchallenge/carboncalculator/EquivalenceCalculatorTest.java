package com.android.greenfoodchallenge.carboncalculator;

import android.graphics.Color;

import org.junit.Test;

import static org.junit.Assert.*;

public class EquivalenceCalculatorTest {
    EquivalenceCalculator equivalenceCalcultor = new EquivalenceCalculator();

    @Test
    public void testGetStringResult1(){
        assertEquals(equivalenceCalcultor.getResultString(0),"If everyone in Vancouver " +
                "had the same diet as you, the amount of CO2e reduced would be equivalent to taking " +
                equivalenceCalcultor.getCarEquivalence(0) +
                " thousand cars off of the road");
    }

    @Test
    public void testGetStringResult2(){
        assertEquals(equivalenceCalcultor.getResultString(100),"If everyone in Vancouver had " +
                "the same diet as you, the amount of extra CO2e produced would be equivalent to putting "
                + equivalenceCalcultor.getCarEquivalence(100) + " thousand cars on the road");
    }
    /* Color Class Definition does not exist outside of android environment so cannot test
    @Test
    public void testGetUserColor1(){
        assertEquals(equivalenceCalcultor.getUserColor(1000), Color.parseColor("#4d0000"));
    }

    @Test
    public void testGetUserColor2(){
        assertEquals(equivalenceCalcultor.getUserColor(0), 1);
        //Color.parseColor("#99ff66")
    }

    @Test
    public void testGetUserColor3(){
        assertEquals(equivalenceCalcultor.getUserColor(2), Color.parseColor("#ff9933"));
    }
    */

}