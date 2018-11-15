package com.android.greenfoodchallenge.carboncalculator;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class AddMeal extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText mealField;
    private EditText proteinField;
    private EditText restaurantField;
    private EditText locationField;
    private EditText description;
    private Button submitMeal;
    private TextView addMeal;
    private String userId;
    private Button upload;
    private ImageView photo;
    private String mealPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setupViewPledgeButton();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getUserId();

        mealField = findViewById(R.id.meal);
        proteinField = findViewById(R.id.protein);
        restaurantField = findViewById(R.id.restaurant);
        locationField = findViewById(R.id.location);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        submitMeal = findViewById(R.id.submitPledgeButton);

        addMeal = findViewById(R.id.textbox1);
        addMeal.setText(getString(R.string.addMealActivity));

        // allows user to upload a photo if they so choose
        upload.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, 1);
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
    }

    // uploads the photo to the app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            photo = findViewById(R.id.icon);
            photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            Bitmap bitmap =  BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmap.recycle();
            byte[] byteArray = stream.toByteArray();
            mealPhoto = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    private void submitMealButton(){
        final String meal = mealField.getText().toString();
        final String protein = proteinField.getText().toString();
        final String restaurant = restaurantField.getText().toString();
        final String location = locationField.getText().toString();
        final String details = description.getText().toString();

        Map<String, Object> storage;

        if (meal.equals("") || protein.equals("") || restaurant.equals("") || location.equals("")) {
            Toast.makeText(AddMeal.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            AddMealHelper mealToFirebase = new AddMealHelper();

            storage = mealToFirebase.addToFirebase(meal, protein, restaurant, location, details, mealPhoto);
            mDatabase.child("users").child(userId).child("meal").setValue(storage);
            Toast.makeText(AddMeal.this, "Accepted", Toast.LENGTH_SHORT).show();
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
