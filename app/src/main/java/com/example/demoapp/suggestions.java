package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class suggestions extends AppCompatActivity {
    Button submit;
    EditText suggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        submit=findViewById(R.id.submit_suggestion);
        suggestion=findViewById(R.id.suggestion_box);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrivately();
            }
        });
    }
    public void savePrivately() {
        String editTextData = suggestion.getText().toString()+",";

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("MM_suggestions");

        // Creating file with name gfg.txt
        File file = new File(folder, "mm_suggestions.txt");
        writeTextData(file, editTextData);
        suggestion.setText("");
    }
    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Suggestion sent", Toast.LENGTH_SHORT).show();
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