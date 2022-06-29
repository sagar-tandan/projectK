package com.example.projectk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UserPanalActivity extends AppCompatActivity {


    private EditText class_code;
    private CardView enter_class;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panal);

        class_code = findViewById(R.id.class_code);
        enter_class = findViewById(R.id.enter_class);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });






        enter_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if that Class exists in database
            }
        });
    }
}