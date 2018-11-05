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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPledgeActivity extends AppCompatActivity {
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> databasePledges;
    private DatabaseReference pledgeDatabase;
    private long totalCO2;
    private long avgCO2;
    private long totalPledges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pledge);
        totalCO2 = 0;
        avgCO2 = 0;
        totalPledges = 0;
        stringPledges = new ArrayList<>();
        databasePledges = new ArrayList<>();
        pledgeDatabase = FirebaseDatabase.getInstance().getReference("users");
        updateUI();
        setupProfileButton();
    }


    @Override
    protected void onStart() {
        super.onStart();
        pledgeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databasePledges.clear();
                stringPledges.clear();
                for(DataSnapshot pledgeSnapshot : dataSnapshot.getChildren()){
                    Pledge pledge = pledgeSnapshot.getValue(Pledge.class);
                    databasePledges.add(pledge);
                    stringPledges.add(pledge.getName() + " has pledged " + Long.toString(pledge.getPledge()) + " CO2");
                }
                totalCO2 = 0;
                Log.d("MyApp", "databasepledges size = " + Integer.toString(databasePledges.size()));
                for(Pledge user : databasePledges){
                    totalCO2 += user.getPledge();
                }
                totalPledges = databasePledges.size();
                avgCO2 = 0;
                if (totalPledges > 0){
                    avgCO2 = totalCO2/totalPledges;
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupProfileButton(){
        Button button = findViewById(R.id.btnProfileActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfileActivity.makeIntent(ViewPledgeActivity.this);
                startActivity(intent);
            }
        });

    }

    private void updateUI(){
        Log.d("Myapp", "Hi updateUI");
        updateInfomatics();
        updateRecyclerView();
    }

    private void updateInfomatics(){
        TextView txtTotalCO2 = (TextView)findViewById(R.id.txtTotalCO2);
        TextView txtAvgCO2 = (TextView)findViewById(R.id.txtAvgCO2);
        TextView txtTotalPledges = (TextView)findViewById(R.id.txtTotalPledges);
        Log.d("MyApp", "total CO2 = " + String.valueOf(totalCO2));
        txtTotalCO2.setText("Total CO2 Pledged: " + String.valueOf(totalCO2));
        txtAvgCO2.setText("Average CO2 Pledged: " + Long.toString(avgCO2));
        txtTotalPledges.setText("Total Pledges Made: " + Long.toString(totalPledges));
    }
    private void updateRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.listPledges);
        PledgeRecylerViewAdapter adapter = new PledgeRecylerViewAdapter(stringPledges, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, ViewPledgeActivity.class);
        return intent;
    }
}
