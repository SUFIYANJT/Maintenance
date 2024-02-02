package com.example.project;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.project.HelperClass.DataHolder.MyModel;
import com.example.project.HelperClass.Downloads.ChangeSeeker;
import com.example.project.HelperClass.Downloads.ResponseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingActivity extends Fragment {


    CustomRecyclerViewAdapter customRecyclerViewAdapter;
    EditText search;
    int NUMBER_OF_ITEM=0;
    public List<DataTable> dataTables;
    public List<DataTable> OriginaldataTables;
    RecyclerView recyclerView;
    Timer timer;
    boolean flag=false;
    MyModel viewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_existing_activity, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(MyModel.class);
        dataTables=viewModel.getDataTables();
        OriginaldataTables=viewModel.getOriginaldataTables();
        recyclerView = view.findViewById(R.id.recyclerView);
        if(dataTables==null)
            dataTables = new ArrayList<>();
        if(OriginaldataTables==null){
            OriginaldataTables=new ArrayList<>();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(dataTables,view.getContext());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        timer = new Timer();
        NUMBER_OF_ITEM=0;
        search=view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i=0;
                customRecyclerViewAdapter.notifyItemRangeRemoved(0,dataTables.size());
                dataTables.clear();
                OriginaldataTables.forEach(dataTable -> {
                    if(dataTable.getName().contains(s)){
                        dataTables.add(dataTable);
                    }
                });
                customRecyclerViewAdapter.notifyItemRangeInserted(0,dataTables.size());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customRecyclerViewAdapter.notifyItemRangeRemoved(0,dataTables.size());
                dataTables.clear();
                InitCall();
            }
        });
       // dataTables.clear();
        if(dataTables.size()==0) {
            Log.d(TAG, "onCreateView: looks like datatables value is clearing on reach scroll");
            InitCall();
        }else {

            Log.d(TAG, "onCreateView: looks like saving list is working "+dataTables.size()+" testing "+dataTables.get(1).name+" "+customRecyclerViewAdapter.getItemCount());

        }
        //startChecking(timer);
        return  view;
        //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
    }
    public void updateUI(ArrayList<ResponseActivity.ActivityData> demoAndroidData){
        dataTables.clear();
        OriginaldataTables.clear();
        customRecyclerViewAdapter.notifyItemRangeRemoved(0,dataTables.size()-1);
        int i=0;
        Log.d(TAG, "onCreateView: "+NUMBER_OF_ITEM+" "+dataTables.size());
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
            dataTables.add(dataTable1);
            OriginaldataTables.add(dataTable1);
            customRecyclerViewAdapter.notifyItemInserted(i);
            i++;
        }
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    public void InitCall(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseActivity> responseActivityCall=apiService.getActivityData(0);

        responseActivityCall.enqueue(new Callback<ResponseActivity>() {
            @Override
            public void onResponse(Call<ResponseActivity> call, Response<ResponseActivity> response) {
                Log.d(TAG, "onResponse: init is called");
                if(response.isSuccessful()){
                    ResponseActivity responseActivity= response.body();
                    if(responseActivity.getResponse()!=null) {
                        Log.d(TAG, "onResponse: NUMBER_OF_ITEM AND response size"+NUMBER_OF_ITEM+" "+responseActivity.getResponse().size());
                        if (NUMBER_OF_ITEM < responseActivity.getResponse().size()) {
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
                            int i=viewModel.updateText(dataTables);
                            Log.d(TAG, "onResponse: calling recycler render "+dataTables.size()+" "+i);
                        }else{
                            Log.d(TAG, "onResponse: problem with count "+NUMBER_OF_ITEM+" "+responseActivity.getResponse().size());
                        }
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dataTables != null) {
            ArrayList<Parcelable> parcelableArrayList = new ArrayList<>();
            for (DataTable dataTable : dataTables) {
                parcelableArrayList.add((Parcelable) dataTable);
            }
            outState.putParcelableArrayList("datatables", parcelableArrayList);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.setData(dataTables);
        viewModel.setDataOriginal(OriginaldataTables);
        Log.d(TAG, "onResponse: change timer paused ");
        flag=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!flag){
            flag=true;
            //startChecking(timer);
        }
        Log.d(TAG, "onResponse: change timer started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

