package com.example.projectk.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.projectk.PDFViewer;
import com.example.projectk.R;
import com.example.projectk.model.Assessment_Model;
import com.example.projectk.model.Note_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_Ass_Adapter  extends RecyclerView.Adapter<Admin_Ass_Adapter.MyViewHolder> {

    Context context;
    List<Assessment_Model> models;

    public Admin_Ass_Adapter(Context context, List<Assessment_Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public Admin_Ass_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_ass_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(models.get(position).getName());
        holder.admin_bhadwa.setText(models.get(position).getAdmin());
        holder.due_date.setText(models.get(position).getDueDate());


        String url = models.get(position).getFileUrl();
        String name = models.get(position).getName();
        String key = models.get(position).getKey();
        String code = models.get(position).getId();
        String mail = models.get(position).getAdmin();
        String date = models.get(position).getDueDate();



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.admin_ass_edit))
                        .setExpanded(true,1100)
                        .create();

                View view1 = dialogPlus.getHolderView();
                EditText edit_title = view1.findViewById(R.id.edit_title);
                EditText edit_date= view1.findViewById(R.id.sub_date);
                CardView update = view1.findViewById(R.id.update);

                edit_title.setText(name);
                edit_date.setText(date);
                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edit_title.getText().toString().isEmpty()){
                            edit_title.setError("Field cannot be empty!");
                        }else if(edit_title.getText().toString().isEmpty()){
                            edit_date.setError("Field cannot be empty!");
                        }else{
                            Map<String,Object> hashMap = new HashMap<>();
                            hashMap.put("name",edit_title.getText().toString());
                            hashMap.put("id",code);
                            hashMap.put("fileUrl",url);
                            hashMap.put("admin",mail);
                            hashMap.put("id_with_admin",code+mail.toLowerCase());
                            hashMap.put("key",key);
                            hashMap.put("dueDate",edit_date.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Assignment")
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

        holder.watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PDFViewer.class);
                intent.putExtra("url",url);
                intent.putExtra("title",name);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Panal");
                builder.setMessage("Are you sure to delete this file?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Assignment")
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
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name,due_date,admin_bhadwa;
        private ImageView edit,delete;
        LinearLayoutCompat watch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            watch = itemView.findViewById(R.id.watch);
            due_date = itemView.findViewById(R.id.due_date);
        }
    }
}
