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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.projectk.model.Admin_SignUp_Model;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText passwordL, login_email;
    private ImageView passShow;
    private CardView login_card;
    private TextView create_Account,forget_pass;
    private CardView admin;


    ProgressDialog progressDialog;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    DatabaseReference reference;





    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS1 = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY1 = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY1 = "password_key";

    public static final String ENTER_KEY = "enter_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences0;
    String Semail, Spassword,enter;

    String check,e,p;



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
        sharedpreferences0 = getSharedPreferences(SHARED_PREFS1, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        Semail = sharedpreferences0.getString(EMAIL_KEY1, null);
        Spassword = sharedpreferences0.getString(PASSWORD_KEY1, null);
        enter = sharedpreferences0.getString(ENTER_KEY, null);
       // Spassword = sharedpreferences0.getString(PASSWORD_KEY1, null);



        reference = FirebaseDatabase.getInstance().getReference().child("ProjectK").child("LOGIN");



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

                //Check Internet Connection

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){

                    VerifyUser();

                }else{
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }


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


               SharedPreferences.Editor editor = sharedpreferences0.edit();

               editor.putString(ENTER_KEY,"ADMIN");

               // to save our data with key and value.
               editor.apply();


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

                       // List<Admin_SignUp_Model> list;

                         reference.child("User").orderByChild("who").addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                     Admin_SignUp_Model model = snapshot1.getValue(Admin_SignUp_Model.class);
                                     check = model.getWho();
                                     e = model.getEmail();
                                     p = model.getPassword();
                                   //  Toast.makeText(MainActivity.this, check, Toast.LENGTH_SHORT).show();
                                     if (check.equals("USER") && e.equals(email)) {

                             progressDialog.dismiss();

                             SharedPreferences.Editor editor = sharedpreferences0.edit();

                             // below two lines will put values for
                             // email and password in shared preferences.
                             editor.putString(EMAIL_KEY1, email);
                             editor.putString(PASSWORD_KEY1, password);

                             // to save our data with key and value.
                             editor.apply();

                             SendUserToNextActivity();

                             Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                         }else {
                                         progressDialog.dismiss();
                             Toast.makeText(MainActivity.this, "Not a User Account!", Toast.LENGTH_SHORT).show();
                         }

                                 }

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });


                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void SendUserToNextActivity() {

      //  Intent intent = new Intent(MainActivity.this,UserPanalActivity.class);
        Intent intent = new Intent(MainActivity.this,Enter_class_Code.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (enter!=null){
            Intent i = new Intent(MainActivity.this, adminPanall.class);
           // Toast.makeText(this, enter, Toast.LENGTH_SHORT).show();
            startActivity(i);
            finish();

        }else if(Semail != null && Spassword  != null) {
//           Intent i = new Intent(MainActivity.this, UserPanalActivity.class);
           Intent i = new Intent(MainActivity.this, Enter_class_Code.class);
           startActivity(i);
            finish();
        }else {
            Toast.makeText(this, "WELCOME!", Toast.LENGTH_SHORT).show();
        }
    }
}