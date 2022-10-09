package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    ListView l;
    EditText searchbar;
    DBHelper db=new DBHelper<>(this);
    ArrayAdapter<String> arr;
    ArrayList<String> list=new ArrayList<>();
    String un;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        searchbar =findViewById(R.id.searchb);
        l=findViewById(R.id.listview);
        Intent i1=getIntent();
        un=i1.getStringExtra("username");
        //loading data from cursor
        Cursor c=db.selectTourData();

        while(c.moveToNext())
        {
            list.add(c.getString(1));
        }

        arr=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        l.setAdapter(arr);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                Intent intent=new Intent(getApplicationContext(),holidaydescription.class);
                intent.putExtra("place",selectedItem);
                intent.putExtra("username",un);
                startActivity(intent);
            }
        });

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (MainActivity3.this).arr.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.exit:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.profile:
                Intent i=new Intent(getApplicationContext(),profile.class);
                i.putExtra("username",un);
                startActivity(i);
                return true;
            case R.id.viewbookings:
                Intent i1=new Intent(getApplicationContext(),seebookings.class);
                i1.putExtra("username",un);
                startActivity(i1);
                return true;
            case R.id.give_suggestions:
                Intent i2=new Intent(getApplicationContext(),suggestions.class);
                startActivity(i2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }}