package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewMealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Meal> databaseMeals;
    private DatabaseReference mealDatabase;
    private String currentFilterLocation = "";
    private String currentFilterProtein = "Tags";
    private ArrayList<String> mealURLs;
    private StorageReference mStorageRef;
    private EditText locationField;
    private BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal);
        databaseMeals = new ArrayList<>();
        mealURLs = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mealDatabase = FirebaseDatabase.getInstance().getReference("meals");
        locationField = findViewById(R.id.filterByLocation);
        setupFilterButton();
        setupProteinDropDown();

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);
        mBottomNavigation.getMenu().findItem(R.id.nav_viewmeal).setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        finish();
                        Intent goToHome = new Intent(ViewMealActivity.this, HomeDashboard.class);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_CLEAR_TASK);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(goToHome);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_viewmeal:
                        break;

                    case R.id.nav_addmeal:
                        finish();
                        Intent goToAddMeal = new Intent(ViewMealActivity.this, AddMeal.class);
                        goToAddMeal.addFlags(goToAddMeal.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToAddMeal);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_profile:
                        finish();
                        Intent goToProfile = new Intent(ViewMealActivity.this, ProfileActivity.class);
                        goToProfile.addFlags(goToProfile.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToProfile);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }

    private void filterMeal(){
        ArrayList<Meal> filteredMeals = new ArrayList<>();
        if(currentFilterLocation.equals("") && currentFilterProtein.equals("Tags")){
            for(Meal meal : databaseMeals){
                filteredMeals.add(meal);
            }
        } else if(!currentFilterLocation.equals("") && !currentFilterProtein.equals("Tags")){
            for(Meal meal : databaseMeals){
                if(meal.getLocation().toUpperCase().contains(currentFilterLocation) && meal.getTags().equals(currentFilterProtein)){
                    filteredMeals.add(meal);
                }
            }
        } else if(currentFilterLocation.equals("") && !currentFilterProtein.equals("Tags")){
            for(Meal meal : databaseMeals){
                if(meal.getTags().equals(currentFilterProtein)){
                    filteredMeals.add(meal);
                }
            }
        } else if(!currentFilterLocation.equals("") && currentFilterProtein.equals("Tags")){
            for(Meal meal : databaseMeals){
                if(meal.getLocation().toUpperCase().contains(currentFilterLocation)){
                    filteredMeals.add(meal);
                }
            }
        }
        updateRecyclerView(filteredMeals);
    }

    private void setupProteinDropDown(){
        Spinner spinner = findViewById(R.id.spinnerProtein);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.protein, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setupFilterButton(){
        Button button = findViewById(R.id.btnFilter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilterLocation = locationField.getText().toString().toUpperCase();
                Log.d("Myapp", "Current filterLocation is: " + currentFilterLocation);
                filterMeal();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mealDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseMeals.clear();
                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()){
                    Meal meal = mealSnapshot.getValue(Meal.class);
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
        MealItemAdapter mMealItemAdapter = new MealItemAdapter(this, specificMeals);
        recyclerView.setAdapter(mMealItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String filter = parent.getItemAtPosition(position).toString();
        switch(parent.getId()){
            case R.id.spinnerProtein:
                currentFilterProtein = filter;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, ViewMealActivity.class);
        return intent;
    }
}
