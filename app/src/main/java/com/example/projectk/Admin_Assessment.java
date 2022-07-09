package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectk.model.Assessment_Model;
import com.example.projectk.model.Note_Model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Admin_Assessment extends AppCompatActivity {


    private ImageView back,choose_image;
    private TextView pdf_text;
    private EditText name_pdf,due_date;
    private CardView upload_pdf;
    private LinearLayoutCompat select_pdf;
    Uri filepath;



    StorageReference storageReference;
    DatabaseReference databaseReference;


    //To stop user from uploading file for multiple time
    private StorageTask uploadTask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assessment);


        select_pdf = findViewById(R.id.select_pdf);
        back = findViewById(R.id.back);
        choose_image = findViewById(R.id.choose_image);
        pdf_text = findViewById(R.id.pdf_selector);
        name_pdf = findViewById(R.id.name_pdf);
        upload_pdf = findViewById(R.id.upload_pdf);
        due_date = findViewById(R.id.due_date);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("ProjectK").child("Assignment");


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

        select_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select pdf files...."),101);
            }
        });


        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //First Check Internet Connection

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {

                    // Restrict admin from uploading multiple file at same time

                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(Admin_Assessment.this, "Cannot upload multiple files at the same time", Toast.LENGTH_SHORT).show();
                    }else {

                        if (filepath == null) {
                            Toast.makeText(Admin_Assessment.this, "Please select the pdf", Toast.LENGTH_SHORT).show();
                        } else if (name_pdf.getText().toString().trim().isEmpty()) {
                            name_pdf.setError("Enter the name");
                        } else if (due_date.getText().toString().trim().isEmpty()) {
                            due_date.setError("Enter the submission date");
                        } else {

                            ProgressDialog progressDialog = new ProgressDialog(Admin_Assessment.this);
                            progressDialog.setTitle("File Uploading...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            StorageReference reference = storageReference.child("Uploads/Assignment/" +name_pdf.getText().toString().trim()+ System.currentTimeMillis() + ".pdf");


                           uploadTask= reference.putFile(filepath)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Assessment_Model model = new Assessment_Model(name_pdf.getText().toString().trim(), code, uri.toString(), "Submission date: " + due_date.getText().toString().trim(),mail);
                                                    databaseReference.child(databaseReference.push().getKey()).setValue(model);

                                                    progressDialog.dismiss();
                                                    Toast.makeText(Admin_Assessment.this, "File Uploaded!", Toast.LENGTH_LONG).show();

                                                    choose_image.setImageResource(R.drawable.note);
                                                    pdf_text.setText("Select Pdf");
                                                    name_pdf.setText("");
                                                    due_date.setText("");


                                                }
                                            });
                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            float total_progress = (float) ((snapshot.getBytesTransferred() / snapshot.getTotalByteCount()) * 100.0);
                                            progressDialog.setMessage("Uploaded: " + (int) total_progress + "%");

                                        }
                                    });
                        }
                    }
                }else {
                    Toast.makeText(Admin_Assessment.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data !=null && data.getData() !=null){

            filepath = data.getData();
            choose_image.setImageResource(R.drawable.pdf);
            pdf_text.setText("Pdf Selected");
        }
    }
}