package com.example.projectk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Change_Class_Code extends AppCompatActivity {


    CardView enter;
    EditText req_class_code;
    ImageView back;

    String enter_class,from_UserPanel;

    SharedPreferences sharedPreferences;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String CLASS_CODE = "codeOf_class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_class_code);

        enter = findViewById(R.id.enter);
        req_class_code = findViewById(R.id.code);
        back = findViewById(R.id.back);


        // getting the data which is stored in shared preferences.
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        enter_class = sharedPreferences.getString(CLASS_CODE, null);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (req_class_code.getText().toString().trim().isEmpty()) {
                    req_class_code.setError("Field cannot be empty!");
                } else {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(CLASS_CODE, req_class_code.getText().toString().trim());
                    editor.apply();

                    Intent intent = new Intent(Change_Class_Code.this, UserPanalActivity.class);
                    intent.putExtra("code_of_class", sharedPreferences.getString(CLASS_CODE, null));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}