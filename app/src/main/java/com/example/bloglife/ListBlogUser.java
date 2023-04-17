package com.example.bloglife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListBlogUser extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    FloatingActionButton btAdd;

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    BlogAdapter myAdapter;
    ArrayList<InfoBlog> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_blog_user);

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

        mAuth = FirebaseAuth.getInstance();
        btAdd = findViewById(R.id.addBlog);

        //Bottom navigation
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.blog);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ListBlog.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.blog:
                        return true;
                    case R.id.user:
                        DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference("Users");
                        Query query = databaseReferences.orderByChild("uemail").equalTo(user.getEmail());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    Users users = dataSnapshot.getValue(Users.class);
                                    users.setUid(dataSnapshot.getKey());
                                    Intent intent = new Intent(getApplicationContext(),UserInformation.class);
                                    intent.putExtra("id", dataSnapshot.getKey());
                                    intent.putExtra("uemail", users.getUemail());
                                    intent.putExtra("uname", users.getUname());
                                    intent.putExtra("uaddress", users.getUaddress());
                                    startActivity(intent);
                                    overridePendingTransition(0,0);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //startActivity(new Intent(getApplicationContext(),UserInformation.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.rvBlog);
        //String myEmail = user.getEmail();
        //System.out.println("Print email" + myEmail);

        databaseReference = FirebaseDatabase.getInstance().getReference("InfoBlog");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new BlogAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        getdata();

        user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginUser.class);
            startActivity(intent);
            finish();
        } else {
            //txtUser.setText(user.getEmail());
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBlog.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                List<DataSnapshot> snapshotList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    snapshotList.add(dataSnapshot);
                }
                Collections.reverse(snapshotList);
                for (DataSnapshot dataSnapshot : snapshotList) {

                    InfoBlog infoBlog = dataSnapshot.getValue(InfoBlog.class);
                    infoBlog.setId(dataSnapshot.getKey());
                    //System.out.println("Get Email" + infoBlog.getIuser());
                    //System.out.println("Get user email " + user.getEmail());
                    if(infoBlog != null && infoBlog.getIuser().equals(user.getEmail()))
                        list.add(infoBlog);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(ListBlogUser.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}