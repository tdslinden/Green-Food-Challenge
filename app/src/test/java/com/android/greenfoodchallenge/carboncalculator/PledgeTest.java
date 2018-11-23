package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class PledgeTest {
    Pledge pledge = new Pledge();
    Meal meal = new Meal();
    Pledge specificPledge = new Pledge("Johnny", 20, "Burnaby", "Star", meal);
    Meal specificMeal = specificPledge.getMeal();

    @Test
    public void testGetRegion() {
        assertEquals(pledge.getRegion(), "Unknown");
    }

    @Test
    public void testSetRegion() {
        pledge.setRegion("Vancouver");
        assertEquals(pledge.getRegion(), "Vancouver");
    }

    @Test
    public void testGetName() {
        assertEquals(pledge.getName(), "Unknown");
    }

    @Test
    public void testSetName() {
        pledge.setName("Bob");
        assertEquals(pledge.getName(), "Bob");
    }

    @Test
    public void testGetPledge() {
        assertEquals(pledge.getPledge(), 0, 0.00001);
    }

    @Test
    public void testSetPledge() {
        pledge.setPledge(50);
        assertEquals(pledge.getPledge(), 50, 0.00001);
    }

    @Test
    public void testGetIcon(){
        assertEquals(pledge.getIcon(), "none");
    }

    @Test
    public void testSetIcon(){
        pledge.setIcon("Leaf");
        assertEquals(pledge.getIcon(), "Leaf");
    }

    @Test
    public void testPledgeConstructorName(){
        assertEquals(specificPledge.getName(), "Johnny");
    }

    @Test
    public void testPledgeConstructorPledge(){
        assertEquals(specificPledge.getPledge(), 20, 0.00001);
    }

    @Test
    public void testPledgeConstructorRegion(){
        assertEquals(specificPledge.getRegion(), "Burnaby");
    }

    @Test
    public void testPledgeConstructorIcon(){
        assertEquals(specificPledge.getIcon(), "Star");
    }

    @Test
    public void testGetMeal(){
        assertEquals(specificMeal.getLocation(), "Unknown");
        assertEquals(specificMeal.getMeal(), "Unknown");
        assertEquals(specificMeal.getProtein(), "Unknown");
        assertEquals(specificMeal.getRestaurant(), "Unknown");
    }
}