package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Map;

import static com.android.greenfoodchallenge.carboncalculator.MapsActivity.locationRestaurant;
import static com.android.greenfoodchallenge.carboncalculator.MapsActivity.nameRestaurant;

public class AddMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mDatabase;
    private DatabaseReference mealCounterDatabase;
    private DatabaseReference mealDatabase;
    private StorageReference mStorageRef;
    private DatabaseReference database;
    private EditText mealField;
    private EditText restaurantField;
    private EditText locationField;
    private EditText description;
    private Button submitMeal;
    private TextView addMeal;
    private String userId;
    private ImageView photo;
    private Uri mImageUri;
    private MealCount userCount;
    private String mealPhotoPath;
    private BottomNavigationView mBottomNavigation;
    private String resName;
    private String resAddress;
    private TextView tagsView;
    private Spinner tagOptions;
    private String tags;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        setupMapsButton();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mealCounterDatabase = FirebaseDatabase.getInstance().getReference("mealCounter");
        mealDatabase = FirebaseDatabase.getInstance().getReference("meals");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        mealField = findViewById(R.id.meal);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        description = findViewById(R.id.description);
        photo = findViewById(R.id.imageView);
        submitMeal = findViewById(R.id.submitPledgeButton);
        tagsView = findViewById(R.id.textbox2);

        tagsView = findViewById(R.id.textbox2);
        addMeal = findViewById(R.id.textbox1);
        tagOptions = findViewById(R.id.tags);

        addMeal.setText(getString(R.string.addMealActivity));
        tagsView.setText(getString(R.string.proteinField));

        // spinner for proteins
        ArrayAdapter<String> myAdaptar = new ArrayAdapter<>(AddMeal.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.protein));
        myAdaptar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagOptions.setAdapter(myAdaptar);
        tagOptions.setOnItemSelectedListener(this);


        // allows user to upload a photo if they so choose
        photo.setOnClickListener(view -> {
            chooseImage();
        });

        submitMeal.setOnClickListener(v -> {
            // If the user somehow manages to get past authentication, notify them and don't accept any inputs
            if(userId == null){
                Toast.makeText(AddMeal.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
            } else {
                submitMealButton();
                Intent goToViewMeals = new Intent(AddMeal.this, ViewMealActivity.class);
                startActivity(goToViewMeals);
            }
        });

        database = FirebaseDatabase.getInstance().getReference("users/meal");

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_nav);
        mBottomNavigation.getMenu().findItem(R.id.nav_addmeal).setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        finish();
                        Intent goToHome = new Intent(AddMeal.this, HomeDashboard.class);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_CLEAR_TASK);
                        goToHome.addFlags(goToHome.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(goToHome);
                        nameRestaurant = "";
                        locationRestaurant = "";
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_viewmeal:
                        finish();
                        Intent goToViewMeal = new Intent(AddMeal.this, ViewMealActivity.class);
                        goToViewMeal.addFlags(goToViewMeal.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToViewMeal);
                        nameRestaurant = "";
                        locationRestaurant = "";
                        overridePendingTransition(0,0);
                        break;

                    case R.id.nav_addmeal:
                        break;

                    case R.id.nav_profile:
                        finish();
                        Intent goToProfile = new Intent(AddMeal.this, ProfileActivity.class);
                        goToProfile.addFlags(goToProfile.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goToProfile);
                        nameRestaurant = "";
                        locationRestaurant = "";
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle storage = this.getIntent().getExtras();

        if (storage != null) {
            resName = storage.getString("restaurantName");
            resAddress = storage.getString("restaurantAddress");
        }

        if(nameRestaurant != null && locationRestaurant != null)
        {
            restaurantField.setText(nameRestaurant);
            locationField.setText(locationRestaurant);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mealCounterDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot pledgeSnapshot : dataSnapshot.getChildren()){
                    if(pledgeSnapshot.getKey().equals(userId)){
                        userCount = pledgeSnapshot.getValue(MealCount.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    // uploads the photo to the app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(photo);
        }
    }

    private void uploadPhoto(String filePath) {
        if(photo != null) {
            StorageReference fileReference = mStorageRef.child(filePath);

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(AddMeal.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        //Store URL in firebase
                        mStorageRef.child(filePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d("myapp", "upload successful and url is " + uri.toString());
                                mealDatabase.child(filePath).child("MealPhoto").setValue(uri.toString());
                            }
                        });
                    })

                    .addOnFailureListener(e -> Toast.makeText(AddMeal.this, "Upload failed", Toast.LENGTH_SHORT).show())

                    .addOnProgressListener(taskSnapshot -> Toast.makeText(AddMeal.this, "Upload in progress", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(AddMeal.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitMealButton(){
        final String meal = mealField.getText().toString();
        String restaurant = restaurantField.getText().toString();
        String location = locationField.getText().toString();
        final String details = description.getText().toString();

        Map<String, Object> storage;

        if (meal.equals("") || tags.equals("Tags") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            mealPhotoPath = "";

            AddMealHelper mealToFirebase = new AddMealHelper();

            if(userCount == null){
                MealCount newMealCount = new MealCount();
                mDatabase.child("mealCounter").child(userId).setValue(newMealCount);
                userCount = newMealCount;
            }

            userCount.setMealCount(userCount.getMealCount() + 1);

            String mealCountText = Integer.toString((int) userCount.getMealCount());

            if(mImageUri != null) {
                mealPhotoPath = userId + mealCountText;

                uploadPhoto(mealPhotoPath);
            }

            storage = mealToFirebase.addToFirebase(meal, tags, restaurant, location, details, "");

            mDatabase.child("meals").child(userId + mealCountText).setValue(storage);
            mDatabase.child("mealCounter").child(userId).setValue(userCount);

            Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupMapsButton() {
        Button button = findViewById(R.id.maps);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tags = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}



