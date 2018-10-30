package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class pledgeActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context){
        Intent intent =new Intent(context, testPledge.class);
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

        getAuthExtras();
        
        submitPledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the user somehow manages to get on to the pledge page without signing in, notify them and don't accept any inputs
                if(userId==null){
                    Toast.makeText(pledgeActivity.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                }
                else {
                    submitPledge();
                }
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
    //Currently this only has a name and CO2 pledge, but this can be expanded upon later.
    //A feature that we can add in the future
    private void submitPledge(){
        final String name = mNameField.getText().toString();
        final String pledge = mCO2Field.getText().toString();
        Toast.makeText(this, "Accepting...", Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Map<String, Object> note = new HashMap<>();
        note.put("Name", name);
        note.put("Pledge", pledge);

        mDatabase.child("users").child(userId).setValue(note);

    }

    //Gets user ID from authentication
    public void getAuthExtras(){
        Bundle authData = this.getIntent().getExtras();
        userId = authData.getString("userId");
    }
}
