package com.example.project;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    int NUMBER_OF_ITEM=0;
    int POSITION=0;
    public List<DataTable> dataTables;
    ArrayList<Integer> verifierList;
    RecyclerView recyclerView;
    List<DataTable> copyList;
    Timer timer;
    boolean timerFlag=true;
    boolean flag=false;
    private int NUMBER_OF_ITEM_IN_CHECK;
    MyModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_existing_activity, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(MyModel.class);
        dataTables=viewModel.getDataTables();
        recyclerView = view.findViewById(R.id.recyclerView);
        if(dataTables==null)
            dataTables = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(dataTables,view.getContext());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        timer = new Timer();
        NUMBER_OF_ITEM=0;
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
            customRecyclerViewAdapter.notifyItemInserted(i);
            i++;
        }
    }
    public void InitCall(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseActivity> responseActivityCall=apiService.getActivityData();

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
                            if(!flag) {
                                flag=true;
                                startChecking(timer);
                            }
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
    public void startChecking(Timer timer){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ApiService apiService = RetrofitClient.getApiService();
                Call<Integer> check=apiService.check("");
                check.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()){
                            Integer i=response.body();
                            Log.d(TAG, "onResponse: change count "+i);
                            if(i!=null&&NUMBER_OF_ITEM==0){
                                NUMBER_OF_ITEM=dataTables.size();
                                NUMBER_OF_ITEM_IN_CHECK=i;
                            }
                            if(i!=null&&NUMBER_OF_ITEM_IN_CHECK<i){
                                //insertion
                                Log.d(TAG, "onResponse: change occurred in activity");
                                doubleCheck();
                                NUMBER_OF_ITEM_IN_CHECK=i;
                            }
                            if(flag)
                                startChecking(timer);
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
                
            }
        },1000);
    }
    public void doubleCheck(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<ChangeSeeker> check=apiService.getChangeSeeker();
        Log.d(TAG, "doubleCheck: called");
        check.enqueue(new Callback<ChangeSeeker>() {
            @Override
            public void onResponse(Call<ChangeSeeker> call, Response<ChangeSeeker> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "doubleChecked: "+response.body());
                    ChangeSeeker changeSeeker=response.body();
                    if(changeSeeker.getResponseData()!=null){
                        ChangeSeeker.ResponseData responseData=changeSeeker.getResponseData();
                        if(responseData!=null){
                            Log.d(TAG, "doubleCheck: "+responseData.getChange_activity_id()+" "+responseData.getChange_activity_type_id()+" "+responseData.getChange_seeker_id()+" "+responseData.getPosition());
                            Log.d(TAG, "doubleCheck NUMBER OF ITEM"+NUMBER_OF_ITEM);
                            POSITION= responseData.getPosition();
                            switch (responseData.getChange_activity_type_id()) {
                                case "delete":
                                    int pos = POSITION - 1;
                                    Log.d(TAG, "doubleCheck: POSITION"+pos);
                                    dataTables.remove(pos);
                                    customRecyclerViewAdapter.notifyItemRemoved(pos);
                                    viewModel.updateText(dataTables);
                                    Log.d(TAG, "doubleCheck: removed should work");
                                    break;
                                case "insert":
                                    InsertCall(POSITION,responseData.getChange_activity_id());
                                    viewModel.updateText(dataTables);
                                    break;
                                case "update":
                                    updateCall(POSITION);
                                    break;
                            }
                        }else{
                            Log.d(TAG, "doubleCheck: parsing error");
                        }

                    }else{
                        Log.d(TAG, "doubleCheck: null response");
                    }
                }else{
                    Log.d(TAG, "doubleChecked: error");
                    flag=true;
                }
            }
            @Override
            public void onFailure(Call<ChangeSeeker> call, Throwable t) {
                Log.e(TAG, "doubleChecked  : ",t );
            }
        });

    }

    private void updateCall(int POSITION) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseActivity.ActivityData.Fields> check=apiService.checkUpdate(POSITION);
        check.enqueue(new Callback<ResponseActivity.ActivityData.Fields>() {
            @Override
            public void onResponse(Call<ResponseActivity.ActivityData.Fields> call, Response<ResponseActivity.ActivityData.Fields> response) {
                if(response.isSuccessful()){
                    ResponseActivity.ActivityData.Fields fields=response.body();
                    if(fields!=null){
                        DataTable dataTable=new DataTable(fields.getActivity_id(),fields.getActivity_name(),fields.getActivity_description(),"history",fields.getActivity_machine_id(),fields.getActivity_component_id(),fields.getActivity_schedule_id(), fields.getActivity_status_id(),fields.getActivity_issued_date());
                        dataTables.add(POSITION,dataTable);
                        customRecyclerViewAdapter.notifyItemChanged(POSITION);
                        viewModel.setDataTable(dataTable);
                    }else{
                        Log.d(TAG, "fields is null: ");
                    }
                }else{
                    Log.d(TAG, "insertCall failed");
                }
            }
            @Override
            public void onFailure(Call<ResponseActivity.ActivityData.Fields> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void InsertCall(int POSITION, int changeActivityId) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseActivity.ActivityData.Fields> check=apiService.checkInsert(changeActivityId);
        check.enqueue(new Callback<ResponseActivity.ActivityData.Fields>() {
            @Override
            public void onResponse(Call<ResponseActivity.ActivityData.Fields> call, Response<ResponseActivity.ActivityData.Fields> response) {
                if(response.isSuccessful()){
                    ResponseActivity.ActivityData.Fields fields=response.body();
                    Log.d(TAG, "doubleCheck: trying to insert into the list"+POSITION+" "+dataTables.size());
                    if(fields!=null){
                        DataTable dataTable=new DataTable(fields.getActivity_id(),fields.getActivity_name(),fields.getActivity_description(),"history",fields.getActivity_machine_id(),fields.getActivity_component_id(),fields.getActivity_schedule_id(), fields.getActivity_status_id(),fields.getActivity_issued_date());
                        dataTables.add(POSITION-1,dataTable);
                        customRecyclerViewAdapter.notifyItemInserted(POSITION-1);
                    }else{
                        Log.d(TAG, "fields is null: ");
                    }
                }else{
                    Log.d(TAG, "insertCall failed");
                }
            }
            @Override
            public void onFailure(Call<ResponseActivity.ActivityData.Fields> call, Throwable t) {
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
        Log.d(TAG, "onResponse: change timer paused ");
        flag=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!flag){
            flag=true;
            startChecking(timer);
        }
        Log.d(TAG, "onResponse: change timer started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

