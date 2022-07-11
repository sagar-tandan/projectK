package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectk.model.Routine_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;

public class Admin_Routine extends AppCompatActivity {


    private TextView uploaded_files;
    private EditText notice_detail;
    private CardView upload_notice;
    private ImageView back;

    DatabaseReference databaseReference;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_routine);

        upload_notice = findViewById(R.id.upload);
        uploaded_files = findViewById(R.id.uploaded_files);
        notice_detail = findViewById(R.id.notice_detail);
        back = findViewById(R.id.back);

        LocalDate today = LocalDate.now(ZoneId.of("America/Hermosillo"));


        databaseReference = FirebaseDatabase.getInstance().getReference("ProjectK").child("Routine");

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


        upload_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Date patta lagauna
                DateTimeFormatter userFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);


                //Time ko lagi
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

                //First Check Internet Connection

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {


                    if (notice_detail.getText().toString().isEmpty()) {
                        notice_detail.setError("Enter name of Teacher");
                    } else {

                        ProgressDialog progressDialog = new ProgressDialog(Admin_Routine.this);
                        progressDialog.setTitle("Routine");
                        progressDialog.setMessage("Updating...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        String key = databaseReference.push().getKey();

                        Routine_Model model = new Routine_Model(notice_detail.getText().toString().trim(),key,code,mail,
                                code+mail.toLowerCase(),sdf.format(c.getTime()), today.format(userFormatter),"0.0");
                        assert key != null;
                        databaseReference.child(key).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Admin_Routine.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                        notice_detail.setText("");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Admin_Routine.this, "Something went Wrong!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                }else{
                    Toast.makeText(Admin_Routine.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        uploaded_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Admin_Routine.this, Admin_Uploaded_Routine.class);
                intent1.putExtra("id",code);
                intent1.putExtra("Mail",mail);
                startActivity(intent1);
            }
        });


    }
}