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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.HelperClass.DataHolder.MyModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.MyViewHolder> {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(mode==1) {
            holder.name.setText(dataTables.get(position).name);
            holder.description.setText(dataTables.get(position).description);
            holder.history.setText(String.format("time remaining: %s", String.valueOf(dataTables.get(position).getTime())));
            holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.setAlpha(0f);
                    v.animate().alpha(1f).setDuration(500).withEndAction(() -> {
                        Intent intent=new Intent(v.getContext(),EditActivity.class);
                        intent.putExtra("activity_id",dataTables.get(position).getActivity_id());
                        intent.putExtra("activity_name",dataTables.get(position).getName());
                        intent.putExtra("description",dataTables.get(position).getDescription());
                        intent.putExtra("machine_id",dataTables.get(position).getMachine_id());
                        intent.putExtra("component_id",dataTables.get(position).getComponent_id());
                        intent.putExtra("schedule_id",dataTables.get(position).getSchedule_id());
                        intent.putExtra("dataTable",dataTables.get(position));
                        mainActivity.startActivity(intent);
                    });
                    return true;
                }
            });

        } else if (mode==2) {
            holder.progressBar.setProgress(10,true);
            holder.description1.setText(pendingData.get(position).description);
            holder.activity_name.setText(pendingData.get(position).getName());
        } else if (mode==3) {
            holder.activity_name.setText(pendingData.get(position).getName());
            holder.description.setText(pendingData.get(position).description);
            holder.report.setText("view");
            holder.report.setClickable(true);
            holder.report.setOnClickListener(v -> {
                Intent intent=new Intent(mainActivity,ReportViewer.class);
                intent.putExtra("activity_id",pendingData.get(position).getId());
                mainActivity.startActivity(intent);
            });

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
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,description,history,status,report;
        TextView activity_name,description1;
        MaterialCardView constraintLayout;
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
                report=itemView.findViewById(R.id.inspector_activity_report);
            }
        }
    }
}
