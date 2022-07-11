package com.example.projectk.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.R;
import com.example.projectk.model.Routine_Model;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
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


        if(position %4 == 0)
        {
            holder.smallColor.setBackgroundColor(Color.parseColor("#E75757"));
            holder.bigColor.setBackgroundColor(Color.parseColor("#FAE6E7"));
        }
        else if(position%4==1)
        {
            holder.smallColor.setBackgroundColor(Color.parseColor("#6C5CB7"));
            holder.bigColor.setBackgroundColor(Color.parseColor("#EEEAFF"));
        }
        else if(position%4==2)
        {
            holder.smallColor.setBackgroundColor(Color.parseColor("#78AAAA"));
            holder.bigColor.setBackgroundColor(Color.parseColor("#D7F9F5"));
        }
        else{
            holder.smallColor.setBackgroundColor(Color.parseColor("#976D3D"));
            holder.bigColor.setBackgroundColor(Color.parseColor("#FCEBDD"));
        }



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView admin_bhadwa,routine,time,date,up,smallColor;
        LinearLayoutCompat bigColor;
        CardView card;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
            routine = itemView.findViewById(R.id.routine);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            up = itemView.findViewById(R.id.up);
            smallColor = itemView.findViewById(R.id.smallColor);
            bigColor = itemView.findViewById(R.id.bigColor);
            card = itemView.findViewById(R.id.card);

        }
    }
}
