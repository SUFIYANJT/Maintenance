package com.example.project;

import static com.example.project.Loginpage.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerInspecterMode extends RecyclerView.Adapter<RecyclerInspecterMode.MyHolder> {
    ArrayList<DataTable> dataTables;
    InspectorMode inspectorMode;
    int user_id;
    public RecyclerInspecterMode(ArrayList<DataTable> dataTables, InspectorMode inspectorMode, int user_id){
        this.dataTables=dataTables;
        this.inspectorMode=inspectorMode;
        this.user_id=user_id;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspector_mode_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView1.setText(dataTables.get(position).getName());
        holder.textView2.setText(dataTables.get(position).getDescription());
        holder.textView3.setText(String.valueOf(dataTables.get(position).getTime()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(inspectorMode, SubmitReport.class);
            Log.d(TAG, "onBindViewHolder: activity_id"+dataTables.get(position).getActivity_id());
            intent.putExtra("activityid",dataTables.get(position).getActivity_id());
            intent.putExtra("userid",user_id);
            Log.d(TAG, "onBindViewHolder: "+user_id);
            inspectorMode.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataTables.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView_recycler1);
            textView2=itemView.findViewById(R.id.textView_recycler2);
            textView3=itemView.findViewById(R.id.textView_recycler3);
        }
    }
}
