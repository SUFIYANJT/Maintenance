package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        dataTable1.setData("activity Name","activity Description","History");
        dataTable2.setData("activity 1","activity Description","History");
        dataTable3.setData("activity Name","activity Description","History");
        dataTable4.setData("activity Name","activity Description","History");
        dataTables.add(dataTable1);
        dataTables.add(dataTable2);
        dataTables.add(dataTable3);
        dataTables.add(dataTable4);
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(dataTables));
        return  view;
        //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
    }//dd
}