package com.example.bloglife;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateBlog extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button btSave, btCancel;
    EditText txtTitle, txtContent;
    String title, content, umail, uid, uname;
    DatabaseReference databaseReference;
    InfoBlog infoBlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_blog);

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

        btSave = findViewById(R.id.btAddBlog);
        btCancel = findViewById(R.id.btCancel);
        txtTitle = findViewById(R.id.txtTittle);
        txtContent = findViewById(R.id.txtContent);

        //Get value from Adapter

        uname = getIntent().getStringExtra("uname");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        umail = getIntent().getStringExtra("user");
        uid = getIntent().getStringExtra("id");

        txtTitle.setText(title);
        txtContent.setText(content);

        //Check login
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

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
                updateBlog();
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

    private void updateBlog() {
        String title_blog,content_blog,uname_blog;
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String currentDateString = sdf.format(currentDate);
        title_blog = txtTitle.getText().toString();
        content_blog = txtContent.getText().toString();
        InfoBlog info = new InfoBlog(uname, title_blog,content_blog,currentDateString, user.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("InfoBlog").child(uid);
        databaseReference.setValue(info);
        Toast.makeText(getApplicationContext(), "Blog has been update", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(getApplicationContext(), ListBlog.class);
        startActivity(intent);
    }
}