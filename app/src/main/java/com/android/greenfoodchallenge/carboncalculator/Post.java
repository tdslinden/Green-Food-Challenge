package com.android.greenfoodchallenge.carboncalculator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


//A class to create a map for the pledge page
@IgnoreExtraProperties
public class Post {


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Map<String, Object> makePost(String name, String region, int co2Pledge){
        Map<String, Object> note = new HashMap<>();
        note.put("Name", name);
        note.put("Region", region);
        note.put("Pledge", co2Pledge);
        return note;
    }

}

