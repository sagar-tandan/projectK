package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectk.model.Note_Model;
import com.example.projectk.model.Notice_Model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;

public class Admin_Notice extends AppCompatActivity {

    private ImageView back,choose_image,show_demo_image;
    private TextView name,uploaded_files;
    private EditText notice_detail,hashtag,link;
    private CardView upload_notice;

    private LinearLayoutCompat select_image;



    private Uri imageUri;
  //  private static final int REQUEST = 1;

    //firebase

    private StorageReference storageReference;
    private DatabaseReference databaseReference;


    //To stop user from uploading file for multiple time
    private StorageTask uploadTask;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice);

        back = findViewById(R.id.back);
        choose_image = findViewById(R.id.choose_image);
        show_demo_image = findViewById(R.id.show_demo_image);
        name = findViewById(R.id.name);
        notice_detail = findViewById(R.id.notice_detail);
        hashtag = findViewById(R.id.hashtag);
        link = findViewById(R.id.link);
        upload_notice = findViewById(R.id.upload_pdf);
        select_image = findViewById(R.id.select_image);
        uploaded_files = findViewById(R.id.uploaded_files);

        LocalDate today = LocalDate.now(ZoneId.of("America/Hermosillo"));



        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("ProjectK").child("Notices");



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

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select Image..."),101);
            }
        });

        upload_notice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                    // Restrict admin from uploading multiple file at same time

                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(Admin_Notice.this, "Cannot upload multiple files at the same time", Toast.LENGTH_SHORT).show();
                    }else {

                        if (imageUri == null) {
                            Toast.makeText(Admin_Notice.this, "Please select the image", Toast.LENGTH_SHORT).show();
                        } else if (notice_detail.getText().toString().trim().isEmpty()) {
                            notice_detail.setError("Enter the notice");
                        }else{

                            ProgressDialog progressDialog = new ProgressDialog(Admin_Notice.this);
                            progressDialog.setTitle("Notice Uploading...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            StorageReference reference = storageReference.child("Uploads/Notice" + System.currentTimeMillis() + ".jpg");


                            uploadTask =reference.putFile(imageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    String key = databaseReference.push().getKey();
                                                    Notice_Model model = new Notice_Model(uri.toString(),code,notice_detail.getText().toString().trim(),
                                                            link.getText().toString().trim(),hashtag.getText().toString().trim(),
                                                            today.format(userFormatter),sdf.format(c.getTime()),mail,code+mail.toLowerCase(),key);
                                                    databaseReference.child(key).setValue(model);

                                                    progressDialog.dismiss();
                                                    Toast.makeText(Admin_Notice.this, "Notice Uploaded!", Toast.LENGTH_LONG).show();

                                                    choose_image.setImageResource(R.drawable.cimage);
                                                    name.setText("Select Image");
                                                    notice_detail.setText("");
                                                    hashtag.setText("");
                                                    link.setText("");
                                                    show_demo_image.setImageResource(R.drawable.imagee);


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
                    Toast.makeText(Admin_Notice.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploaded_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Admin_Notice.this,Admin_Uploaded_Notice.class);
                intent1.putExtra("id",code);
                intent1.putExtra("Mail",mail);
                startActivity(intent1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data !=null && data.getData() !=null){

            imageUri = data.getData();
            show_demo_image.setImageURI(imageUri);
            choose_image.setImageResource(R.drawable.imagee);
            name.setText("Image Selected");

        }
    }
}