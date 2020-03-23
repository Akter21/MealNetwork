package com.example.mealnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText SignUpEmailEditText,SignUpPasswordEditText;
    private Button SignUpButton;
    private TextView SignInTextView;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        SignUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditTextId);
        SignUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditTextId);
        SignUpButton = (Button) findViewById(R.id.signUpButtonId);
        SignInTextView = findViewById(R.id.signInTextViewId);
        progressbar = findViewById(R.id.progressbarId);

        SignInTextView.setOnClickListener(this);
        SignUpButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButtonId:
                userRegister();
                break;
            case R.id.signInTextViewId:

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userRegister() {
        String email = SignUpEmailEditText.getText().toString().trim();
        String password = SignUpPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {

            SignUpEmailEditText.setError("Enter an email Address");
            SignUpEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            SignUpEmailEditText.setError("Enter a valid email Address");
            SignUpEmailEditText.requestFocus();
            return;

        }
        if(email.isEmpty())
        {
            SignUpPasswordEditText.setError("Enter a password");
            SignUpPasswordEditText.requestFocus();
            return;
        }
        if(password.length()<5)
        {
            SignUpPasswordEditText.setError("Minimum Length of password should 5");
            SignUpPasswordEditText.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(getApplicationContext(), "Register is Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            }

        });


    }
}
