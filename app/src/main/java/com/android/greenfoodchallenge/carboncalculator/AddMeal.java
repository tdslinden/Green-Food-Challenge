package com.android.greenfoodchallenge.carboncalculator;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class AddMeal extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mealDatabase;
    private StorageReference mStorageRef;
    private EditText mealField;
    private EditText tagsField;
    private EditText restaurantField;
    private EditText locationField;
    private EditText description;
    private Button submitMeal;
    private TextView addMeal;
    private String userId;
    private Button upload;
    private ImageView photo;
    private Uri mImageUri;
    private Integer mealCount;

    private ArrayList<Meal> userData;
    private DatabaseReference database;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setupViewPledgeButton();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mealDatabase = FirebaseDatabase.getInstance().getReference("users");

        getUserId();

        mealField = findViewById(R.id.meal);
        tagsField = findViewById(R.id.tags);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        photo = findViewById(R.id.imageView);
        submitMeal = findViewById(R.id.submitPledgeButton);

        addMeal = findViewById(R.id.textbox1);
        addMeal.setText(getString(R.string.addMealActivity));

        // allows user to upload a photo if they so choose
        upload.setOnClickListener(view -> {
            chooseImage();
        });

        submitMeal.setOnClickListener(v -> {
            // If the user somehow manages to get past authentication, notify them and don't accept any inputs
            if(userId == null){
                Toast.makeText(AddMeal.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
            }
            else {
                submitMealButton();
            }
        });

        userData = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("users/meal");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mealDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData.clear();
                for(DataSnapshot pledgeSnapshot : dataSnapshot.getChildren()){
                    if(pledgeSnapshot.getKey().equals(userId)){
                        Pledge userInfo = pledgeSnapshot.getValue(Pledge.class);
                        Meal userMeal = userInfo.getMeal();
                        userData.add(userMeal);
                        mealCount = (int) userInfo.getMealCount();
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

    private String getExtension(Uri mImageUri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(mImageUri));
    }

    private void uploadPhoto() {
        if(photo != null) {
            StorageReference fileReference = mStorageRef.child(userId + System.currentTimeMillis() + "." + getExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(AddMeal.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(AddMeal.this, "Upload failed", Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(taskSnapshot -> Toast.makeText(AddMeal.this, "Upload in progress", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(AddMeal.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitMealButton(){
        final String meal = mealField.getText().toString();
        final String tags = tagsField.getText().toString();
        final String restaurant = restaurantField.getText().toString();
        final String location = locationField.getText().toString();
        final String details = description.getText().toString();

        Map<String, Object> storage;

        if (meal.equals("") || tags.equals("") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            AddMealHelper mealToFirebase = new AddMealHelper();
            if(mImageUri != null) {

                Log.d("Myapp", "HI !");

                uploadPhoto();

                mealCount++;

                String mealCountText = Integer.toString(mealCount);

                storage = mealToFirebase.addToFirebase(meal, tags, restaurant, location, details, userId, mealCount);
                mDatabase.child("users").child(userId).child("meal" + mealCountText).setValue(storage);
                Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();



            } else {

                mealCount++;

                String mealCountText = Integer.toString(mealCount);

                storage = mealToFirebase.addToFirebase(meal, tags, restaurant, location, details, "", mealCount);
                mDatabase.child("users").child(userId).child("meal" + mealCountText).setValue(storage);

                Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();


            }

        }
    }

    //Gets user ID from authentication
    public void getUserId(){
        Bundle pledgeUserId = this.getIntent().getExtras();
        userId = pledgeUserId.getString("userId");
    }

    private void setupViewPledgeButton(){
        Button button = findViewById(R.id.viewPledgeButton);
        button.setOnClickListener(v -> {
            Intent intent = ViewPledgeActivity.makeIntentWithUID(AddMeal.this, userId);
            startActivity(intent);
        });
    }
}
