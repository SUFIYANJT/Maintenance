package com.example.project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ExistingActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_existing_activity, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<DataTable> dataTables=new ArrayList<>();
        DataTable dataTable1=new DataTable();
        DataTable dataTable2=new DataTable();
        DataTable dataTable3=new DataTable();
        DataTable dataTable4=new DataTable();
        dataTable1.setData("activity 1","activity Description","time");
        dataTable2.setData("activity 2","activity Description","time");
        dataTable3.setData("activity 3","activity Description","time");
        dataTable4.setData("activity 4","activity Description","time");
        dataTables.add(dataTable1);
        dataTables.add(dataTable2);
        dataTables.add(dataTable3);
        dataTables.add(dataTable4);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //view.setBackgroundColor(getResources().getColor(R.color.gray));
                view.setAlpha(0f);
                view.animate().alpha(1f).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(view.getContext(),EditActivity.class);
                        view.getContext().startActivity(intent);
                    }
                });

            }
        }));
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(dataTables,view.getContext()));
        return  view;
        //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
    }
}