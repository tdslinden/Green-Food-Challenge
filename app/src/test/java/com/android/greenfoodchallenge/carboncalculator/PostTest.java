package com.android.greenfoodchallenge.carboncalculator;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PostTest {
    Post post = new Post();
    @Test
    public void testmakePost(){
        Map<String, Object> testNote = new HashMap<>();
        String name = "someName";
        String region = "someRegion";
        int co2Pledge = 5;
        testNote.put("Name", name);
        testNote.put("Region", region);
        testNote.put("Pledge", co2Pledge);

        assertEquals(testNote, post.makePost(name, region, co2Pledge));
    }
    @Test
    public void testmakePost2(){
        Map<String, Object> testNote = new HashMap<>();
        String name = "";
        String region = "";
        int co2Pledge = 0;
        testNote.put("Name", name);
        testNote.put("Region", region);
        testNote.put("Pledge", co2Pledge);

        assertEquals(testNote, post.makePost(name, region, co2Pledge));
    }
}