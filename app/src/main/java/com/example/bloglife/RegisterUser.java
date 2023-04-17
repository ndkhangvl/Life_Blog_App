package com.example.bloglife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    Button btRegister;
    EditText uemail, upasswd, uname, uaddress;
    FirebaseAuth mAuth;
    TextView txtLoginNow;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

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

        btRegister = findViewById(R.id.btRegister);
        txtLoginNow = findViewById(R.id.txtLoginNow);
        uemail = findViewById(R.id.txtEmaill);
        upasswd = findViewById(R.id.txtPasswd);
        uname = findViewById(R.id.txtUname);
        uaddress = findViewById(R.id.txtAddress);
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterUser.this, LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email,user_passwd,user_uname,user_address;
                user_email = String.valueOf(uemail.getText());
                user_passwd = String.valueOf(upasswd.getText());
                user_uname = String.valueOf(uname.getText());
                user_address = String.valueOf(uaddress.getText());

                if(TextUtils.isEmpty(user_email)) {
                    Toast.makeText(RegisterUser.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(user_passwd)) {
                    Toast.makeText(RegisterUser.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(user_uname)) {
                    Toast.makeText(RegisterUser.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(user_address)) {
                    Toast.makeText(RegisterUser.this, "Please enter address", Toast.LENGTH_SHORT).show();
                    return;
                }

                Users users = new Users(user_email, user_uname, user_address);
                databaseReference.push().setValue(users);

                mAuth.createUserWithEmailAndPassword(user_email, user_passwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(RegisterUser.this, "Account has been created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterUser.this, "Account has been faild", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}