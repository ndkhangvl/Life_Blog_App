package com.example.bloglife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginUser extends AppCompatActivity {
    Button btLogin;
    TextView txtRegisterNow, txtEmail, txtPasswd, txtForgotPasswd;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1E232C"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        btLogin = findViewById(R.id.btLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPasswd);
        txtRegisterNow = findViewById(R.id.txtRegisterNow);
        txtForgotPasswd = findViewById(R.id.txtForgotPasswd);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){}
        else {
            Intent intent = new Intent(getApplicationContext(), ListBlog.class);
            startActivity(intent);
            finish();
        }

        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUser.this, RegisterUser.class);
                startActivity(intent);
                finish();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email,user_passwd;
                user_email = String.valueOf(txtEmail.getText());
                user_passwd = String.valueOf(txtPasswd.getText());
                if(TextUtils.isEmpty(user_email)) {
                    Toast.makeText(LoginUser.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(user_passwd)) {
                    Toast.makeText(LoginUser.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(user_email, user_passwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginUser.this, ListBlog.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(LoginUser.this, "Authenication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        txtForgotPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(txtEmail.getText())) {
                    Toast.makeText(LoginUser.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(String.valueOf(txtEmail.getText()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginUser.this, "Check notification from email for reset password", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginUser.this, "Reset password fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}