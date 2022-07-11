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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectk.R;
import com.example.projectk.model.Notice_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_Notice_Adapter extends RecyclerView.Adapter<Admin_Notice_Adapter.MyViewHoolder> {

    Context context;
    List<Notice_Model> list;

    public Admin_Notice_Adapter(Context context, List<Notice_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHoolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_notice_item,parent,false);
        return new MyViewHoolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoolder holder, int position) {

        String notText = list.get(position).getNotice();
        String hash = list.get(position).getHashtag();
        String linkkk = list.get(position).getLink();
        String code = list.get(position).getId();
        String iAd = list.get(position).getId_with_admin();
        String url = list.get(position).getImageUrl();
        String mail = list.get(position).getAdmin();
        String key = list.get(position).getKey();
        String tm = list.get(position).getTime();
        String dt = list.get(position).getDate();


        holder.admin_bhadwa.setText(list.get(position).getAdmin());
        holder.notice_text.setText(list.get(position).getNotice());
        holder.hashtag.setText(list.get(position).getHashtag());
        holder.link.setText(list.get(position).getLink());
        holder.time.setText(list.get(position).getTime());
        holder.date.setText(list.get(position).getDate());

        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.notice_image);


        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (linkkk.contains("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(linkkk));
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Cannot open this link", Toast.LENGTH_SHORT).show();
                }

            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.admin_notice_edit))
                        .setExpanded(true,1100)
                        .create();

                View view1 = dialogPlus.getHolderView();
                EditText notices = view1.findViewById(R.id.notice);
                EditText hashtags= view1.findViewById(R.id.hashtag);
                EditText links= view1.findViewById(R.id.links);
                CardView update = view1.findViewById(R.id.update);


                notices.setText(notText);
                hashtags.setText(hash);
                links.setText(linkkk);
                dialogPlus.show();


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (notices.getText().toString().isEmpty()){
                            notices.setError("Field cannot be empty!");
                        }else{
                            Map<String,Object> hashMap = new HashMap<>();
                            hashMap.put("notice",notices.getText().toString());
                            hashMap.put("id",code);
                            hashMap.put("imageUrl",url);
                            hashMap.put("admin",mail);
                            hashMap.put("id_with_admin",iAd);
                            hashMap.put("key",key);
                            hashMap.put("time",tm);
                            hashMap.put("date",dt);
                            hashMap.put("hashtag",hashtags.getText().toString());
                            hashMap.put("link",links.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Notices")
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
                builder.setMessage("Are you sure to delete this Notice?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("ProjectK").child("Notices")
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

    public class MyViewHoolder extends RecyclerView.ViewHolder {

        private TextView admin_bhadwa,notice_text,hashtag,link,time,date;
        private ImageView edit,delete,notice_image;

        public MyViewHoolder(@NonNull View itemView) {
            super(itemView);
            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            notice_text = itemView.findViewById(R.id.notice_text);
            hashtag = itemView.findViewById(R.id.hashtag);
            link = itemView.findViewById(R.id.link);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            notice_image = itemView.findViewById(R.id.notice_image);

        }
    }
}
