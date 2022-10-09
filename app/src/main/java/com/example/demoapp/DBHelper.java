package com.example.demoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

;

public class DBHelper<pubic> extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "demodb.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table udetails(username text primary key, password text,name text,contact text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists udetails");
        db.execSQL("drop table if exists destinations");
    }

    public boolean insertdata(String username,String password,String name,String contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        cv.put("name",name);
        cv.put("contact",contact);
        long result=db.insert("udetails",null,cv);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean select(String username,String password)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from udetails where username=? and password=?",new String[]{username,password});
        if(c.getCount()==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Cursor getProfileData(String username)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from udetails where username=?",new String[]{username});
        return c;
    }
    public Cursor selectTourData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select * from destinations",null);
        return c;
    }
    public Cursor selectedTourData(String place){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select * from destinations where place=?",new String[]{place});
        return c;
    }
    public boolean insertHolidayData(String username,Integer destid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Cursor c=db.rawQuery("select * from bookings where username=? and destinations_id=?",new String[]{username,String.valueOf(destid)});
        if(c.getCount()>0)
        {
            return false;
        }
        else {
            cv.put("username", username);
            cv.put("destinations_id", destid);
            long result = db.insert("bookings", null, cv);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }
    public Cursor selectBookings(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select * from destinations where id in (select destinations_id from bookings where username=?)",new String[]{username});
        return c;
    }
    public boolean cancelBooking(String username, Integer place_id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("bookings","destinations_id=? and username=?",new String[]{String.valueOf(place_id),username}) >0;
    }
    public boolean insertIntoDestinations(String place,String days,String price,String description,byte[] img)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("place",place);
        cv.put("price",price);
        cv.put("description",description);
        cv.put("days",days);
        cv.put("image",img);
        Cursor c=db.rawQuery("select * from destinations where place=?",new String[]{place});
        if(c.getCount()>0)
        {
            return false;
        }
        else
        {
            long result=db.insert("destinations",null,cv);
            if(result==-1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
    public boolean deleteHolidayData(Integer place_id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("destinations","id=?",new String[]{String.valueOf(place_id)}) >0;
    }
}
