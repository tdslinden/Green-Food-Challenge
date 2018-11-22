package com.android.greenfoodchallenge.carboncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity{

    private Button signUp;
    private EditText emailText;
    private EditText passwordText;
    private TextView sendToLogin;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() != null){
            finish();
            Intent goToHome = new Intent(Registration.this, HomeDashboard.class);
            goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(goToHome);
            overridePendingTransition(0,0);
        }

        sendToLogin = (TextView) findViewById(R.id.sendToLogin);
        sendToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUp = (Button) findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
    }

    private void registerUser(){
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

        //if Validation is okay
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            Intent goToHome = new Intent(Registration.this, HomeDashboard.class);
                            goToHome.addFlags(goToHome.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(goToHome);
                            overridePendingTransition(0,0);
                        }else{
                            Toast.makeText(Registration.this, "Registered Not Successful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
