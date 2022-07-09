package com.example.projectk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectk.Adapters.Note_Adapter;
import com.example.projectk.Adapters.Phone_Adapter;
import com.example.projectk.model.Note_Model;
import com.example.projectk.model.Phone_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_Phone extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ImageView back;

    private TextView no_data_found;

    private SwipeRefreshLayout swipe;

    private DatabaseReference reference;

    private List<Phone_Model> list;

    private Phone_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_phone);

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


        reference = FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Phone");
        reference.keepSynced(true);



        RetriveData();


        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                RetriveData();
            }
        });


    }

    private void RetriveData() {

        Intent intent = getIntent();
        String codeNo = intent.getStringExtra("id");

        ProgressDialog progressDialog = new ProgressDialog(User_Phone.this);
        progressDialog.setTitle("Phone Number");
        progressDialog.setMessage("Fetching data....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        reference.orderByChild("id").equalTo(codeNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    Phone_Model m = snapshot1.getValue(Phone_Model.class);
                    list.add(m);
                    no_data_found.setText("");
                }


                adapter = new Phone_Adapter(User_Phone.this,list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(User_Phone.this);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Phone.this, "No such class exists", Toast.LENGTH_SHORT).show();
            }
        });



    }
}