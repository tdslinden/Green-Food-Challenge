package com.android.greenfoodchallenge.carboncalculator;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class AddMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private EditText mealField;
    private EditText restaurantField;
    private EditText locationField;
    private EditText description;
    private Button submitMeal;
    private TextView addMeal;
    private TextView tagsView;
    private String userId;
    private Button upload;
    private ImageView photo;
    private Uri mImageUri;
    private boolean mLocationPermissionGranted;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setupViewPledgeButton();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        getUserId();

        mealField = findViewById(R.id.meal);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        photo = findViewById(R.id.imageView);
        submitMeal = findViewById(R.id.submitPledgeButton);
        tagsView = findViewById(R.id.textbox2);
        addMeal = findViewById(R.id.textbox1);
        tagOptions = findViewById(R.id.tags);

        addMeal.setText(getString(R.string.addMealActivity));
        tagsView.setText(getString(R.string.proteinField));

        // allows user to upload a photo if they so choose
        upload.setOnClickListener(view -> {
            chooseImage();
        });

        // spinner for proteins
        ArrayAdapter<String> myAdaptar = new ArrayAdapter<>(AddMeal.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.protein));
        myAdaptar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagOptions.setAdapter(myAdaptar);
        tagOptions.setOnItemSelectedListener(this);

        submitMeal.setOnClickListener(v -> {
            // If the user somehow manages to get past authentication, notify them and don't accept any inputs
            if(userId == null){
                Toast.makeText(AddMeal.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
            }
            else {
                submitMealButton();
            }
        });

        //Prompts request to access location
        getLocationPermission();
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
        final String restaurant = restaurantField.getText().toString();
        final String location = locationField.getText().toString();
        final String details = description.getText().toString();

        Map<String, Object> storage;

        if (meal.equals("") || tags.equals("Tags") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            AddMealHelper mealToFirebase = new AddMealHelper();

            if(mImageUri != null) {
                uploadPhoto();

                storage = mealToFirebase.addToFirebase(meal, tags, restaurant, location, details, userId);
                mDatabase.child("users").child(userId).child("meal").setValue(storage);
                Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();
            } else {
                storage = mealToFirebase.addToFirebase(meal, tags, restaurant, location, details, "");
                mDatabase.child("users").child(userId).child("meal").setValue(storage);
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


    //Prompts pop up to give location permission
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }
}
