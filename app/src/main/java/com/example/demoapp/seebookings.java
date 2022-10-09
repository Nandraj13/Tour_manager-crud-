package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class seebookings extends AppCompatActivity {
    ListView bookings;
    DBHelper db=new DBHelper<>(this);
    ArrayAdapter<String> arr;
    ArrayList<String> list=new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seebookings);
        bookings=findViewById(R.id.bookinglist);

        Intent getun=getIntent();
        String un=getun.getStringExtra("username");
        Cursor c=db.selectBookings(un);
        while(c.moveToNext())
        {
            list.add(c.getString(1));
        }

        arr=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        bookings.setAdapter(arr);
        bookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                Intent intent=new Intent(getApplicationContext(),Seebookingdetails.class);
                intent.putExtra("place",selectedItem);
                intent.putExtra("username",un);
                startActivity(intent);
            }
        });
    }
}