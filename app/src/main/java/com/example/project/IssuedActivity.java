package com.example.project;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.HelperClass.DataHolder.MyModel;
import com.example.project.HelperClass.Downloads.ResponseActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    IssuedActivity extends Fragment {
    ArrayList<DataTable> dataTableArrayList;
    CustomCycler customCycler;
    SwipeRefreshLayout swipeRefreshLayout;
    MyModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_issued_activity, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        viewModel=new ViewModelProvider(requireActivity()).get(MyModel.class);
        dataTableArrayList=viewModel.getDataTablesIssued();
        if(dataTableArrayList==null)
            dataTableArrayList = new ArrayList<>();
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        customCycler = new CustomCycler(dataTableArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customCycler);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customCycler.notifyItemRangeRemoved(0,dataTableArrayList.size());
                dataTableArrayList.clear();
                InitCall();
            }
        });
        if(dataTableArrayList.size()==0)
            InitCall();
        return view;
    }
    public void InitCall(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseActivity> responseActivityCall=apiService.getActivityData(1);

        responseActivityCall.enqueue(new Callback<ResponseActivity>() {
            @Override
            public void onResponse(Call<ResponseActivity> call, Response<ResponseActivity> response) {
                Log.d(TAG, "onResponse: init is called");
                if(response.isSuccessful()){
                    ResponseActivity responseActivity= response.body();
                    if(responseActivity.getResponse()!=null) {
                        Log.d(TAG, "onResponse: NUMBER_OF_ITEM AND response size"+" "+responseActivity.getResponse().size());
                        ArrayList<ResponseActivity.ActivityData> demoAndroidData=new ArrayList<>();
                        responseActivity.getResponse().forEach((key, value) -> {
                            ResponseActivity.ActivityData activityData = responseActivity.getResponse().get(key);
                            if (activityData != null) {
                                demoAndroidData.add(activityData);
                                Log.d(TAG, "onResponse: activities "+key);
                            }
                        });
                        Collections.sort(demoAndroidData, Comparator.comparingInt(ResponseActivity.ActivityData::getPk));
                        updateUI(demoAndroidData);

                        Log.d(TAG, "onResponse: calling recycler render "+dataTableArrayList.size());
                        viewModel.setDataIssued(dataTableArrayList);
                    }else{
                        Log.d(TAG, "onResponse: response is null");
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseActivity> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
    public void updateUI(ArrayList<ResponseActivity.ActivityData> demoAndroidData){
        dataTableArrayList.clear();
        customCycler.notifyItemRangeRemoved(0,dataTableArrayList.size()-1);
        int i=0;
        Log.d(TAG, "onCreateView: "+" "+dataTableArrayList.size());
        for (ResponseActivity.ActivityData values: demoAndroidData) {
            int pk=values.getPk();
            String model = values.getModel();
            ResponseActivity.ActivityData.Fields fields = values.getFields();
            String activity_name = fields.getActivity_name();
            String activity_description = fields.getActivity_description();
            int machine_id=fields.getActivity_machine_id();
            int component_id=fields.getActivity_component_id();
            int schedule_id=fields.getActivity_schedule_id();
            int status_id=fields.getActivity_status_id();
            Log.d(TAG, "onResponse1: " + pk + " " + model + " " + activity_name + " " + activity_description);
            DataTable dataTable1 = new DataTable(pk,activity_name, activity_description, "time",machine_id,component_id,schedule_id,status_id,fields.getActivity_issued_date());
            dataTableArrayList.add(dataTable1);
            customCycler.notifyItemInserted(i);
            i++;
        }
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.setDataIssued(dataTableArrayList);
    }
}
class CustomCycler extends RecyclerView.Adapter<CustomCycler.MyHolder>{
    ArrayList<DataTable> dataTableArrayList;
    CustomCycler(ArrayList<DataTable> dataTableArrayList){
        this.dataTableArrayList=dataTableArrayList;
    }

    @NonNull
    @Override
    public CustomCycler.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.existing_activity_item, parent, false);
        MyHolder myViewHolder= new CustomCycler.MyHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCycler.MyHolder holder, int position) {
        holder.name.setText(dataTableArrayList.get(position).name);
        holder.description.setText(dataTableArrayList.get(position).getDescription());
        holder.history.setText(dataTableArrayList.get(position).getHistory());

    }

    @Override
    public int getItemCount() {
        if(dataTableArrayList!=null)
            return dataTableArrayList.size();
        else
            return 0;
    }
    class MyHolder extends RecyclerView.ViewHolder{

        TextView name,description,history;
        MaterialCardView constraintLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.activity_name);
            description = itemView.findViewById(R.id.activity_description);
            history = itemView.findViewById(R.id.activity_history);
            constraintLayout=itemView.findViewById(R.id.existItem);
        }
    }
}