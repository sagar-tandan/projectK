package com.example.projectk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.R;
import com.example.projectk.model.Phone_Model;

import java.util.List;

public class Phone_Adapter extends RecyclerView.Adapter<Phone_Adapter.MyViewHolder> {

    Context context;
    List<Phone_Model> list;

    public Phone_Adapter(Context context, List<Phone_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_phone_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String phone = list.get(position).getPhone_no();

        holder.teacher_name.setText(list.get(position).getTeacher_name());
        holder.phone_number.setText(list.get(position).getPhone_no());
        holder.admin_bhawda.setText(list.get(position).getAdmin());


        holder.call.setOnClickListener(new View.OnClickListener() {
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

        TextView phone_number,teacher_name,admin_bhawda;
        ImageView call;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            phone_number = itemView.findViewById(R.id.phone_number);
            admin_bhawda = itemView.findViewById(R.id.admin_bhawda);
            call = itemView.findViewById(R.id.call);

        }
    }
}
