package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private EditText mNameField, mRegionField,mCO2Field;
    private Button submitPledgeButton;
    private String userId;
    private TextView addPledge;
    private TextView saveCarbon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = findViewById(R.id.nameField);
        mRegionField = findViewById(R.id.regionField);
        mCO2Field = findViewById(R.id.co2Field);
        submitPledgeButton = findViewById(R.id.submitPledgeButton);

        getAuthExtras();

        addPledge = findViewById(R.id.textbox1);
        addPledge.setText(getString(R.string.addPledge));

        saveCarbon = findViewById(R.id.textbox2);
        saveCarbon.setText(getString(R.string.saveCarbon));
        
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
     * Submit Pledge Buttons
     *
     */
    //Currently this only has a name and CO2 pledge, but this can be expanded upon later.
    //A feature that we can add in the future
    private void submitPledge(){
        final String name = mNameField.getText().toString();
        final String pledgeText = mCO2Field.getText().toString();
        final String region = mRegionField.getText().toString();
        final int pledge = Integer.parseInt(pledgeText);

        Map<String, Object> note = new HashMap<>();

        if (name.equals("") || region.equals("")) {
            Toast.makeText(pledgeActivity.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(pledgeActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
            Post post = new Post();

            note = post.makePost(name, region, pledge);
            mDatabase.child("users").child(userId).setValue(note);
            Toast.makeText(pledgeActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
        }
    }

    //Gets user ID from authentication
    public void getAuthExtras(){
        Bundle authData = this.getIntent().getExtras();
        userId = authData.getString("userId");
    }
}
