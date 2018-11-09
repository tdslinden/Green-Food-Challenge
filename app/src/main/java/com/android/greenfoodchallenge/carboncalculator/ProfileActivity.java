package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// displays user's profile activity
public class ProfileActivity extends AppCompatActivity {
    private static final String EXTRA_UID = "com.android.greenfoodchallenge.carboncalculator.ProfileActivity - UID";
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> userDatabasePledges;
    DatabaseReference pledgeDatabase;
    private String userID;
    private long userTotalCO2;
    private long userAvgCO2;
    private long userTotalPledges;
    Button removePledge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        extractDataFromIntent();
        userTotalCO2 = 0;
        userAvgCO2 = 0;
        userTotalPledges = 0;
        stringPledges = new ArrayList<>();
        userDatabasePledges = new ArrayList<>();
        pledgeDatabase = FirebaseDatabase.getInstance().getReference("users");
        removePledge = findViewById(R.id.remove_button);
        removePledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID == null){
                    Toast.makeText(ProfileActivity.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                }
                else{
                    pledgeDatabase.child(userID).child("Pledge").setValue(0);
                }
            }
        });

        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pledgeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDatabasePledges.clear();
                for(DataSnapshot pledgeSnapshot : dataSnapshot.getChildren()){
                    if(pledgeSnapshot.getKey().equals(userID)) {
                        Pledge pledge = pledgeSnapshot.getValue(Pledge.class);
                        userDatabasePledges.add(pledge);
                    }
                }

                userTotalCO2 = 0;
                for(Pledge user : userDatabasePledges){
                    userTotalCO2 += user.getPledge();
                }

                userTotalPledges = userDatabasePledges.size();
                userAvgCO2 = 0;
                if (userTotalPledges > 0){
                    userAvgCO2 = userTotalCO2 / userTotalPledges;
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
        updateGraph();
        updateInfomatics();
        updateRecyclerView();
    }

    private void updateGraph() {
        //Refresh graph statistics
        //Add graph points here
    }

    private void updateInfomatics() {
        TextView txtTotalCO2 = findViewById(R.id.txtTotalCO2);
        TextView txtAvgCO2 = findViewById(R.id.txtAvgCO2);
        TextView txtTotalPledges = findViewById(R.id.txtTotalPledges);
        txtTotalCO2.setText("Your Total Tonnes of CO2e Pledged: " + Long.toString(userTotalCO2));
        txtAvgCO2.setText("Your Average CO2e Pledged: " + Long.toString(userAvgCO2));
        txtTotalPledges.setText("Your Total Pledges Made: " + Long.toString(userTotalPledges));
    }

    private void updateRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.listPledges);
        PledgeRecylerViewAdapter adapter = new PledgeRecylerViewAdapter(userDatabasePledges, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        userID = intent.getStringExtra(EXTRA_UID);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    public static Intent makeIntentWithUID(Context context, String userID) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_UID, userID);
        return intent;
    }
}
