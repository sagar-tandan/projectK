package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectk.Adapters.Note_Adapter;
import com.example.projectk.model.Note_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminInterface extends AppCompatActivity {

    ImageView imageView,log_out;
    TextView room_number;
    EditText class_code;
    CardView save_class;

    LinearLayoutCompat routine,notes,notice,assessment,phone;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;





    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String CLASS_CODE = "Class_code1";

    public static final String EMAIL_HERE = "email here";

   // String Scode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        imageView = findViewById(R.id.pk);
        room_number = findViewById(R.id.room_number);
        routine = findViewById(R.id.routine);
        notes = findViewById(R.id.notes);
        notice = findViewById(R.id.notice);
        assessment = findViewById(R.id.assessment);
        phone = findViewById(R.id.phone);
        class_code = findViewById(R.id.class_code);
        save_class = findViewById(R.id.save_class);
        log_out = findViewById(R.id.log_out);



        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

       // Scode = sharedpreferences.getString(CLASS_CODE,null);


        class_code.setText(sharedpreferences.getString(CLASS_CODE,null));
        room_number.setText(sharedpreferences.getString(CLASS_CODE,null));




        //Taking class code from previous activity

        Intent intent = getIntent();
        String code = intent.getStringExtra("Email");

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(EMAIL_HERE,code);
        editor.apply();





        //Check Internet Connection

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){

        }else{
            Toast.makeText(AdminInterface.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }



        save_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (class_code.getText().toString().trim().isEmpty()){
                    Toast.makeText(AdminInterface.this, "Please enter the Class Code", Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(AdminInterface.this,""+sharedpreferences.getString(EMAIL_HERE,code), Toast.LENGTH_SHORT).show();
                }else{

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(CLASS_CODE,class_code.getText().toString().trim());
                    editor.apply();
                    Toast.makeText(AdminInterface.this, "Class Saved!!", Toast.LENGTH_SHORT).show();
                    room_number.setText(class_code.getText().toString().trim());


                }
            }
        });


        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                  //  Toast.makeText(AdminInterface.this, "working", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(AdminInterface.this,Admin_Note.class);

                   intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));
                   intent.putExtra("Mail",sharedpreferences.getString(EMAIL_HERE,null));

                   startActivity(intent);
                }else{
                    Toast.makeText(AdminInterface.this, "Please create class", Toast.LENGTH_SHORT).show();
                }
            }
        });

        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    //  Toast.makeText(AdminInterface.this, "working", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminInterface.this,Admin_Routine.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));
                    intent.putExtra("Mail",sharedpreferences.getString(EMAIL_HERE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(AdminInterface.this, "Please create class", Toast.LENGTH_SHORT).show();
                }

            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    //  Toast.makeText(AdminInterface.this, "working", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminInterface.this,Admin_phone.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));
                    intent.putExtra("Mail",sharedpreferences.getString(EMAIL_HERE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(AdminInterface.this, "Please create class", Toast.LENGTH_SHORT).show();
                }

            }
        });

        assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){

                    Intent intent = new Intent(AdminInterface.this,Admin_Assessment.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));
                    intent.putExtra("Mail",sharedpreferences.getString(EMAIL_HERE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(AdminInterface.this, "Please create class", Toast.LENGTH_SHORT).show();
                }

            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){

                    Intent intent = new Intent(AdminInterface.this,Admin_Notice.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));
                    intent.putExtra("Mail",sharedpreferences.getString(EMAIL_HERE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(AdminInterface.this, "Please create class", Toast.LENGTH_SHORT).show();
                }

            }
        });




        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AdminInterface.this)
                        .setTitle("ProjectK")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();

                                SharedPreferences preferences =getSharedPreferences("shared_prefs",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent1 = new Intent(AdminInterface.this,MainActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                                finish();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });



        Glide.with(AdminInterface.this).load(R.drawable.pkk).into(imageView);
    }
}