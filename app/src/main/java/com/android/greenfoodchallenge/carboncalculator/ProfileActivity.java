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
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String EXTRA_UID = "com.android.greenfoodchallenge.carboncalculator.ProfileActivity - UID";
    private static final int GET_FROM_GALLERY = 0;
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> userDatabasePledges;
    DatabaseReference pledgeDatabase, mealDatabase;
    private long userTotalCO2;
    private long userAvgCO2;
    private long userTotalPledges;
    private BottomNavigationView mBottomNavigation;
    private Uri selectedImage;
    private Button signOutButton, editProfile;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String userId;
    private RoundedBitmapDrawable userPicture;
    private Button removePledge, removeMeal;
    private CircularImageView mProfilePicture;

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
        mealDatabase = FirebaseDatabase.getInstance().getReference("meals");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        userId = mFirebaseUser.getUid();

        editProfile = (Button)findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId == null){
                    Toast.makeText(ProfileActivity.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(editProfile.getText().equals("Edit Profile")){
                        removePledge.setVisibility(View.VISIBLE);
                        removeMeal.setVisibility(View.VISIBLE);
                        editProfile.setText("Done");
                    }
                    else{
                        removePledge.setVisibility(View.GONE);
                        removeMeal.setVisibility(View.GONE);
                        editProfile.setText("Edit Profile");
                    }
                }

            }
        });

        signOutButton = (Button)findViewById(R.id.signoutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        removePledge = (Button)findViewById(R.id.remove_pledge);
        removePledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pledgeDatabase.child(userId).child("Pledge").removeValue();
            }
        });

        removeMeal = (Button)findViewById(R.id.remove_meal);
        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mealDatabase.child(userId).removeValue();
            }
        });

        mProfilePicture = (CircularImageView) findViewById(R.id.profilePicture);
        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),GET_FROM_GALLERY);
            }
        });

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);
//        mBottomNavigation.getMenu().findItem(R.id.nav_profile).setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
//                    case R.id.nav_home:
//                        finish();
//                        Intent goToHome = new Intent(ProfileActivity.this, HomeDashboard.class);
//                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
//                        startActivity(goToHome);
//                        overridePendingTransition(0,0);
//                        break;

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
                userPicture = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
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
                    if(pledgeSnapshot.getKey().equals(userId)) {
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
        goToLogin.addFlags(goToLogin.FLAG_ACTIVITY_CLEAR_TASK);
        goToLogin.addFlags(goToLogin.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goToLogin);
        overridePendingTransition(0,0);
    }

    private void updateUI() {
        updateGraph();
        //updateInfomatics();
        updateRecyclerView();
    }

    private void updateGraph() {
        //Refresh graph statistics
        //Add graph points here
    }


    private void updateRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.listPledges);
        PledgeRecylerViewAdapter adapter = new PledgeRecylerViewAdapter(userDatabasePledges, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getStringExtra(EXTRA_UID);
    }

}
