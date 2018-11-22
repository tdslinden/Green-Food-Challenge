package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Meal> databaseMeals;
    private DatabaseReference mealDatabase;
    private String currentFilterLocation = "All";
    private String currentFilterProtein = "All";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal);
        databaseMeals = new ArrayList<>();
        mealDatabase = FirebaseDatabase.getInstance().getReference("users");
        setupCityDropDown();
        setupProteinDropDown();
    }

    private void filterMeal(){
        ArrayList<Meal> filteredMeals = new ArrayList<>();
        if(currentFilterLocation.equals("All") && currentFilterProtein.equals("All")){
            for(Meal meal : databaseMeals){
                filteredMeals.add(meal);
            }
        } else if(!currentFilterLocation.equals("All") && !currentFilterProtein.equals("All")){
            for(Meal meal : databaseMeals){
                if(meal.getLocation().equals(currentFilterLocation) && meal.getTags().equals(currentFilterProtein)){
                    filteredMeals.add(meal);
                }
            }
        } else if(currentFilterLocation.equals("All") && !currentFilterProtein.equals("All")){
            for(Meal meal : databaseMeals){
                if(meal.getTags().equals(currentFilterProtein)){
                    filteredMeals.add(meal);
                }
            }
        } else if(!currentFilterLocation.equals("All") && currentFilterProtein.equals("All")){
            for(Meal meal : databaseMeals){
                if(meal.getLocation().equals(currentFilterLocation)){
                    filteredMeals.add(meal);
                }
            }
        }
        updateRecyclerView(filteredMeals);
    }

    private void setupCityDropDown(){
        Spinner spinner = findViewById(R.id.spinnerCities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setupProteinDropDown(){
        Spinner spinner = findViewById(R.id.spinnerProtein);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.protein, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mealDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseMeals.clear();
                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()){
                    Pledge pledge = mealSnapshot.getValue(Pledge.class);
                    Meal meal = pledge.getMeal();
                    if(meal.isValidMeal()) {
                        databaseMeals.add(meal);
                    }
                }
                filterMeal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateRecyclerView(ArrayList<Meal> specificMeals){
        RecyclerView recyclerView = findViewById(R.id.listMeals);
        MealRecyclerViewAdapter adapter = new MealRecyclerViewAdapter(specificMeals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String filter = parent.getItemAtPosition(position).toString();
        switch(parent.getId()){
            case R.id.spinnerCities:
                currentFilterLocation = filter;
                break;
            case R.id.spinnerProtein:
                currentFilterProtein = filter;
                break;

        }
        filterMeal();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, ViewMealActivity.class);
        return intent;
    }
}
