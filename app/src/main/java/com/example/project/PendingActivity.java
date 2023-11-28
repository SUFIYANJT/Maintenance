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


public class PendingActivity extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_activity, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.pending_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<PendingData> pendingData=new ArrayList<>();
        PendingData pendingData1=new PendingData();
        pendingData1.setData(10,"description","activity 1");
        pendingData.add(pendingData1);
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(pendingData,2));
        return view;
    }
}