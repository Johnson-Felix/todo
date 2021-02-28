package com.john.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    Button newUserText;
    Button loginButton;
    ImageButton googleButton;
    ProgressBar spinner;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            goToMainPage();
        }
        else {

            setContentView(R.layout.activity_login);

            emailText = (EditText) findViewById(R.id.email_editText);
            passwordText = (EditText) findViewById(R.id.password_editText);
            newUserText = (Button) findViewById(R.id.newUser_TextView);
            loginButton = (Button) findViewById(R.id.login_button);
            googleButton = (ImageButton) findViewById(R.id.google_button);
            spinner = (ProgressBar) findViewById(R.id.progressSpinner);
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                doLogin();
                break;
            case R.id.google_button:
                googleLogin();
                break;

            case R.id.newUser_TextView:
                goToRegisterPage();
                break;
        }
    }

    private void googleLogin() {
        Log.i("google","sign in");
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    private void goToRegisterPage() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    private void goToMainPage() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    private void doLogin() {
        String email = emailText.getText().toString();
        if(email.isEmpty()) {
            emailText.setError("Email Cannot be empty");
            return;
        }
        String password = passwordText.getText().toString();
        if(password.isEmpty()){
            passwordText.setError("Password Cannot be empty");
            return;
        }

        spinner.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                    goToMainPage();
                }
                else{
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }
}