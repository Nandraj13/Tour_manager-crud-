package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class View_suggestions extends AppCompatActivity {
    ListView l;
    String[] list;
    ArrayAdapter<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_suggestions);
        l=findViewById(R.id.view_suggestions_list);
        showPrivateData();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.adminmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.exitadmin:
                finish();
                System.exit(0);
                return true;
            case R.id.viewsuggestion:
                Intent i=new Intent(getApplicationContext(),View_suggestions.class);
                startActivity(i);
                return true;
            case R.id.clearsuggestion:
                savePrivately();
                Intent i1=new Intent(getApplicationContext(),Admin.class);
                startActivity(i1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showPrivateData() {
        File folder = getExternalFilesDir("MM_suggestions");
        File file = new File(folder, "mm_suggestions.txt");
        String data = getdata(file);
        if (data != null) {
            list=data.split(",");
            arr=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
            l.setAdapter(arr);
        }
    }
    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public void savePrivately() {
        String editTextData = "";

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("MM_suggestions");

        // Creating file with name gfg.txt
        File file = new File(folder, "mm_suggestions.txt");
        writeTextData(file, editTextData);
    }
    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
