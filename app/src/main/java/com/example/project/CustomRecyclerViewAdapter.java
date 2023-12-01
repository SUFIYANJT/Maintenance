package com.example.project;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<DataTable> dataTables;
    List<PendingData> pendingData;
    Context mainActivity;
    int mode=1;
    public CustomRecyclerViewAdapter(List<DataTable> dataTables, Context activity){
        this.dataTables=dataTables;
        this.mainActivity=activity;
    }
    public CustomRecyclerViewAdapter(List<PendingData> pendingData,int i,Context activity){
        this.mode=i;
        this.pendingData=pendingData;
        this.mainActivity=activity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder;
        if(mode==1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.existing_activity_item, parent, false);
             myViewHolder= new MyViewHolder(view,mode);
        }else if(mode==2){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_activity_item, parent, false);
            myViewHolder = new MyViewHolder(view,mode);
            Log.d(TAG, "onCreateViewHolder: mode 2");
        }else if(mode==3){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspector_review_item, parent, false);
            myViewHolder = new MyViewHolder(view,mode);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_activity_item, parent, false);
            myViewHolder = new MyViewHolder(view,mode);
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(mode==1) {
            holder.name.setText(dataTables.get(position).name);
            holder.description.setText(dataTables.get(position).description);
            holder.history.setText(dataTables.get(position).history);

        } else if (mode==2) {
            holder.progressBar.setProgress(pendingData.get(position).progress,true);
            holder.description1.setText(pendingData.get(position).description);
            holder.activity_name.setText(pendingData.get(position).activity_name);
        } else if (mode==3) {

        }

    }

    @Override
    public int getItemCount() {
        int size=0;
        if(mode==1){
            size=dataTables.size();
        } else if (mode==2) {
            size=pendingData.size();
            Log.d(TAG, "getItemCount: "+size);
        } else if (mode==3) {
            size=pendingData.size();
        }
        return size;
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView name,description,history,status,report;
    TextView activity_name,description1;
    ConstraintLayout constraintLayout;
    com.google.android.material.progressindicator.LinearProgressIndicator progressBar;
    public MyViewHolder(@NonNull View itemView,int mode) {
        super(itemView);
        if(mode==1) {
            name = itemView.findViewById(R.id.activity_name);
            description = itemView.findViewById(R.id.activity_description);
            history = itemView.findViewById(R.id.activity_history);
            constraintLayout=itemView.findViewById(R.id.existItem);
        }else if (mode==2){
            activity_name=itemView.findViewById(R.id.pending_activity_name);
            description1=itemView.findViewById(R.id.pending_description_name);
            progressBar=itemView.findViewById(R.id.pending_progress);
        } else if (mode==3) {
            activity_name=itemView.findViewById(R.id.inspector_activity_name);
            description=itemView.findViewById(R.id.inspector_activity_description);
            status=itemView.findViewById(R.id.inspector_activity_status);
            report=itemView.findViewById(R.id.inspector_activity_report);
        }
    }
}