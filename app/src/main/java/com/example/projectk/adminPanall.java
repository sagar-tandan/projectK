package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminPanall extends AppCompatActivity {


    private ImageView back,passShow;
    private EditText login_email,pass;
    private TextView forget_password,create_admin_acc;
    private CardView Login_Admin;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ProgressDialog progressDialog;

    DatabaseReference databaseReference;


    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String SHemail, SHpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panall);

        back = findViewById(R.id.back);
        login_email = findViewById(R.id.login_email);
        pass = findViewById(R.id.passwordL);
        forget_password = findViewById(R.id.forget_pass);
        create_admin_acc = findViewById(R.id.create_Account);
        Login_Admin = findViewById(R.id.login_card);
        passShow = findViewById(R.id.passShow);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        databaseReference = FirebaseDatabase.getInstance().getReference("ProjectK").child("LOGIN");



        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        SHemail = sharedpreferences.getString(EMAIL_KEY, null);
        SHpassword = sharedpreferences.getString(PASSWORD_KEY, null);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Show and Hide Password
        passShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.passShow) {

                    if (pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        ((ImageView) (view)).setImageResource(R.drawable.visibility);
                        //Show Password
                        pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ((ImageView) (view)).setImageResource(R.drawable.eye);

                        //Hide Password
                        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });


        //Forget Password section
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminPanall.this,forgetPassword.class);
                startActivity(intent);

            }
        });

        //Create account
        create_admin_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminPanall.this,Admin_SignUp.class);
                startActivity(intent);
            }
        });


        Login_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check Internet Connection

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {

                    String email = login_email.getText().toString().trim();
                    String password = pass.getText().toString().trim();

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        login_email.setError("Enter the valid Email");
                        login_email.requestFocus();
                    } else if (email.isEmpty()) {
                        login_email.setError("Email is required");
                        login_email.requestFocus();
                    } else if (password.isEmpty() || password.length() < 8) {
                        pass.setError("Password cannot be empty or less than 8 letters");
                        pass.requestFocus();
                    } else {

                        progressDialog = new ProgressDialog(adminPanall.this);
                        progressDialog.setMessage("Please wait till Login....");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setTitle("LOGIN");
                        progressDialog.show();


                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        // below two lines will put values for
                                        // email and password in shared preferences.
                                        editor.putString(EMAIL_KEY, email);
                                        editor.putString(PASSWORD_KEY, password);

                                        // to save our data with key and value.
                                        editor.apply();

                                        progressDialog.dismiss();
                                        SendAdminToNewActivity();
                                        Toast.makeText(adminPanall.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    }else {

                                        Toast.makeText(adminPanall.this, "Please Verify your Email Address! ", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }


                                } else {
                                    Toast.makeText(adminPanall.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(adminPanall.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void SendAdminToNewActivity() {
        Intent intent = new Intent(adminPanall.this,AdminInterface.class);
        intent.putExtra("Email",login_email.getText().toString().trim());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (SHemail != null && SHpassword  != null) {
            Intent i = new Intent(adminPanall.this, AdminInterface.class);
            i.putExtra("Email",sharedpreferences.getString(EMAIL_KEY,null));
            startActivity(i);
            finish();
        }
    }
}