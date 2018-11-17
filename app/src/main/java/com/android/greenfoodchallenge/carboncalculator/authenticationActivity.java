package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class authenticationActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private Button mBackButton;

    public static Intent makeIntent(Context context){
        Intent intent =new Intent(context, authenticationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mBackButton = (Button) findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Used to verify that you have been given a userId, remove before the end of the sprint

                //There is more data available in FirebaseUser that can be accessed and bundled here
                //Once we finalize what we want in the database, more things can be added to the bundle
                Bundle b = new Bundle();
                String userId = user.getUid();
                b.putString("userId", userId);

                Intent goToPledge = new Intent(authenticationActivity.this, pledgeActivity.class);
                goToPledge.putExtras(b);
                startActivity(goToPledge);

            }
            //This toast occurs if the authentication fails or if the user cancels their authentication while it is still running
            else {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Used to verify that you have been given a userId, remove before the end of the sprint

                //There is more data available in FirebaseUser that can be accessed and bundled here
                //Once we finalize what we want in the database, more things can be added to the bundle
                Bundle b = new Bundle();
                String userId = user.getUid();
                b.putString("userId", userId);

                Intent goToPledge = new Intent(authenticationActivity.this, pledgeActivity.class);
                goToPledge.putExtras(b);
                startActivity(goToPledge);

               // Toast.makeText(authenticationActivity.this, "Failed to authenticate, already logged in.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(authenticationActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
    * Methods that buttons use
    */

    public void authenticateUser(View v) {
        createSignInIntent();
    }

    public void signOutUser(View v) {
        signOut();
    }

}

