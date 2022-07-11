package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectk.model.Phone_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;

public class Admin_phone extends AppCompatActivity {

    private ImageView back;
    private EditText name_teacher,phone_no;
    private CardView upload_phone;
    private TextView uploaded_files;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_phone);

        back = findViewById(R.id.back);
        name_teacher = findViewById(R.id.name_teacher);
        phone_no = findViewById(R.id.phone_no);
        upload_phone = findViewById(R.id.upload_phone);
        uploaded_files = findViewById(R.id.uploaded_files);

        databaseReference = FirebaseDatabase.getInstance().getReference("ProjectK").child("Phone");

        //Taking class code from previous activity

        Intent intent = getIntent();
        String code = intent.getStringExtra("id");
        String mail = intent.getStringExtra("Mail");



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        upload_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //First Check Internet Connection

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {


                    if (name_teacher.getText().toString().isEmpty()) {
                        name_teacher.setError("Enter name of Teacher");
                    } else if (phone_no.getText().toString().trim().isEmpty()) {
                        phone_no.setError("Enter phone no.");
                    } else {

                        String key = databaseReference.push().getKey();

                        Phone_Model model = new Phone_Model(phone_no.getText().toString().trim(),
                                name_teacher.getText().toString().trim(), code,mail,code+mail.toLowerCase(),key);
                        assert key != null;
                        databaseReference.child(key).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Admin_phone.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                        name_teacher.setText("");
                                        phone_no.setText("");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Admin_phone.this, "Something went Wrong!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                }else{
                    Toast.makeText(Admin_phone.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //TO show uploaded files

        uploaded_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Admin_phone.this,Admin_Uploaded_phone.class);
                intent1.putExtra("id",code);
                intent1.putExtra("Mail",mail);
                startActivity(intent1);
            }
        });
    }
}