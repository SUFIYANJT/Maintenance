package com.example.project;

import static com.example.project.createActivity.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.HelperClass.DataHolder.MyModel;

import java.util.ArrayList;
import java.util.List;


public class PendingActivity extends Fragment {
    MyModel viewModel;
    ArrayList<DataTable> dataTables;
    List<PendingData> pendingData=new ArrayList<>();
    CustomRecyclerViewAdapter customRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_activity, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(MyModel.class);
        viewModel.getTextLiveData().observe(getViewLifecycleOwner(), activityDatas -> {
            Log.d(TAG, "onCreateView: Observe model called");
            dataTables=(ArrayList<DataTable>) activityDatas;
            Log.d(TAG, "onCreateView: dataTable size "+dataTables.size());
            //if(pendingData!=null)
                //customRecyclerViewAdapter.notifyItemRangeRemoved(0,pendingData.size());
            int i=0;
            if(pendingData.size()==0&&customRecyclerViewAdapter.getItemCount()==0)
                for(DataTable data:dataTables){
                    if(data.status_id==3) {
                        PendingData pendingData1 = new PendingData();
                        pendingData1.setData(data.getActivity_id(),data.name, data.description, "view", data.machine_id, data.component_id, data.schedule_id, data.status_id, data.getIssued_date());
                        pendingData.add(pendingData1);
                        customRecyclerViewAdapter.notifyItemInserted(i);
                        i++;
                        Log.d(TAG, "onCreateView in Pending Data : " + i + " " + customRecyclerViewAdapter.getItemCount() + " " + data.getTime());
                    }
                }
            else
                Log.d(TAG, "onCreateView: in pending activity "+pendingData.size()+" "+customRecyclerViewAdapter.getItemCount());
        });
        Log.d(TAG, "onCreateView: started pendingFragment");
        //viewModel.updateText(new ArrayList<>());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(pendingData,2,view.getContext());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        return view;
    }
}