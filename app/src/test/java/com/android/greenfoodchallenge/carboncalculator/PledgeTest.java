package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class PledgeTest {
    Pledge pledge = new Pledge();
    Pledge specificPledge = new Pledge("Johnny", 20, "Burnaby");

    @Test
    public void testGetRegion() {
        assertEquals(pledge.getRegion(), "none");
    }

    @Test
    public void testSetRegion() {
        pledge.setRegion("Vancouver");
        assertEquals(pledge.getRegion(), "Vancouver");
    }

    @Test
    public void testGetName() {
        assertEquals(pledge.getName(), "none");
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
}