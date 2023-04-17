package com.example.bloglife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserInformation extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth mAuth;
    EditText txtUEmail, txtUName, txtUAddress;
    Button btLogout, btChange;
    String UName, UAddress, UEmail, UID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        btLogout = findViewById(R.id.btLogout);
        btChange = findViewById(R.id.btChange);

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

        //Bottom navigation
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.user);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.blog:
                        startActivity(new Intent(getApplicationContext(),ListBlogUser.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ListBlog.class));
                        overridePendingTransition(0,0);
                    case R.id.user:
                        return true;
                }
                return false;
            }
        });


        txtUEmail = findViewById(R.id.txtUEmail);
        txtUName = findViewById(R.id.txtUname);
        txtUAddress = findViewById(R.id.txtUAddress);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginUser.class);
            startActivity(intent);
            finish();
        } else {
            txtUEmail.setText(user.getEmail());
        }

        UID = getIntent().getStringExtra("id");
        UEmail = getIntent().getStringExtra("uemail");
        UName = getIntent().getStringExtra("uname");
        UAddress = getIntent().getStringExtra("uaddress");

        txtUName.setText(UName);
        txtUAddress.setText(UAddress);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname,uaddress;
                if(TextUtils.isEmpty(txtUName.getText())){
                    Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txtUAddress.getText())){
                    Toast.makeText(getApplicationContext(), "Enter address", Toast.LENGTH_SHORT).show();
                    return;
                }
                uname = txtUName.getText().toString();
                uaddress = txtUAddress.getText().toString();

                DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference().child("InfoBlog");
                Query query = databaseReferences.orderByChild("iuser").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String key = dataSnapshot.getKey();
                            databaseReferences.child(key).child("uname").setValue(txtUName.getText().toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Users user = new Users(FirebaseAuth.getInstance().getCurrentUser().getEmail(),uname, uaddress);
                System.out.println("In cai UID: " + UID);
                databaseReference.child(UID).setValue(user);
                Toast.makeText(getApplicationContext(), "User information updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}