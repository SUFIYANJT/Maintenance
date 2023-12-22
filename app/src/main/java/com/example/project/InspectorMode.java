package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.project.HelperClass.Downloads.ResponseActivity;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspectorMode extends AppCompatActivity {
    int user_id;
    public static String TAG="ContentValues";
    ArrayList<DataTable> dataTables;
    String username;
    int userID;
    RecyclerInspecterMode recyclerInspecterMode;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_mode);
        Toolbar toolbar=findViewById(R.id.toolbarInspector);
        Intent intent = getIntent();

        if(intent!=null){
            username=intent.getStringExtra("username");
            user_id=intent.getIntExtra("userid",0);
            toolbar.setTitle(username);
        }

        recyclerView = findViewById(R.id.recyclerViewInspectorMode);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataTables = new ArrayList<>();
        getData();

        recyclerInspecterMode = new RecyclerInspecterMode(dataTables,this,user_id);
        recyclerView.setAdapter(recyclerInspecterMode);
    }
    public void getData(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseActivity> check=apiService.getTask(user_id);
        check.enqueue(new Callback<ResponseActivity>() {
            @Override
            public void onResponse(Call<ResponseActivity> call, Response<ResponseActivity> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: response was successful");
                    ResponseActivity responseActivity=response.body();
                    if(responseActivity!=null){
                        responseActivity.getResponse().forEach((key,value)->{
                            Log.d(TAG, "onResponse: "+responseActivity.getResponse().get(key).getFields().getActivity_id());
                            ResponseActivity.ActivityData.Fields fields=responseActivity.getResponse().get(key).getFields();
                            DataTable dataTable=new DataTable(
                                    responseActivity.getResponse().get(key).getPk(),
                                    fields.getActivity_name(),
                                    fields.getActivity_description(),
                                    "history",
                                    fields.getActivity_machine_id(),
                                    fields.getActivity_component_id(),
                                    fields.getActivity_schedule_id(),
                                    fields.getActivity_status_id(),
                                    fields.getActivity_issued_date()
                            );
                            dataTables.add(dataTable);
                        });
                        if(responseActivity.getResponse().size()==0){
                            DataTable dataTable=new DataTable(0,"No Task assigned ","",null,0,0,0,0,new Date());
                            dataTables.add(dataTable);
                        }
                        recyclerInspecterMode.notifyItemRangeChanged(0,dataTables.size());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseActivity> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }
}
