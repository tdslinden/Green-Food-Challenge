package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DeleteMealActivity extends AppCompatActivity {

    private ArrayList<Meal> databaseMeals;
    private ArrayList<String> databaseMealIDs;
    private DatabaseReference mealDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private StorageReference mStorageRef;
    private Button backButton;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_meal);
        databaseMeals = new ArrayList<>();
        databaseMealIDs = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        userID = mFirebaseUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mealDatabase = FirebaseDatabase.getInstance().getReference("meals");

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                databaseMealIDs.clear();
                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()){
                    if(mealSnapshot.getKey().contains(userID)){
                        Meal meal = mealSnapshot.getValue(Meal.class);
                        if(meal.isValidMeal()) {
                            databaseMeals.add(meal);
                            databaseMealIDs.add(mealSnapshot.getKey());
                        }
                    }
                }
                updateRecyclerView(databaseMeals, databaseMealIDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateRecyclerView(ArrayList<Meal> specificMeals, ArrayList<String> specificIDs){
        RecyclerView recyclerView = findViewById(R.id.listMeals);
        DeleteMealItemAdapter mMealItemAdapter = new DeleteMealItemAdapter(this, specificMeals, specificIDs);
        recyclerView.setAdapter(mMealItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, DeleteMealActivity.class);
        return intent;
    }
}
