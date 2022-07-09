package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectk.Adapters.Assessment_Adapter;
import com.example.projectk.Adapters.Phone_Adapter;
import com.example.projectk.model.Assessment_Model;
import com.example.projectk.model.Phone_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_Assessment extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ImageView back;

    private TextView no_data_found;

    private SwipeRefreshLayout swipe;

    private DatabaseReference reference;

    private List<Assessment_Model> list;

    private Assessment_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_assessment);



        recyclerView = findViewById(R.id.recyclerView);
        swipe = findViewById(R.id.swipe);
        back = findViewById(R.id.back);
        no_data_found = findViewById(R.id.no_data_found);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Assignment");

        //Check Internet Connection

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){

            RetriveData();

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.setRefreshing(false);
                    RetriveData();
                }
            });

        }else{
            Toast.makeText(User_Assessment.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }



    }

    private void RetriveData() {

        Intent intent = getIntent();
        String codeNo = intent.getStringExtra("id");

        ProgressDialog progressDialog = new ProgressDialog(User_Assessment.this);
        progressDialog.setTitle("Assignment Files");
        progressDialog.setMessage("Fetching data....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        reference.orderByChild("id").equalTo(codeNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    Assessment_Model m = snapshot1.getValue(Assessment_Model.class);
                    list.add(m);
                    no_data_found.setText("");
                }


                adapter = new Assessment_Adapter(User_Assessment.this,list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(User_Assessment.this);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Assessment.this, "No such class exists", Toast.LENGTH_SHORT).show();
            }
        });



    }
}