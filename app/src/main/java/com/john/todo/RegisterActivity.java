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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    TextView newUserText;
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

            setContentView(R.layout.activity_register);

            emailText = (EditText) findViewById(R.id.email_editText);
            passwordText = (EditText) findViewById(R.id.password_editText);
            newUserText = (TextView) findViewById(R.id.go_to_login);
            loginButton = (Button) findViewById(R.id.register_button);
            googleButton = (ImageButton) findViewById(R.id.google_button);
            spinner = (ProgressBar) findViewById(R.id.progressSpinner);
        }

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_button:
                register();
                break;
            case R.id.google_button:
                googleLogin();
                break;

            case R.id.go_to_login:
                goToLoginPage();
                break;
        }
    }

    private void goToLoginPage() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }

    private void goToMainPage() {
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();
    }

    private void googleLogin() {

    }

    private void register() {
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
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG);
                    goToMainPage();
                }
                else{
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG);
                }
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }
}