package com.example.projectk.Adapters;

import android.content.Context;
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
import com.example.projectk.model.Note_Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
//public class Admin_Note_Adapter extends FirebaseRecyclerAdapter<Note_Model,Admin_Note_Adapter.MyViewHolder> {
//
//    public Admin_Note_Adapter(@NonNull FirebaseRecyclerOptions<Note_Model> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull Admin_Note_Adapter.MyViewHolder holder, int position, @NonNull Note_Model model) {
//
//    }
//
//    @NonNull
//    @Override
//    public Admin_Note_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}


public class Admin_Note_Adapter extends RecyclerView.Adapter<Admin_Note_Adapter.MyViewHolder> {

    Context context;
    List<Note_Model> models;

    public Admin_Note_Adapter(Context context, List<Note_Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_note_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        String url = models.get(position).getFileUrl();
        String name = models.get(position).getName();
        String key = models.get(position).getKey();
        String code = models.get(position).getId();
        String mail = models.get(position).getAdmin();



        holder.name.setText(models.get(position).getName());
        holder.admin_bhadwa.setText(models.get(position).getAdmin());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.admin_note_edit))
                        .setExpanded(true,1100)
                        .create();

                View view1 = dialogPlus.getHolderView();
                EditText edit_title = view1.findViewById(R.id.edit_title);
                CardView update = view1.findViewById(R.id.update);

                edit_title.setText(name);
                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edit_title.getText().toString().isEmpty()){
                            edit_title.setError("Field cannot be empty!");
                        }else{
                            Map<String,Object> hashMap = new HashMap<>();
                            hashMap.put("name",edit_title.getText().toString());
                            hashMap.put("id",code);
                            hashMap.put("fileUrl",url);
                            hashMap.put("admin",mail);
                            hashMap.put("id_with_admin",code+mail.toLowerCase());
                            hashMap.put("key",key);

                            FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Notes")
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

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name,admin_bhadwa;
        private ImageView edit,delete;
        LinearLayoutCompat watch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            watch = itemView.findViewById(R.id.watch);
        }
    }
}
