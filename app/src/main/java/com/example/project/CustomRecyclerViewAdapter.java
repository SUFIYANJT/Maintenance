package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<DataTable> dataTables;
    public CustomRecyclerViewAdapter(List<DataTable> dataTables){
        this.dataTables=dataTables;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.existing_activity_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText( dataTables.get(position).name);
        holder.description.setText(dataTables.get(position).name);
        holder.history.setText(dataTables.get(position).history);

    }

    @Override
    public int getItemCount() {
        return dataTables.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView name,description,history;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.activity_name);
        description=itemView.findViewById(R.id.activity_description);
        history=itemView.findViewById(R.id.activity_history);
    }
}