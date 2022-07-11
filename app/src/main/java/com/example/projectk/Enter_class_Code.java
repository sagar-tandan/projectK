package com.example.projectk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Enter_class_Code extends AppCompatActivity {

    CardView enter;
    EditText req_class_code;

    String enter_class;

    SharedPreferences sharedPreferences;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String CLASS_CODE = "code_of_class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_class_code);

        enter = findViewById(R.id.enter);
        req_class_code = findViewById(R.id.code);


        // getting the data which is stored in shared preferences.
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        enter_class = sharedPreferences.getString(CLASS_CODE, null);


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (req_class_code.getText().toString().trim().isEmpty()) {
                    req_class_code.setError("Field cannot be empty!");
                } else {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(CLASS_CODE, req_class_code.getText().toString().trim());
                    editor.apply();

                    Intent intent = new Intent(Enter_class_Code.this, UserPanalActivity.class);
                    intent.putExtra("code_of_class", sharedPreferences.getString(CLASS_CODE, null));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (enter_class != null) {
            Intent i = new Intent(Enter_class_Code.this, UserPanalActivity.class);
            i.putExtra("code_of_class",sharedPreferences.getString(CLASS_CODE, null));
            // Toast.makeText(this, enter, Toast.LENGTH_SHORT).show();
            startActivity(i);
            finish();
        }
    }
}