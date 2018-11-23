package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPledgeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String EXTRA_UID = "com.android.greenfoodchallenge.carboncalculator.ViewPledgeActivity - UID";
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> databasePledges;
    private DatabaseReference pledgeDatabase;
    private EquivalenceCalculator calculator = new EquivalenceCalculator();
    private String userID;
    private Button backButton;
    private long totalCO2;
    private long avgCO2;
    private long totalPledges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pledge);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        extractDataFromIntent();
        totalCO2 = 0;
        avgCO2 = 0;
        totalPledges = 0;
        stringPledges = new ArrayList<>();
        databasePledges = new ArrayList<>();
        pledgeDatabase = FirebaseDatabase.getInstance().getReference("users");
        updateUI(databasePledges);
        setupCityDropDown();
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
                    if(pledge.getPledge() != 0) {
                        databasePledges.add(pledge);
                    }
                }
                totalCO2 = 0;
                for(Pledge user : databasePledges){
                    totalCO2 += user.getPledge();
                }
                totalPledges = databasePledges.size();
                avgCO2 = 0;
                if (totalPledges > 0){
                    avgCO2 = totalCO2/totalPledges;
                }
                updateUI(databasePledges);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(ArrayList<Pledge> specificPledges){
        updateInfomatics();
        updateRecyclerView(specificPledges);
    }

    private void updateInfomatics(){
        TextView txtTotalCO2 = (TextView)findViewById(R.id.txtTotalCO2);
        TextView txtAvgCO2 = (TextView)findViewById(R.id.txtAvgCO2);
        TextView txtTotalPledges = (TextView)findViewById(R.id.txtTotalPledges);
        TextView txtUserUnderstanding = (TextView) findViewById(R.id.txtUserUnderstanding);
        txtTotalCO2.setText("Total Tonnes of CO2e Pledged: " + String.valueOf(totalCO2));
        txtAvgCO2.setText("Average CO2e per person Pledged: " + Long.toString(avgCO2));
        txtTotalPledges.setText("Total Pledges Made: " + Long.toString(totalPledges));
        int carEquivalence = (int) (totalCO2/7.4);
        txtUserUnderstanding.setText("In total, " + Integer.toString(carEquivalence) + " cars have been removed off the road!");
    }
    private void updateRecyclerView(ArrayList<Pledge> specificPledges){
        RecyclerView recyclerView = findViewById(R.id.listPledges);
        PledgeRecylerViewAdapter adapter = new PledgeRecylerViewAdapter(specificPledges, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupCityDropDown(){
        Spinner spinner = findViewById(R.id.spinnerCities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void addStringPledge(ArrayList<String> pledgeStringList, Pledge pledge){
        pledgeStringList.add(pledge.getRegion() + " : " + pledge.getName() + " has pledged " + Long.toString(pledge.getPledge()) + " CO2");
    }

    private void calculateInformatics(ArrayList<Pledge> pledgeList){
        totalCO2 = 0;
        for(Pledge user : pledgeList){
            totalCO2 += user.getPledge();
        }
        totalPledges = pledgeList.size();
        avgCO2 = 0;
        if (totalPledges > 0){
            avgCO2 = totalCO2/totalPledges;
        }
    }

    private void filterCity(String city){
        ArrayList<Pledge> filteredPledges = new ArrayList<>();
        for(Pledge pledge : databasePledges){
            if(city.equals("All")){
                filteredPledges.add(pledge);
            }
            else {
                if (city.equals(pledge.getRegion())) {
                    filteredPledges.add(pledge);
                }
            }
        }
        calculateInformatics(filteredPledges);
        updateUI(filteredPledges);
    }

    private void extractDataFromIntent(){
        Intent intent = getIntent();
        userID = intent.getStringExtra(EXTRA_UID);
    }
    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, ViewPledgeActivity.class);
        return intent;
    }

    public static Intent makeIntentWithUID(Context context, String userID){
        Intent intent = new Intent(context, ViewPledgeActivity.class);
        intent.putExtra(EXTRA_UID, userID);
        return intent;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String city = parent.getItemAtPosition(position).toString();
        filterCity(city);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
