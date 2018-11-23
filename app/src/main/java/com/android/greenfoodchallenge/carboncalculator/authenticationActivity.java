package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class authenticationActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private Button logIn;
    private EditText emailText;
    private EditText passwordText;
    private TextView toRegistration;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mFirebaseAuth = FirebaseAuth.getInstance();

        toRegistration = (TextView) findViewById(R.id.sendToRegistration);
        toRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegistration = new Intent(authenticationActivity.this, Registration.class);
                goToRegistration.addFlags(goToRegistration.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToRegistration);
                overridePendingTransition(0,0);
            }
        });

        logIn = (Button) findViewById(R.id.loginButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);

    }

    private void userLogin(){
        Editable editableEmail = emailText.getText();
        String email = editableEmail.toString().trim();
        Editable editablePassword = passwordText.getText();
        String password = editablePassword.toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;

        }

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            Intent goToHome = new Intent(authenticationActivity.this, HomeDashboard.class);
                            goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(goToHome);
                            overridePendingTransition(0,0);
                        }else{
                            Toast.makeText(authenticationActivity.this, "The email or password you have entered is incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /*
    * Currently only 1 sign in option is available
    * Probably simplifies things to have one sign in option so each user only has one account
    * Calling this method signs the user in and sends them to the pledge page
    */
    public void createSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    /*
    * Starts after calling the createSignInIntent
    * Sends the user to the pledge activity afterwards
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
                //Used to verify that you have been given a userId, remove before the end of the sprint

                //There is more data available in FirebaseUser that can be accessed and bundled here
                //Once we finalize what we want in the database, more things can be added to the bundle

                finish();
                Intent goToHome = new Intent(authenticationActivity.this, HomeDashboard.class);
                goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goToHome);
                overridePendingTransition(0,0);

            }
        }
    }

    public void authenticateUser(View v) {
        createSignInIntent();
    }

}

