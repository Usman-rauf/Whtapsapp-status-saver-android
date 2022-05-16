package com.example.whatsappstatusdownloader;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyRecyclerAdapterClass extends RecyclerView.Adapter<MyViewHolderClass> {
    private Context context;
    private ArrayList<Object> arrayList;

    public MyRecyclerAdapterClass(Context context, ArrayList<Object> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list, null, false);
        MyViewHolderClass myViewHolderClass = new MyViewHolderClass(view);
        return myViewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderClass holder, int position) {

        final MyWhatAppStatusModelClass myWhatAppStatusModelClass = (MyWhatAppStatusModelClass) arrayList.get(position);
        if (myWhatAppStatusModelClass.getUri().toString().endsWith(".mp4")) {
            holder.PlayButtonImageView.setVisibility(View.VISIBLE);
        } else {
            holder.PlayButtonImageView.setVisibility(View.INVISIBLE);
        }
        Glide.with(context)
                .load(myWhatAppStatusModelClass.getUri())
                .into(holder.MainImageView);
        holder.DownloadID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CheckFolderIsVisible();
                final String path = ((MyWhatAppStatusModelClass) arrayList.get(position)).getPath();
                final File file = new File(path);
                String destPath = Environment.getDownloadCacheDirectory().getAbsolutePath() + ConstantPathClass.SAVE_FOLDER_NAME;
                File destFile = new File(destPath);

                try {
                    FileUtils.copyFileToDirectory(file, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(
                        context,
                        new String[]{destPath + myWhatAppStatusModelClass.getFileName()},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        }
                );
                Toast.makeText(context, "SAved to:"+destPath+myWhatAppStatusModelClass.getFileName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckFolderIsVisible() {
        String path = Environment.getDownloadCacheDirectory().getAbsolutePath() + ConstantPathClass.SAVE_FOLDER_NAME;
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            Toast.makeText(context, "Folder Already Created", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
