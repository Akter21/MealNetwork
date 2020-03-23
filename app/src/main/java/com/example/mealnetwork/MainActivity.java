package com.example.mealnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.util.Printer;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText SignInEmailEditText,SignInPasswordEditText;
    private Button SignInButton;
    private TextView SignUpTextView;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        SignInEmailEditText = (EditText) findViewById(R.id.signInEmailEditTextId);
        SignInPasswordEditText = (EditText) findViewById(R.id.signInPasswordEditTextId);
        SignInButton = (Button) findViewById(R.id.signInButtonId);
        SignUpTextView = findViewById(R.id.signUpTextViewId);
        progressbar = findViewById(R.id.progressbarId);
        SignUpTextView.setOnClickListener(this);
        SignInButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButtonId:
                userLogin();
                break;
            case R.id.signUpTextViewId:

                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userLogin() {
        String email = SignInEmailEditText.getText().toString().trim();
        String password = SignInPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {

            SignInEmailEditText.setError("Enter an email Address");
            SignInEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            SignInEmailEditText.setError("Enter a valid email Address");
            SignInEmailEditText.requestFocus();
            return;

        }
        if(email.isEmpty())
        {
            SignInPasswordEditText.setError("Enter a password");
            SignInPasswordEditText.requestFocus();
            return;
        }
        if(password.length()<5)
        {
            SignInPasswordEditText.setError("Minimum Length of password should 5");
            SignInPasswordEditText.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
               if(task.isSuccessful())
               {
                Intent intent = new Intent (getApplicationContext(),HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
               }else{
                   Toast.makeText(getApplicationContext(), "Login Unsuccesfull", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
