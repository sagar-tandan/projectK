package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_create_class extends AppCompatActivity {

    private EditText class_code;
    private CardView create_class;
    private ImageView back;

    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;


    // variable for Text view.
    private TextView retrieveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_class);

        class_code = findViewById(R.id.class_code);
        create_class = findViewById(R.id.create_class);
        back = findViewById(R.id.back);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        create_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String class_id=class_code.getText().toString().trim();
                if(!class_id.isEmpty()){
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference(class_id);
                    retrieveData = findViewById(R.id.db_data);

                    // calling method
                    // for getting data.
                    getdata();


                }
                else{
                    Toast.makeText(Admin_create_class.this, "Class Code cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //check if class exists
    private void enter_class(String value){
        Intent i=new Intent(Admin_create_class.this,AdminInterface.class);
        i.putExtra("name",value);
        startActivity(i);
    };


    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                retrieveData.setText(value);
                enter_class(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(Admin_create_class.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}