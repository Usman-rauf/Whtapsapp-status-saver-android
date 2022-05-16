package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerList;
    SwipeRefreshLayout swipeRefreshLayout;
    MyRecyclerAdapterClass myRecyclerAdapterClass;
    File[] files;
    ArrayList<Object> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntialView();
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                SetUpRefreshLayout();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    private void  IntialView()
    {
        recyclerList=findViewById(R.id.recyclerList);
        swipeRefreshLayout=findViewById(R.id.SwipRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                SetUpRefreshLayout();
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(MainActivity.this, "Refresh!", Toast.LENGTH_SHORT).show();
                        }
                    },2000);
                }
            }
        });
    }
    private void SetUpRefreshLayout()
    {
        arrayList.clear();
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myRecyclerAdapterClass=new MyRecyclerAdapterClass(MainActivity.this,getData());
        recyclerList.setAdapter(myRecyclerAdapterClass);
        myRecyclerAdapterClass.notifyDataSetChanged();
    }

    private ArrayList<Object> getData() {
        MyWhatAppStatusModelClass myWhatAppStatusModelClass;
        String TargetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ConstantPathClass.FOLDER_NAME+"Media/.Statuses";
        File TargetDirectory=new File(TargetPath);
        files=TargetDirectory.listFiles();
        for(int i=0;i<files.length;i++)
        {
            File file=files[i];
            myWhatAppStatusModelClass=new MyWhatAppStatusModelClass();
            myWhatAppStatusModelClass.setUri(Uri.fromFile(file));
            myWhatAppStatusModelClass.setPath(files[i].getAbsolutePath());
            myWhatAppStatusModelClass.setFileName(file.getName());
            if(!myWhatAppStatusModelClass.getUri().toString().endsWith("nomedia"))
            {
                arrayList.add(myWhatAppStatusModelClass);
            }
        }
        return arrayList;
    }

}