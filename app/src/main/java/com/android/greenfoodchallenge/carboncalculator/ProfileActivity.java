package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> userDatabasePledges;
    DatabaseReference pledgeDatabase;
    private long userTotalCO2;
    private long userAvgCO2;
    private long userTotalPledges;
    //private String userID;
    private String name = "some name"; //Temporary Check Remove later
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userTotalCO2 = 0;
        userAvgCO2 = 0;
        userTotalPledges = 0;
        stringPledges = new ArrayList<>();
        userDatabasePledges = new ArrayList<>();
        pledgeDatabase = FirebaseDatabase.getInstance().getReference("users");
        updateUI();
        /*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Log.d("myapp", "User ID = " + userID);
        */
    }


    @Override
    protected void onStart() {
        super.onStart();
        pledgeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDatabasePledges.clear();
                for(DataSnapshot pledgeSnapshot : dataSnapshot.getChildren()){
                    Pledge pledge = pledgeSnapshot.getValue(Pledge.class);
                    //UserID Check If statement Here; Change name for userID later
                    if(name.equals(pledge.getName())) {
                        userDatabasePledges.add(pledge);
                        stringPledges.add("You had pledged " + Long.toString(pledge.getPledge()) + " CO2");
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

    private void updateUI(){
        updateGraph();
        updateInfomatics();
        updateRecyclerView();
    }

    private void updateGraph(){
        //Refresh graph statistics
        //Add graph points here
    }

    private void updateInfomatics(){
        TextView txtTotalCO2 = (TextView)findViewById(R.id.txtTotalCO2);
        TextView txtAvgCO2 = (TextView)findViewById(R.id.txtAvgCO2);
        TextView txtTotalPledges = (TextView)findViewById(R.id.txtTotalPledges);
        txtTotalCO2.setText("Your Total Tonnes of CO2e Pledged: " + Long.toString(userTotalCO2));
        txtAvgCO2.setText("Your Average CO2e Pledged: " + Long.toString(userAvgCO2));
        txtTotalPledges.setText("Your Total Pledges Made: " + Long.toString(userTotalPledges));
    }

    private void updateRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.listPledges);
        PledgeRecylerViewAdapter adapter = new PledgeRecylerViewAdapter(stringPledges, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }
}
