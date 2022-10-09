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

public class admin_holiday_description extends AppCompatActivity {

    int place_id;
    ImageView img;
    TextView description,price,days,placename;
    Button Delete;
    DBHelper db=new DBHelper<>(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_holiday_description);

        img=findViewById(R.id.admin_holiday_photo);


        Intent i=getIntent();
        String place=i.getStringExtra("place");

        Cursor destinationdata=db.selectedTourData(place);
        description=findViewById(R.id.admin_holiday_description);
        price=findViewById(R.id.admin_holiday_price);
        days=findViewById(R.id.admin_holiday_days);
        placename=findViewById(R.id.admin_holiday_place);
        Delete=findViewById(R.id.admin_holiday_deleteholiday);
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

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.deleteHolidayData(place_id))
                {
                    Toast.makeText(getApplicationContext(),"Holiday Deleted",Toast.LENGTH_SHORT).show();
                    Intent refresh=new Intent(getApplicationContext(),Admin.class);
                    startActivity(refresh);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error while deleting holiday",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}