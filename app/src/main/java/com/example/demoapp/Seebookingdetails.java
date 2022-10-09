package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.Locale;

public class Seebookingdetails extends AppCompatActivity {
    int place_id;
    ImageView img;
    TextView description,price,days,placename;
    Button cancel;
    DBHelper db=new DBHelper<>(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seebookingdetails);

        img=findViewById(R.id.bookingphoto);


        Intent i=getIntent();
        String place=i.getStringExtra("place");
        String username=i.getStringExtra("username");
        Cursor destinationdata=db.selectedTourData(place);

        description=findViewById(R.id.bookingdescription);
        price=findViewById(R.id.bookingprice);
        days=findViewById(R.id.bookingdays);
        placename=findViewById(R.id.bookingplace);
        cancel=findViewById(R.id.cancelholiday);

        while(destinationdata.moveToNext())
        {
            place_id=Integer.parseInt(destinationdata.getString(0));
            placename.setText(destinationdata.getString(1));
            byte[] imgfromdb=destinationdata.getBlob(5);
            Bitmap convertedimgfromdb= BitmapFactory.decodeByteArray(imgfromdb,0,imgfromdb.length);
            img.setImageBitmap(convertedimgfromdb);
            days.append(" "+destinationdata.getString(2));
            description.setText(destinationdata.getString(3));
            price.append(" "+destinationdata.getString(4));
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.cancelBooking(username,place_id))
                {
                    Toast.makeText(getApplicationContext(),"Holiday Cancelled",Toast.LENGTH_SHORT).show();
                    Intent refresh=new Intent(getApplicationContext(),seebookings.class);
                    startActivity(refresh);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error While Cancelling",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}