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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBlog extends AppCompatActivity {
    Button btSave, btCancel;
    EditText txtTitle, txtContent;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    protected String uname_blog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);
        btSave = findViewById(R.id.btAddBlog);
        btCancel = findViewById(R.id.btCancel);
        txtTitle = findViewById(R.id.txtTittle);
        txtContent = findViewById(R.id.txtContent);

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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("InfoBlog");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference("Users");
        Query query = databaseReferences.orderByChild("uemail").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    uname_blog = dataSnapshot.child("uname").getValue(String.class);
                    System.out.println("Ten cua username log: " + uname_blog);
                    //saveBlog(uname_blog);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(txtTitle.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter title for blog", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txtContent.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter content for blog", Toast.LENGTH_SHORT).show();
                    return;
                }
                //saveBlog(uname_blog);

                //Test
                String title_blog,content_blog;
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                String currentDateString = sdf.format(currentDate);
                title_blog = txtTitle.getText().toString();
                content_blog = txtContent.getText().toString();
                System.out.println("Title is: " + title_blog + "Content is: " + content_blog);
                System.out.println("Ten cua username " + uname_blog);
                InfoBlog info = new InfoBlog(uname_blog, title_blog,content_blog,currentDateString, user.getEmail());
                databaseReference.push().setValue(info);
                Toast.makeText(getApplicationContext(), "Blog has been add", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), ListBlog.class);
                startActivity(intent);

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListBlog.class);
                startActivity(intent);
            }
        });
    }

//    private void saveBlog(String uname_blog) {
//        String title_blog,content_blog;
//        Calendar calendar = Calendar.getInstance();
//        Date currentDate = calendar.getTime();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
//        String currentDateString = sdf.format(currentDate);
//        title_blog = txtTitle.getText().toString();
//        content_blog = txtContent.getText().toString();
//        System.out.println("Ten cua username " + uname_blog);
//        InfoBlog info = new InfoBlog(uname_blog, title_blog,content_blog,currentDateString, user.getEmail());
//        databaseReference.push().setValue(info);
//        Toast.makeText(getApplicationContext(), "Blog has been add", Toast.LENGTH_SHORT).show();
//        finish();
//        Intent intent = new Intent(getApplicationContext(), ListBlog.class);
//        startActivity(intent);
//    }
}