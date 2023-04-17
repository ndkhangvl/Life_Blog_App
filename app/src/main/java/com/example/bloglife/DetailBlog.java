package com.example.bloglife;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class DetailBlog extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String title, content, umail, uid;
    TextView txtTitle, txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_blog);

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

        txtTitle = findViewById(R.id.txtDetailTitle);
        txtContent = findViewById(R.id.txtDetailContent);

        //Get value from Adapter
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        umail = getIntent().getStringExtra("user");
        uid = getIntent().getStringExtra("id");

        txtTitle.setText(title);
        txtContent.setText(content);
    }
}