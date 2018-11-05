package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, testPledge.class);
        return intent;
    }

    private DatabaseReference mDatabase;
    private EditText mNameField;
    private Button submitPledgeButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = findViewById(R.id.nameField);
        submitPledgeButton = findViewById(R.id.submitPledgeButton);

        getAuthExtras();

        TextView addPledge = findViewById(R.id.textbox1);
        addPledge.setText(getString(R.string.addPledge));

        TextView saveCarbon = findViewById(R.id.textbox2);
        saveCarbon.setText(getString(R.string.saveCarbon));

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Preset_Options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        EditText input = findViewById(R.id.input);
        input.setEnabled(false);
        input.setClickable(false);
        input.setTextColor(Color.parseColor("#ffffff"));

        final String[] pledge = {null};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    pledge[0] = getString(R.string.opt1);
                } else if (i == 1) {
                    pledge[0] = getString(R.string.opt2);
                } else if (i == 2) {
                    pledge[0] = getString(R.string.opt3);
                } else if (i == 3) {
                    pledge[0] = getString(R.string.opt4);
                } else if (i == 4) {
                    pledge[0] = getString(R.string.opt5);
                } else if (i == 5) {
                    input.setEnabled(true);
                    input.setClickable(true);
                    input.setTextColor(Color.parseColor("#000000"));

                    pledge[0] = input.getText().toString();
                }

                submitPledgeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //If the user somehow manages to get on to the pledge page without signing in, notify them and don't accept any inputs
                        if (userId == null) {
                            Toast.makeText(pledgeActivity.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                        } else {
                            submitPledge(pledge);
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            private void submitPledge(String pledge[]) {
                final String name = mNameField.getText().toString();
                final int pledgeInteger = Integer.parseInt(pledge[0]);

                if(name.equals("") || pledge.equals("")) {
                    Toast.makeText(pledgeActivity.this, "You must fill in all the fields", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(pledgeActivity.this, "Accepting...", Toast.LENGTH_SHORT).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                Map<String, Object> note = new HashMap<>();
                note.put("Name", name);
                note.put("Pledge", pledgeInteger);

                mDatabase.child("users").child(userId).setValue(note);

            }
        });
    }

    private void getAuthExtras() {
        Bundle authData = this.getIntent().getExtras();
        userId = authData.getString("userId");
    }
}

