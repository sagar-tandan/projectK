package com.example.projectk.Adapters;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectk.PDFViewer;
import com.example.projectk.R;
import com.example.projectk.model.Note_Model;

import java.io.File;
import java.util.List;

public class Note_Adapter extends RecyclerView.Adapter<Note_Adapter.MyViewHolder> {



    Context context;
    List<Note_Model> modelList;

    public Note_Adapter(Context context, List<Note_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_note_item,parent,false);
        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String title = modelList.get(position).getName();
        String url = modelList.get(position).getFileUrl();




        holder.name.setText(modelList.get(position).getName());
        holder.admin_bhadwa.setText(modelList.get(position).getAdmin());

        //Working of fileUrl for watch
        holder.watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, PDFViewer.class);
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                context.startActivity(intent);
            }
        });



        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);

//                String path =getRootDirPath(context);
//                Toast.makeText(context, "Download Started!", Toast.LENGTH_SHORT).show();
//               downloadFile(context,title, path,url);
            }
        });

    }

//    private void downloadFile(Context context, String title, String path, String url) {
//
//        DownloadManager downloadmanager = (DownloadManager) context.
//                getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(url);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalFilesDir(context, path, title + ".pdf");
//        downloadmanager.enqueue(request);
//        Toast.makeText(context, "File Stored at "+ path, Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    public static String getRootDirPath(Context context) {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(), null)[0];
//            return file.getAbsolutePath();
//        } else {
//            return context.getApplicationContext().getFilesDir().getAbsolutePath();
//        }
//    }



    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         CardView card;
         ImageView download;
         TextView name,admin_bhadwa;
         LinearLayoutCompat watch;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            download = itemView.findViewById(R.id.download);
            name = itemView.findViewById(R.id.name);
            watch = itemView.findViewById(R.id.watch);
            admin_bhadwa = itemView.findViewById(R.id.admin_bhadwa);

            
        }
    }
}
