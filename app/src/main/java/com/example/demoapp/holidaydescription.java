package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class holidaydescription extends AppCompatActivity {
    int place_id;
    ImageView img;
    TextView description,price,days,placename;
    Button book;
    FloatingActionButton location;
    String place;
    DBHelper db=new DBHelper<>(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidaydescription);

        img=findViewById(R.id.photo);


        Intent i=getIntent();
        place=i.getStringExtra("place");
        String username=i.getStringExtra("username");

        Cursor destinationdata=db.selectedTourData(place);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        days=findViewById(R.id.days);
        placename=findViewById(R.id.dplace);
        book=findViewById(R.id.bookholiday);
        location=findViewById(R.id.location);
        while(destinationdata.moveToNext())
        {
            place_id=Integer.parseInt(destinationdata.getString(0));
         placename.setText(destinationdata.getString(1));

         byte[] imgfromdb=destinationdata.getBlob(5);
         Bitmap convertedimgfromdb=BitmapFactory.decodeByteArray(imgfromdb,0,imgfromdb.length);
         img.setImageBitmap(convertedimgfromdb);

         days.append(" "+destinationdata.getString(2));
         description.setText(destinationdata.getString(3));
         price.append(" "+destinationdata.getString(4));
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.insertHolidayData(username,place_id))
                {
                    Toast.makeText(getApplicationContext(),"Holiday Booked",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Holiday Already Booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                i.putExtra("place",place);
                startActivity(i);
            }
        });
    }
}