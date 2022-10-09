package com.example.demoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class admin_addholiday extends Fragment {
    View view;
    EditText placename,placeprice,placedescription,placeday;
    Button addholiday;
    ImageView image;
    DBHelper db;
    Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_admin_addholiday, container, false);
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db=new DBHelper<>(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placename=getView().findViewById(R.id.addplacename);
        placeprice=getView().findViewById(R.id.addplaceprice);
        placeday=getView().findViewById(R.id.addplacedays);
        placedescription=getView().findViewById(R.id.addplacedescription);

        addholiday=getView().findViewById(R.id.addholidaybutton);
        image=getView().findViewById(R.id.addplaceimage);
        addholiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String place=placename.getText().toString();
                String price=placeprice.getText().toString();
                String day=placeday.getText().toString();
                String description=placedescription.getText().toString();
                if(place.equals("")||day.equals("")||price.equals("")||description.equals("")||image.getDrawable() == null)
                {
                    Toast.makeText(getContext(),"None of the field should be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                    Bitmap bt = drawable.getBitmap();
                    ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
                    bt.compress(Bitmap.CompressFormat.PNG,100,bytearray);
                    byte[] img= bytearray.toByteArray();
                Boolean res=db.insertIntoDestinations(place,day,price,description,img);
                if(res)
                {
                    Toast.makeText(getContext(),"Holiday Added",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Holiday Already Added",Toast.LENGTH_SHORT).show();
                }
            }}
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 1);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            uri=data.getData();
            image.setImageURI(uri);
        }
    }

}