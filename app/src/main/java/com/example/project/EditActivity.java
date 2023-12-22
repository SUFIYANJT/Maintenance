package com.example.project;

import static com.example.project.createActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.HelperClass.DataHolder.MyModel;
import com.example.project.HelperClass.Downloads.ResponseComponent;
import com.example.project.HelperClass.Downloads.ResponseDjango;
import com.example.project.HelperClass.Downloads.ResponseSchedule;
import com.example.project.HelperClass.Downloads.UserDatas;
import com.example.project.HelperClass.Uploads.NewActivityInstance;
import com.example.project.HelperClass.Uploads.TaskData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    ArrayAdapter<String> stringArrayAdapter;
    ArrayAdapter<String> stringArrayAdapter1;
    ArrayAdapter<String> stringArrayAdapter2;
    NewActivityInstance newActivityInstance=new NewActivityInstance();
    int MachinePosition,ComponentPosition,SchedulePosition;
    ResponseSchedule responseSchedule;
    ResponseDjango responseDjango1;
    ResponseComponent responseComponent;
    Spinner selectMachine;
    MyModel viewModel;
    Spinner selectComponent;
    Spinner selectSchedule;
    ArrayList<String> arrayList,arrayList1,arrayList2;
    ArrayList<TaskData> taskData;
    RecyclerView recyclerView;
    DataTable dataTable;
    ArrayList<UserDatas.Users.Fields> fields;
    int activity_id;
    String activity_name,activity_description;
    int component_id,machine_id,schedule_id;
    EditText search;
    CustomRecycler customRecycler;
    ArrayList<Integer> userids;
    int userposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText activityName = findViewById(R.id.editText);
        EditText description = findViewById(R.id.editText3);
        search = findViewById(R.id.customInput3);
        Intent intent=getIntent();
        if(intent!=null){
            activity_id=intent.getIntExtra("activity_id",0);
            activity_name=intent.getStringExtra("activity_name");
            activity_description=intent.getStringExtra("description");
            machine_id=intent.getIntExtra("machine_id",0);
            component_id=intent.getIntExtra("component_id",0);
            schedule_id=intent.getIntExtra("schedule_id",0);
        }
        userids=new ArrayList<>();
        activityName.setText(activity_name);
        description.setText(activity_description);
        Log.d(TAG, "onCreate: datas acquired from previous activity page "+activity_id+" "+activity_name+" "+activity_description+" "+machine_id);
        arrayList=new ArrayList<>();
        arrayList1=new ArrayList<>();
        arrayList2=new ArrayList<>();
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseDjango> call = apiService.getUser("");
        arrayList.add("not item found");
        stringArrayAdapter = new ArrayAdapter<>(EditActivity.this, android.R.layout.simple_expandable_list_item_1,arrayList);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        MachineCall(call);
        Call<ResponseComponent> responseDjangoCall= apiService.getComponent("");
        arrayList1.add("no item found");
        stringArrayAdapter1 = new ArrayAdapter<>(EditActivity.this, android.R.layout.simple_expandable_list_item_1,arrayList1);
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        ComponentCall(responseDjangoCall);
        Call<ResponseSchedule> responseScheduleCall = apiService.getSchedule("");
        arrayList2.add("no item found");
        stringArrayAdapter2 = new ArrayAdapter<>(EditActivity.this, android.R.layout.simple_expandable_list_item_1,arrayList2);
        stringArrayAdapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        ScheduleCall(responseScheduleCall);
        selectMachine = findViewById(R.id.comboBox);
        selectComponent = findViewById(R.id.comboBox1);
        selectSchedule = findViewById(R.id.comboBox2);
        selectMachine.setAdapter(stringArrayAdapter);
        selectComponent.setAdapter(stringArrayAdapter1);
        selectSchedule.setAdapter(stringArrayAdapter2);
        recyclerView= findViewById(R.id.comboBox3);
        fields=new ArrayList<>();

        customRecycler = new CustomRecycler(fields);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customRecycler);
        dropDown();
        stringArrayAdapter.notifyDataSetChanged();
        stringArrayAdapter1.notifyDataSetChanged();
        stringArrayAdapter2.notifyDataSetChanged();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Call<UserDatas> userDatasCall= apiService.getUsers(s.toString());
                userDatasCall.enqueue(new Callback<UserDatas>() {
                    @Override
                    public void onResponse(Call<UserDatas> call, Response<UserDatas> response) {

                        if(response.isSuccessful()){
                            UserDatas userDatas=response.body();
                            if(userDatas!=null){
                                userDatas.getResponse().forEach((key,value)->{
                                    fields.add(userDatas.getResponse().get(key).getFields());
                                    userids.add(Integer.getInteger(key));
                                });
                            }

                            customRecycler.notifyDataSetChanged();
                        }else{
                            Log.d(TAG, "onResponse: response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDatas> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button assign=findViewById(R.id.submit);
        assign.setOnClickListener(v -> {
            TaskData taskData1=new TaskData();
            taskData1.setTask_activity_id(activity_id);
            taskData1.setActivity_name(activityName.getText().toString());
            taskData1.setDescription(description.getText().toString());
            taskData1.setMachine_id(MachinePosition);
            taskData1.setComponent_id(ComponentPosition);
            taskData1.setSchedule_id(SchedulePosition);
            Log.d(TAG, "onCreate: userid position "+userids.size());
            taskData1.setTask_assigned_to(fields.get(userposition).getUser_name());
            Call<Integer> taskDataCall=apiService.uploadAssignment(taskData1);
            taskDataCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()&&response.body()==100){
                        Toast.makeText(EditActivity.this,"Task assigned successfully",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        });
    }
    public void dropDown(){

        selectMachine.setSelection(machine_id-1);
        selectComponent.setSelection(component_id-1);
        selectSchedule.setSelection(schedule_id-1);
        selectMachine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                MachinePosition=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        selectComponent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ComponentPosition=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        selectSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SchedulePosition=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void MachineCall(Call<ResponseDjango> call){

        call.enqueue(new Callback<ResponseDjango>() {
            @Override
            public void onResponse(Call<ResponseDjango> call, Response<ResponseDjango> response) {
                if(response.isSuccessful()){
                    responseDjango1 = response.body();
                    // Logging the entire response body as a string
                    if (responseDjango1 != null) {
                        String responseBodyString = new Gson().toJson(responseDjango1); // Convert to JSON string
                        Log.d(TAG, "Response Body: " + responseBodyString);
                        if(responseDjango1.getResponse()!=null){
                            arrayList.clear();
                            //userids.clear();
                            responseDjango1.getResponse().forEach((key,value)->{
                                if(responseDjango1.getResponse().get(key).getFields()!=null&&key!=null) {
                                    Log.d(TAG, "onResponse: "+responseDjango1.getResponse().get(key).getFields().getMachine_name());
                                    arrayList.add(responseDjango1.getResponse().get(key).getFields().getMachine_name().toString());

                                    Log.d(TAG, "onResponse: arraylist and userid "+Integer.getInteger(key));
                                }
                            });
                            stringArrayAdapter.notifyDataSetChanged();
                            Log.d(TAG, "onResponse: arraylist"+arrayList.size()+" "+userids.size());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseDjango> call, Throwable t) {
                Log.d(TAG, "onFailure: failed Component");
                Log.d(TAG, "Failed: " + t.getMessage(), t);
                // arrayList.add("no item found");
                stringArrayAdapter.notifyDataSetChanged();
            }

        });
    }
    public void ComponentCall(Call<ResponseComponent> responseDjangoCall){
        responseDjangoCall.enqueue(new Callback<ResponseComponent>() {
            @Override
            public void onResponse(Call<ResponseComponent> call, Response<ResponseComponent> response) {

                responseComponent = response.body();

                if(response.isSuccessful()){
                    arrayList1.clear();
                    if (responseComponent != null) {
                        String responseBodyString = new Gson().toJson(responseComponent); // Convert to JSON string
                        Log.d(TAG, "Response Body: " + responseBodyString);
                        if(responseComponent.getResponse()!=null){
                            arrayList1.clear();
                            responseComponent.getResponse().forEach((key,value)->{
                                if(responseComponent.getResponse().get(key).getFields()!=null) {
                                    Log.d(TAG, "onResponse: "+responseComponent.getResponse().get(key).getFields().getComponent_name());
                                    arrayList1.add(responseComponent.getResponse().get(key).getFields().getComponent_name().toString());
                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseComponent> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

                stringArrayAdapter1.notifyDataSetChanged();
            }
        });
    }
    public void ScheduleCall(Call<ResponseSchedule> responseScheduleCall){
        responseScheduleCall.enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                arrayList2.clear();
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response.body());

                    responseSchedule = response.body();
                    if(responseSchedule.getResponse()!=null){
                        responseSchedule.getResponse().forEach((key,value)->{
                            arrayList2.add(value.getFields().getSchedule_type());
                        });
                        stringArrayAdapter2.notifyDataSetChanged();
                    }
                }else{
                    Log.d(TAG, "onResponse: response is not successfull");
                }
            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
    class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.CustomHolder>
    {
        ArrayList<UserDatas.Users.Fields> fields;

        CustomRecycler(ArrayList<UserDatas.Users.Fields> arrayList){
            this.fields=arrayList;
        }

        @NonNull
        @Override
        public CustomRecycler.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_activity_item, parent, false);
            return new CustomHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomRecycler.CustomHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.textView.setText(fields.get(position).getUser_name());
            holder.itemView.setOnClickListener(v -> {
                EditActivity.this.runOnUiThread(() -> {
                    search.setText(fields.get(position).getUser_name());
                    userposition=position;
                    EditActivity.this.customRecycler.notifyDataSetChanged();
                });
            });
        }
        @Override
        public int getItemCount() {
            return fields.size();
        }
        class  CustomHolder extends  RecyclerView.ViewHolder{
            TextView textView;
            public CustomHolder(@NonNull View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.text_edit_activity);
            }
        }
    }
}
