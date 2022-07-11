package com.example.projectk.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.R;
import com.example.projectk.model.Phone_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_Phn_Adapter extends RecyclerView.Adapter<Admin_Phn_Adapter.MyViewHolder> {

    Context context;
    List<Phone_Model> list;

    public Admin_Phn_Adapter(Context context, List<Phone_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_phone_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String nam = list.get(position).getTeacher_name();
        String phone = list.get(position).getPhone_no();
        String key = list.get(position).getKey();
        String mail = list.get(position).getAdmin();
        String tid = list.get(position).getId();
        String tidAdmin = list.get(position).getId_with_admin();



        holder.teacher_name.setText(list.get(position).getTeacher_name());
        holder.phone_number.setText(list.get(position).getPhone_no());
        holder.admin_bhadwa.setText(list.get(position).getAdmin());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.admin_phn_edit))
                        .setExpanded(true,1100)
                        .create();

                View view1 = dialogPlus.getHolderView();
                EditText name = view1.findViewById(R.id.name);
                EditText phone_no= view1.findViewById(R.id.phone_no);
                CardView update = view1.findViewById(R.id.update);

                name.setText(nam);
                phone_no.setText(phone);
                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (name.getText().toString().isEmpty()){
                            phone_no.setError("Field cannot be empty!");
                        }else if(phone_no.getText().toString().isEmpty()){
                            phone_no.setError("Field cannot be empty!");
                        }else{
                            Map<String,Object> hashMap = new HashMap<>();
                            hashMap.put("teacher_name",name.getText().toString());
                            hashMap.put("id",tid);
                            hashMap.put("admin",mail);
                            hashMap.put("id_with_admin",tidAdmin);
                            hashMap.put("key",key);
                            hashMap.put("phone_no",phone_no.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Phone")
                                    .child(key).updateChildren(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialogPlus.dismiss();
                                            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });

                        }
                    }
                });


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Panal");
                builder.setMessage("Are you sure to delete this number?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Phone")
                                .child(key).removeValue();
                        Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
            }
        });

        holder.watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView teacher_name,phone_number,admin_bhadwa;
        private ImageView edit,delete;
        private LinearLayoutCompat watch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            teacher_name = itemView.findViewById(R.id.teacher_name);
            phone_number = itemView.findViewById(R.id.phone_number);
            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            watch = itemView.findViewById(R.id.watch);
        }
    }
}
