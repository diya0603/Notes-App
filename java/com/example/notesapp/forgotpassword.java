package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpassword extends AppCompatActivity {

    private EditText mforgotpassword ;
    private Button mpasswordrecoverbtn ;
    private TextView mgobacktologin ;

    private FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        getSupportActionBar().hide() ;

        mforgotpassword = (EditText) findViewById(R.id.forgotpassword) ;
        mpasswordrecoverbtn = (Button) findViewById(R.id.passwordrecoverbtn) ;
        mgobacktologin = (TextView) findViewById(R.id.gobacktologin) ;

        firebaseAuth = FirebaseAuth.getInstance() ;

        mpasswordrecoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgotpassword.getText().toString().trim() ;
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your mail", Toast.LENGTH_SHORT).show() ;
                }
                else{
                    // send PASSWORD RECOVERY EMAIL to user's given mail-ID
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Recovery Email sent", Toast.LENGTH_SHORT).show();
                                finish() ;

                                startActivity(new Intent(forgotpassword.this, MainActivity.class));
                            }

                            else{
                                Toast.makeText(getApplicationContext(), "Failed to send Recovery Email", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotpassword.this, MainActivity.class) ;
                startActivity(intent);
            }
        });
    }


}