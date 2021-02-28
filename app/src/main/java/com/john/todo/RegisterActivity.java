package com.john.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    Button newUserText;
    Button loginButton;
    ImageButton googleButton;
    ProgressBar spinner;

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;

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
            newUserText = (Button) findViewById(R.id.go_to_login);
            loginButton = (Button) findViewById(R.id.register_button);
            googleButton = (ImageButton) findViewById(R.id.google_button);
            spinner = (ProgressBar) findViewById(R.id.progressSpinner);

            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(RegisterActivity.this,gso);
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
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,100);
        spinner.setVisibility(View.VISIBLE);
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
                    Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();
                    goToMainPage();
                }
                else{
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {

            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(signInAccountTask.isSuccessful()) {

                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if(googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
                        auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    showToast("Login Successful");
                                    goToMainPage();
                                }
                                else{
                                    showToast(task.getException().getMessage());
                                }
                                spinner.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    else {
                        spinner.setVisibility(View.INVISIBLE);
                        showToast("Sign In Error");
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                    spinner.setVisibility(View.INVISIBLE);
                }
            }
            else {
                spinner.setVisibility(View.INVISIBLE);
                showToast("Sign In Error");
            }
        }
    }

    private void showToast(String toast) {
        Toast.makeText(RegisterActivity.this,toast,Toast.LENGTH_SHORT).show();
    }
}