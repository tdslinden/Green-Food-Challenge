package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String EXTRA_UID = "com.android.greenfoodchallenge.carboncalculator.ProfileActivity - UID";
    private static final int GET_FROM_GALLERY = 0;
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> userDatabasePledges;
    DatabaseReference pledgeDatabase;
    private String userID;
    private long userTotalCO2;
    private long userAvgCO2;
    private long userTotalPledges;
    private BottomNavigationView mBottomNavigation;
    private Uri selectedImage;
    private Button signOutButton;
    private FirebaseAuth mFirebaseAuth;
    Button removePledge;
    ImageButton mProfilePicture;

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

        mFirebaseAuth = FirebaseAuth.getInstance();
        signOutButton = (Button)findViewById(R.id.signoutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        removePledge = (Button)findViewById(R.id.remove_button);
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

        mProfilePicture = (ImageButton) findViewById(R.id.profilePicture);
        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),GET_FROM_GALLERY);
            }
        });

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);
        mBottomNavigation.getMenu().findItem(R.id.nav_profile).setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        finish();
                        Intent goToHome = new Intent(ProfileActivity.this, HomeDashboard.class);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToHome);
                        break;

                    case R.id.nav_addmeal:
                        finish();
                        Intent goToAddMeal = new Intent(ProfileActivity.this, AddMeal.class);
                        goToAddMeal.addFlags(goToAddMeal.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToAddMeal);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_profile:
                        break;

                }
                return true;
            }
        });

        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                RoundedBitmapDrawable userPicture = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                userPicture.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
                mProfilePicture.setBackground(userPicture);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    }
                });
        mFirebaseAuth.signOut();
        finish();
        Intent goToLogin = new Intent(ProfileActivity.this, authenticationActivity.class);
        goToLogin.addFlags(goToLogin.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(goToLogin);
        overridePendingTransition(0,0);
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
        TextView txtTotalCO2 = (TextView)findViewById(R.id.txtTotalCO2);
        TextView txtAvgCO2 = (TextView)findViewById(R.id.txtAvgCO2);
        TextView txtTotalPledges = (TextView)findViewById(R.id.txtTotalPledges);
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
