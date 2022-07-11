package com.example.projectk.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.R;
import com.example.projectk.model.Routine_Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class Routine_Adapter extends RecyclerView.Adapter<Routine_Adapter.MyViewholder> {

    Context context;
    List<Routine_Model> list;

    public Routine_Adapter(Context context, List<Routine_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_routine_item,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {



        holder.routine.setText(list.get(position).getRoutine());
        holder.time.setText(list.get(position).getTime());

        holder.admin_bhadwa.setText(list.get(position).getAdmin());
        holder.date.setText(list.get(position).getDate());
        holder.up.setText(list.get(position).getUpdate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView admin_bhadwa,routine,time,date,up;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            routine = itemView.findViewById(R.id.routine);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            up = itemView.findViewById(R.id.up);

        }
    }
}
