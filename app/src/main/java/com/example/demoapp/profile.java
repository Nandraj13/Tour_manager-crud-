package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class profile extends AppCompatActivity {
    TextView username,name,contact;
    String usern,n,cont;
    Button cpass;
    DBHelper db=new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();
        String un=i.getStringExtra("username");
        Cursor userdata=db.getProfileData(un);
        cpass=findViewById(R.id.changepassword);
        while(userdata.moveToNext())
        {
            usern=userdata.getString(0);
            n=userdata.getString(2);
            cont=userdata.getString(3);

        }
        username=findViewById(R.id.username1);
        username.setText(usern);
        name=findViewById(R.id.name1);
        name.setText(n);
        contact=findViewById(R.id.contact1);
        contact.setText(cont);
        cpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Sorry feature not available",Toast.LENGTH_SHORT).show();
            }
        });
    }

}