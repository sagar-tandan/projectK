package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private ImageView back,passShow,CpassShow;
    private EditText signup_email,signup_pass,signup_Cpass;
    private CardView create_account;
    private TextView login_page;


    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        back = findViewById(R.id.back);
        passShow = findViewById(R.id.passShow);
        signup_email = findViewById(R.id.signup_email);
        signup_pass = findViewById(R.id.signup_pass);
        create_account = findViewById(R.id.create_account);
        login_page = findViewById(R.id.login_page);
        CpassShow = findViewById(R.id.CpassShow);
        signup_Cpass = findViewById(R.id.signup_Cpass);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();







        //Fiunctioning of back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
               // finish();

            }
        });

        //Functioning of login button
        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });



        passShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHidePass(view);
            }
        });

        CpassShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHidePass1(view);
            }
        });


        // CREATE Account Working here
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptEmail_Pass();
            }
        });
    }



    private void acceptEmail_Pass() {

         String email = signup_email.getText().toString().trim();
         String password = signup_pass.getText().toString().trim();
         String Confirmpassword = signup_Cpass.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signup_email.setError("Enter the valid Email");
            signup_email.requestFocus();

        } else if (email.isEmpty()){
            signup_email.setError("Email is required");
            signup_email.requestFocus();

        }else if (password.isEmpty() || password.length()<8){
                signup_pass.setError("Password cannot be empty or less than 8 letters");
                signup_pass.requestFocus();

            }else if (!password.matches(Confirmpassword)){
                signup_Cpass.setError("Password doesn't matches");
                signup_Cpass.requestFocus();
            }else{


            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("Please wait till registration....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Creating Account");
            progressDialog.show();





               firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            SendUserToNextActivity();
                            Toast.makeText(SignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Failed to Sign up!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        }




    public void ShowHidePass(View view){

        if(view.getId()==R.id.passShow){

            if(signup_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.visibility);
                //Show Password
                signup_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye);

                //Hide Password
                signup_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void ShowHidePass1(View view) {


        if(view.getId()==R.id.CpassShow){

            if(signup_Cpass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.visibility);
                //Show Password
                signup_Cpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye);

                //Hide Password
                signup_Cpass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void SendUserToNextActivity() {

        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
