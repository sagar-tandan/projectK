package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private EditText passwordL, login_email;
    private ImageView passShow;
    private CardView login_card;
    private TextView create_Account,forget_pass;
    private TextView admin;


    ProgressDialog progressDialog;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;





    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String SHemail, SHpassword;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_card = findViewById(R.id.login_card);
        login_email = findViewById(R.id.login_email);
        passShow = findViewById(R.id.passShow);
        passwordL = findViewById(R.id.passwordL);
        create_Account = findViewById(R.id.create_Account);
        forget_pass = findViewById(R.id.forget_pass);
        admin = findViewById(R.id.admin);




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        SHemail = sharedpreferences.getString(EMAIL_KEY, null);
        SHpassword = sharedpreferences.getString(PASSWORD_KEY, null);






        passShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHidePass(view);
            }
        });



        create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        login_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyUser();

            }
        });

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,forgetPassword.class);
                startActivity(intent);
            }
        });

        //ADMIN WORK HERE

       admin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,adminPanall.class);
               startActivity(intent);

           }
       });

    }


    public void ShowHidePass(View view) {

        if (view.getId() == R.id.passShow) {

            if (passwordL.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.visibility);
                //Show Password
                passwordL.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.eye);

                //Hide Password
                passwordL.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void VerifyUser() {

         String email = login_email.getText().toString().trim();
         String password = passwordL.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            login_email.setError("Enter the valid Email");
            login_email.requestFocus();

        } else if (email.isEmpty()) {
            login_email.setError("Email is required");
            login_email.requestFocus();

        }else if (password.isEmpty() || password.length() < 8) {
            passwordL.setError("Password cannot be empty or less than 8 letters");
            passwordL.requestFocus();
        } else {


            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait till Login....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("LOGIN");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        // below two lines will put values for
                        // email and password in shared preferences.
                        editor.putString(EMAIL_KEY,email);
                        editor.putString(PASSWORD_KEY, password);

                        // to save our data with key and value.
                        editor.apply();

                        SendUserToNextActivity();

                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void SendUserToNextActivity() {

        Intent intent = new Intent(MainActivity.this,UserPanalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SHemail != null && SHpassword  != null) {
            Intent i = new Intent(MainActivity.this, UserPanalActivity.class);
            startActivity(i);
            finish();
        }
    }
}