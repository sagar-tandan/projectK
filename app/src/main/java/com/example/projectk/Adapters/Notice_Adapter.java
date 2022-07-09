package com.example.projectk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectk.R;
import com.example.projectk.model.Notice_Model;

import java.util.List;

public class Notice_Adapter extends RecyclerView.Adapter<Notice_Adapter.MyViewHolder> {

    Context context;
    List<Notice_Model> list;

    public Notice_Adapter(Context context, List<Notice_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Notice_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_notice_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notice_Adapter.MyViewHolder holder, final int position) {

        holder.notice_text.setText(list.get(position).getNotice());
        holder.hashtag.setText(list.get(position).getHashtag());
        holder.link.setText(list.get(position).getLink());
        holder.time.setText(list.get(position).getTime());
        holder.date.setText(list.get(position).getDate());
        holder.admin_bhadwa.setText(list.get(position).getAdmin());

        String linkk = list.get(position).getLink();

        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.notice_image);

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (linkk.contains("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(linkk));
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Cannot open this link", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView notice_text,hashtag,link,time,date,admin_bhadwa;
        private ImageView notice_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notice_image = itemView.findViewById(R.id.notice_image);
            notice_text = itemView.findViewById(R.id.notice_text);
            hashtag = itemView.findViewById(R.id.hashtag);
            link = itemView.findViewById(R.id.link);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);
        }
    }
}
