package com.example.projectk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdminInterface extends AppCompatActivity {
    Intent i=getIntent();
    String value=i.getStringExtra("value");
    private TextView txt_value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

         txt_value=findViewById(R.id.txt_value);
         txt_value.setText(value);

    }
}