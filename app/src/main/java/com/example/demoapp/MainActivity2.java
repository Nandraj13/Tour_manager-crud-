package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    DBHelper db;
    EditText username,password;
    Button login;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.lbutton);
        db = new DBHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un=username.getText().toString();
                String ps=password.getText().toString();
                if(un.equals("")||ps.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"None of the field should be empty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Boolean result=db.select(un,ps);
                    if(result)
                    {
                        Toast.makeText(getApplicationContext(),"Authenticated",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),MainActivity3.class);
                        i.putExtra("username",un);
                        startActivity(i);
                    }
                    else
                    {

                        if(un.equals("admin1")&&ps.equals("123"))
                        {
                            Intent i=new Intent(getApplicationContext(),Admin.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Not Authenticated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}