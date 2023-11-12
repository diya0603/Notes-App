package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText mloginemail, mloginpassword ;
    private RelativeLayout mlogin, mgotosignup ;
    TextView mgotoforgotpassword ;
    ProgressBar mprogressbarofmainactivity;
    FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mloginemail = findViewById(R.id.loginemail) ;
        mloginpassword = findViewById(R.id.loginpassword) ;
        mlogin = findViewById(R.id.login) ;
        mgotosignup = findViewById(R.id.gotosignup) ;
        mgotoforgotpassword = findViewById(R.id.gotoforgotpassword) ;

        firebaseAuth = FirebaseAuth.getInstance() ;
        mprogressbarofmainactivity=findViewById(R.id.progressbarofmainactivity);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser() ;

        if(firebaseUser != null){
            // if user is already logged-in
            finish() ;
            startActivity(new Intent(MainActivity.this, notesactivity.class));
        }

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail =  mloginemail.getText().toString().trim() ;
                String password = mloginpassword.getText().toString().trim() ;
                
                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }

                else{
                    // LOGIN USER

                        mprogressbarofmainactivity.setVisibility(view.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkMailVerification() ;
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "User not registered, Sign-up to create an account", Toast.LENGTH_SHORT).show();
                                mprogressbarofmainactivity.setVisibility(view.INVISIBLE);
                            }
                        }
                    }) ;

                }
            }
        });


        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });

        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, forgotpassword.class));
            }
        });
    }

    private void checkMailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser() ;

        if(firebaseUser.isEmailVerified() == true){
            Toast.makeText(getApplicationContext(), "Login Succesful! :)", Toast.LENGTH_SHORT).show();
            finish() ;

            startActivity(new Intent(MainActivity.this, notesactivity.class));

        }
        else{
            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Make sure you have entered a valid Email ID", Toast.LENGTH_SHORT).show();

        }
    }
}