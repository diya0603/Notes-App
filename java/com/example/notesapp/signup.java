package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    private EditText msignupemail, msignuppassword ;
    private RelativeLayout msignup ;
    private TextView mgotologin ;

    private FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide() ;

        msignupemail = (EditText) findViewById(R.id.signupemail) ;
        msignuppassword = (EditText) findViewById(R.id.signuppassword) ;
        msignup = findViewById(R.id.signup) ;
        mgotologin = findViewById(R.id.gotologin) ;

        //instance
        firebaseAuth = FirebaseAuth.getInstance() ;

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = msignupemail.getText().toString().trim() ;
                String password = msignuppassword.getText().toString().trim() ;

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show() ;

                } else if (password.length() < 7) {
                    Toast.makeText(getApplicationContext(), "Password must contain atleast 7 characters", Toast.LENGTH_SHORT).show() ;

                }

                else{
                    // REGISTER USER TO FIREBASE
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Sign-up Successful :)", Toast.LENGTH_SHORT).show();
                                sendEmailVerification() ;
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Sign-up Failed :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) ;
                }
            }
        });

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, MainActivity.class) ;
                startActivity(intent) ;
            }
        });
    }

    // send email verification
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser() ;
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "verification email sent, verify and login :)", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish() ;

                    startActivity(new Intent(signup.this, MainActivity.class));
                }
            }) ;
        }
        else{
            Toast.makeText(getApplicationContext(), "Unable to send verification email :(", Toast.LENGTH_SHORT).show();
        }
    }
}