package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {

    private ImageView back;
    private EditText rec_email;
    private CardView recover_btn;


    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        back = findViewById(R.id.back);
        rec_email = findViewById(R.id.rec_email);
        recover_btn = findViewById(R.id.recover_btn);

        firebaseAuth = FirebaseAuth.getInstance();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recover_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check Internet Connection

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {

                    String email = rec_email.getText().toString().trim();

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        rec_email.setError("Enter the valid Email");

                    } else if (email.isEmpty()) {
                        rec_email.setError("Email is required");
                        rec_email.requestFocus();

                    } else {


                        progressDialog = new ProgressDialog(forgetPassword.this);
                        progressDialog.setMessage("Sending Verification Mail");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setTitle("Recover Password");
                        progressDialog.show();


                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(forgetPassword.this, "Check your Email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(forgetPassword.this, "Try Again! Something went wrong!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                } else {
                    Toast.makeText(forgetPassword.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}