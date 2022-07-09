package com.example.projectk.Adapters;


import android.app.DownloadManager;
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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.PDFViewer;
import com.example.projectk.R;
import com.example.projectk.model.Assessment_Model;

import java.util.List;

public class Assessment_Adapter extends RecyclerView.Adapter<Assessment_Adapter.MyViewHolder> {



    Context context;
    List<Assessment_Model> list;

    public Assessment_Adapter(Context context, List<Assessment_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Assessment_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_assessment_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Assessment_Adapter.MyViewHolder holder, int position) {


        String url = list.get(position).getFileUrl();
        String name = list.get(position).getName();


        holder.file_name.setText(list.get(position).getName());
        holder.due_date.setText(list.get(position).getDueDate());
        holder.admin_bhawda.setText(list.get(position).getAdmin());

        //Working of fileUrl for download
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);

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

//    private void DownloadPdf(Context context, String name, String s, String directoryDownloads, String url) {
//        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(url);
//        DownloadManager.Request request= new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//        request.setDestinationInExternalFilesDir(context,directoryDownloads,name + s);
//        long reference = downloadManager.enqueue(request);
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView file_name,due_date,admin_bhawda;
        LinearLayoutCompat watch;
        ImageView download;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            file_name = itemView.findViewById(R.id.file_name);
            watch = itemView.findViewById(R.id.watch);
            due_date = itemView.findViewById(R.id.due_date);
            download = itemView.findViewById(R.id.download);
            admin_bhawda = itemView.findViewById(R.id.admin_bhawda);
        }
    }
}
