package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String EXTRA_UID = "com.android.greenfoodchallenge.carboncalculator.ProfileActivity - UID";
    private static final int GET_FROM_GALLERY = 0;
    private ArrayList<String> stringPledges;
    private ArrayList<Pledge> userDatabasePledges;
    DatabaseReference pledgeDatabase, mealDatabase;
    private BottomNavigationView mBottomNavigation;
    private Uri selectedImage;
    private Button signOutButton, editProfile;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String userId;
    private RoundedBitmapDrawable userPicture;
    private Button removePledge, removeMeal;
    private CircularImageView mProfilePicture;
    private ImageView pledgeIcon;
    private TextView pledgeText;
    private EditText editName;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        extractDataFromIntent();

        stringPledges = new ArrayList<>();
        userDatabasePledges = new ArrayList<>();
        pledgeDatabase = FirebaseDatabase.getInstance().getReference("users");
        mealDatabase = FirebaseDatabase.getInstance().getReference("meals");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        userId = mFirebaseUser.getUid();
        editName = (EditText) findViewById(R.id.addName);

        setName();
        setupIconDropDown();
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
                        editName.setVisibility(View.VISIBLE);
                        editProfile.setText("Done");
                    }
                    else{
                        removePledge.setVisibility(View.GONE);
                        removeMeal.setVisibility(View.GONE);
                        editName.setVisibility(View.GONE);
                        setName();
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
                finish();
                Intent deleteMeal = new Intent(ProfileActivity.this, DeleteMealActivity.class);
                deleteMeal.addFlags(deleteMeal.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(deleteMeal);
                overridePendingTransition(0,0);
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
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_viewmeal:
                        finish();
                        Intent goToViewMeal = new Intent(ProfileActivity.this, ViewMealActivity.class);
                        goToViewMeal.addFlags(goToViewMeal.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToViewMeal);
                        overridePendingTransition(0,0);
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
        updatePledge();
    }

    private void updatePledge() {

        pledgeIcon = findViewById(R.id.userIcon);
        pledgeText = findViewById(R.id.pledgeText);

        if(userDatabasePledges.size() == 1) {
            Pledge userPledge = userDatabasePledges.get(0);
            if (userPledge.getIcon().equals("Star")) {
                pledgeIcon.setImageResource(R.drawable.gstar64);
            } else if (userPledge.getIcon().equals("Leaf")) {
                pledgeIcon.setImageResource(R.drawable.leaf64);
            } else if (userPledge.getIcon().equals("Sprout")) {
                pledgeIcon.setImageResource(R.drawable.sprout);
            } else if (userPledge.getIcon().equals("Heart")) {
                pledgeIcon.setImageResource(R.drawable.heart);
            } else if (userPledge.getIcon().equals("Recycle")) {
                pledgeIcon.setImageResource(R.drawable.recycle);
            } else if (userPledge.getIcon().equals("Tree")) {
                pledgeIcon.setImageResource(R.drawable.tree);
            } else {
                pledgeIcon.setImageResource(R.drawable.target);
            }
            pledgeText.setText(getPledgeDescription(userPledge));
        }
    }

    private String getPledgeDescription(Pledge user){

        return user.getRegion() + ": " + user.getName() + " has pledged to reduce their footprint by " + Long.toString(user.getPledge()) + " CO2e.";
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getStringExtra(EXTRA_UID);
    }

    private void setupIconDropDown(){
        Spinner spinner = findViewById(R.id.spinnerIcon);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.icons, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String icon = parent.getItemAtPosition(position).toString();
        switch(parent.getId()){
            case R.id.spinnerIcon:
                if(!icon.equals("Icon")) {
                    pledgeDatabase.child(userId).child("Icon").setValue(icon);
                }
                break;

        }
    }

    private void setName() {
        userName = (TextView) findViewById(R.id.userName);
        String name;
        if(editName.getText() != null){
            name = editName.getText().toString();
            userName.setText(name);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
