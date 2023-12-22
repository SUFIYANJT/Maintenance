package com.example.project;

import static com.example.project.createActivity.TAG;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.HelperClass.DataHolder.MyModel;

import java.util.ArrayList;
import java.util.List;
public class InspectorReview extends Fragment {
    MyModel viewModel;
    List<DataTable> dataTables;
    CustomRecyclerViewAdapter customRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inspector_review, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView_inspector);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<PendingData> pendingData=new ArrayList<>();
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(pendingData,3,view.getContext());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        viewModel=new ViewModelProvider(requireActivity()).get(MyModel.class);
        viewModel.getTextLiveData().observe(getViewLifecycleOwner(), activityDatas -> {
            Log.d(TAG, "onCreateView: Observe model called in Inspector Review");
            dataTables=(ArrayList<DataTable>) activityDatas;
            Log.d(TAG, "onCreateView: dataTable size "+dataTables.size());
            //if(pendingData!=null)
            //customRecyclerViewAdapter.notifyItemRangeRemoved(0,pendingData.size());
            int i=0;
            if(pendingData.size()==0&&customRecyclerViewAdapter.getItemCount()==0)
                for(DataTable data:dataTables){
                    if(data.status_id==4) {
                        PendingData pendingData1 = new PendingData();
                        pendingData1.setData(data.getActivity_id(),data.name, data.description, "view", data.machine_id, data.component_id, data.schedule_id, data.status_id, data.getIssued_date());
                        pendingData.add(pendingData1);
                        customRecyclerViewAdapter.notifyItemInserted(i);
                        i++;
                        Log.d(TAG, "onCreateView in Pending Data in Inspector Review: " + i + " " + customRecyclerViewAdapter.getItemCount() + " " + data.getTime());
                    }
                }
            else
                Log.d(TAG, "onCreateView: in pending activity in Inspector Review"+pendingData.size()+" "+customRecyclerViewAdapter.getItemCount()+" "+pendingData.get(0).status_id);
            customRecyclerViewAdapter.notifyItemRangeInserted(0,pendingData.size());

        });
        return view;
    }
}