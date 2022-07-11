package com.example.projectk.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.R;
import com.example.projectk.model.Routine_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_Routine_Adapter extends RecyclerView.Adapter<Admin_Routine_Adapter.MyViewholder> {

    Context context;
    List<Routine_Model> list;

    public Admin_Routine_Adapter(Context context, List<Routine_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Admin_Routine_Adapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.admiin_routine_item,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Routine_Adapter.MyViewholder holder, int position) {



        String routine1 = list.get(position).getRoutine();
        String code = list.get(position).getId();
        String iAd = list.get(position).getId_with_admin();
        String mail = list.get(position).getAdmin();
        String key = list.get(position).getKey();
        String tm = list.get(position).getTime();
        String dt = list.get(position).getDate();
        String update1 = list.get(position).getUpdate();



        holder.routine.setText(list.get(position).getRoutine());
        holder.time.setText(list.get(position).getTime());

        holder.admin_bhadwa.setText(list.get(position).getAdmin());
        holder.date.setText(list.get(position).getDate());
        holder.up.setText(list.get(position).getUpdate());



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.admin_routine_edit))
                        .setExpanded(true,1100)
                        .create();

                View view1 = dialogPlus.getHolderView();
                EditText routine = view1.findViewById(R.id.routine);
                EditText version= view1.findViewById(R.id.version);
                CardView update = view1.findViewById(R.id.update);


                routine.setText(routine1);
                version.setText(update1);
                dialogPlus.show();


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (routine.getText().toString().isEmpty()){
                            routine.setError("Field cannot be empty!");
                        }else{
                            Map<String,Object> hashMap = new HashMap<>();
                            hashMap.put("routine",routine.getText().toString());
                            hashMap.put("id",code);
                            hashMap.put("admin",mail);
                            hashMap.put("id_with_admin",iAd);
                            hashMap.put("key",key);
                            hashMap.put("time",tm);
                            hashMap.put("date",dt);
                            hashMap.put("update",version.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Routine")
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
                builder.setMessage("Are you sure to delete this routine?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Routine")
                                .child(key).removeValue();
                        Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView admin_bhadwa,routine,time,date,up;
        ImageView edit,delete;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            routine = itemView.findViewById(R.id.routine);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            up = itemView.findViewById(R.id.up);
        }
    }
}
