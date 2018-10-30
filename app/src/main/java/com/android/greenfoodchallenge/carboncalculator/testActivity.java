package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


//Allows users to fill in edit boxes and have that information sent to the firestore database
public class testActivity extends AppCompatActivity {
    public static Intent makeIntent(Context context){
        Intent intent =new Intent(context, testActivity.class);
        return intent;
    }

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText editTextTitle, editTextDesc;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        button1 = findViewById(R.id.Button1);
        editTextTitle = findViewById(R.id.Title);
        editTextDesc = findViewById(R.id.TitleDesc);
    }

    //Required for authentication
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //For storing and sending information filled out in the text fields
    //sends the information to the firestore db
    public void saveNote(View v){
        String title = editTextTitle.getText().toString();
        String desc = editTextDesc.getText().toString();
        Map<String, Object> note = new HashMap<>();
        note.put("Title", title);
        note.put("Desc", desc);

        db.collection("Notebook").document("A THIRD NOTE").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(testActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(testActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
