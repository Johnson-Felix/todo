package com.john.todo;

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

public class LoginActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    TextView newUserText;
    Button loginButton;
    ImageButton googleButton;
    ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText)findViewById(R.id.email_editText);
        passwordText = (EditText)findViewById(R.id.password_editText);
        newUserText = (TextView) findViewById(R.id.newUser_TextView);
        loginButton = (Button) findViewById(R.id.login_button);
        googleButton = (ImageButton) findViewById(R.id.google_button);
        spinner = (ProgressBar)findViewById(R.id.progressSpinner);
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
    }

    private void goToRegisterPage() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    private void googleLogin() {

    }
}