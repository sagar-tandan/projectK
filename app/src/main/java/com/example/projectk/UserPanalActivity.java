package com.example.projectk;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class UserPanalActivity extends AppCompatActivity {


    ImageView imageView,log_out;
    TextView room_number;

    LinearLayoutCompat routine,notes,notice,assessment,phone;



    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String CLASS_CODE = "Class_code1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panal);

        imageView = findViewById(R.id.pk);
        room_number = findViewById(R.id.room_number);
        routine = findViewById(R.id.routine);
        notes = findViewById(R.id.notes);
        notice = findViewById(R.id.notice);
        assessment = findViewById(R.id.assessment);
        phone = findViewById(R.id.phone);
        log_out = findViewById(R.id.log_out);



        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // Scode = sharedpreferences.getString(CLASS_CODE,null);


       // class_code.setText(sharedpreferences.getString(CLASS_CODE,null));
        room_number.setText(sharedpreferences.getString(CLASS_CODE,null));




        Intent intent = getIntent();
        String Code_of_class = intent.getStringExtra("code_of_class");

        SharedPreferences.Editor editor = sharedpreferences.edit();
        // editor.putString(CLASS_CODE,class_code.getText().toString().trim());
        editor.putString(CLASS_CODE,Code_of_class);
        editor.apply();
        room_number.setText(Code_of_class);



        //Check Internet Connection

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){

        }else{
            Toast.makeText(UserPanalActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }



        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    Intent intent = new Intent(UserPanalActivity.this,User_Note.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(UserPanalActivity.this, "Please enter the Class Code", Toast.LENGTH_SHORT).show();
                }
            }
        });


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    Intent intent = new Intent(UserPanalActivity.this,User_Phone.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(UserPanalActivity.this, "Please enter the Class Code", Toast.LENGTH_SHORT).show();
                }
            }
        });


        assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    Intent intent = new Intent(UserPanalActivity.this,User_Assessment.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(UserPanalActivity.this, "Please enter the Class Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    Intent intent = new Intent(UserPanalActivity.this,User_Notice.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(UserPanalActivity.this, "Please enter the Class Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpreferences.getString(CLASS_CODE,null) != null){
                    Intent intent = new Intent(UserPanalActivity.this,User_Routine.class);

                    intent.putExtra("id",sharedpreferences.getString(CLASS_CODE,null));

                    startActivity(intent);
                }else{
                    Toast.makeText(UserPanalActivity.this, "Please enter the Class Code", Toast.LENGTH_SHORT).show();
                }
            }
        });


        log_out.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
//                new AlertDialog.Builder(UserPanalActivity.this)
//                        .setTitle("ProjectK")
//                        .setMessage("Are you sure you want to logout?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                FirebaseAuth.getInstance().signOut();
//
//                                SharedPreferences preferences =getSharedPreferences("shared_prefs",Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.clear();
//                                editor.apply();
//                                Intent intent1 = new Intent(UserPanalActivity.this,MainActivity.class);
//                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent1);
//                                finish();
//
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();


                PopupMenu popupMenu = new PopupMenu(UserPanalActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.change){

//                            SharedPreferences preferences =getSharedPreferences("shared_prefs",Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.remove("Class_code1").apply();
//
                            Intent intent1 = new Intent(UserPanalActivity.this,Change_Class_Code.class);
                            startActivity(intent1);
                            finish();
                        }
                        if (menuItem.getItemId() == R.id.LogOut){


                        new AlertDialog.Builder(UserPanalActivity.this)
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
                                    Intent intent1 = new Intent(UserPanalActivity.this,MainActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent1);
                                    finish();

                                }
                            })
                            .setNegativeButton("No", null)
                            .show();


                        }
                        return false;
                    }
                });
                popupMenu.show();

            }


        });



        Glide.with(UserPanalActivity.this).load(R.drawable.pkk).into(imageView);

    }


}