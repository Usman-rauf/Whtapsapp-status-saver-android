package com.example.whatsappstatusdownloader;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderClass extends RecyclerView.ViewHolder {
    ImageView DownloadID,PlayButtonImageView,MainImageView;
    public MyViewHolderClass(@NonNull View itemView) {
        super(itemView);
        DownloadID=itemView.findViewById(R.id.DownloadID);
        PlayButtonImageView=itemView.findViewById(R.id.PlayButtonImageView);
        MainImageView=itemView.findViewById(R.id.MainImageView);
    }
}
