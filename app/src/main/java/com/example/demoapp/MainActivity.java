package com.example.demoapp;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    Button register;
    EditText username,password,name,contact;
    TextView login,rg;
    DBHelper db=new DBHelper(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg=findViewById(R.id.register);
        register=findViewById(R.id.rbutton);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        name=findViewById(R.id.name);
        contact=findViewById(R.id.contact);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nextactivity();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un=username.getText().toString();
                String ps=password.getText().toString();
                String n=name.getText().toString();
                String cont=contact.getText().toString();
                if(un.equals("")||ps.equals("")||n.equals("")||cont.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"None of the field should be empty",Toast.LENGTH_LONG).show();
                }
                else if(cont.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Phone number should of exact 10 digits",Toast.LENGTH_LONG).show();
                }
                else
                {
                Boolean result=db.insertdata(un,ps,n,cont);
                if(result)
                {
                    Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Registered",Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }

    public void nextactivity() {
        Intent i=new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(i);
    }
}