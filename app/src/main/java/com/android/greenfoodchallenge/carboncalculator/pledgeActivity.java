package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class pledgeActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context){
        Intent intent =new Intent(context, pledgeActivity.class);
        return intent;
    }

    private DatabaseReference mDatabase;
    private EditText mNameField, mCO2Field;
    private Button submitPledgeButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pledge);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = findViewById(R.id.nameField);
        mCO2Field = findViewById(R.id.co2Field);
        submitPledgeButton = findViewById(R.id.submitPledgeButton);

        submitPledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPledge();
            }
        });
    }
    /*
     *
     *
     * SUBMIT PLEDGE METHODS
     *
     *
     */

    private void submitPledge(){
        final String name = mNameField.getText().toString();
        final String pledge = mCO2Field.getText().toString();
        //setEditingEnabled(false);
        //Toast.makeText(pledgeActivity.this,"C1", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        Resources res = getResources();

        Map<String, Object> note = new HashMap<>();
        //note.put("UserId", userId);
        note.put("Name", name);
        note.put("Pledge", pledge);

        mDatabase.child("users").child(userId).setValue(note);

    }

    /*
    public void getAuthExtras(){
        Bundle authData = this.getIntent().getExtras();
        userId = authData.getString("userId");
    }*/
}