package com.example.project;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.HelperClass.DataHolder.MyModel;
import com.example.project.HelperClass.Downloads.ResponseComponent;
import com.example.project.HelperClass.Downloads.ResponseDjango;
import com.example.project.HelperClass.Downloads.ResponseSchedule;
import com.example.project.HelperClass.Uploads.NewActivityInstance;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class createActivity extends Fragment {
    //some changes
    public static String TAG="ContentView";
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> arrayList1=new ArrayList<>();
    ArrayList<String> arrayList2=new ArrayList<>();
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
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_activity, container, false);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        EditText activityName = view.findViewById(R.id.editText);
        EditText description = view.findViewById(R.id.editText3);
        EditText name = view.findViewById(R.id.editText4);
        Context context=view.getContext();
        viewModel = new ViewModelProvider(requireActivity()).get(MyModel.class);
        selectMachine = view.findViewById(R.id.comboBox);
        selectComponent = view.findViewById(R.id.comboBox1);
        selectSchedule = view.findViewById(R.id.comboBox2);
        dropDown();
        Button submit = view.findViewById(R.id.submit);
        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseDjango> call = apiService.getUser("");
        arrayList= viewModel.getResponseDjangoMutableLiveData().getValue();
     //   Log.d(TAG, "onCreateView: "+arrayList.size());
        if(arrayList==null) {
            Log.d(TAG, "onCreateView: value is null calling Machine call");
            arrayList=new ArrayList<>();
            arrayList.add("not item found");
            stringArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,arrayList);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            MachineCall(call);
        }else
            Log.d(TAG, "onCreateView: value is not null");
        arrayList1=viewModel.getResponseComponentMutableLiveData().getValue();
        Call<ResponseComponent> responseDjangoCall= apiService.getComponent("");
        if(arrayList1==null){
            arrayList1=new ArrayList<>();
            arrayList1.add("no item found");
            stringArrayAdapter1 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,arrayList1);
            stringArrayAdapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            ComponentCall(responseDjangoCall);
        }
        arrayList2=viewModel.getResponseScheduleMutableLiveData().getValue();
        Call<ResponseSchedule> responseScheduleCall = apiService.getSchedule("");
        if(arrayList2==null) {
            arrayList2=new ArrayList<>();
            arrayList2.add("no item found");
            stringArrayAdapter2 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,arrayList2);
            stringArrayAdapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            ScheduleCall(responseScheduleCall);
        }

     //   dropDown();
        submit=view.findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            String name_activity = activityName.getText().toString();
            String activity_description=description.getText().toString();
            String some_name=name.getText().toString();
            newActivityInstance.setActivity_name(name_activity);
            newActivityInstance.setActivity_description(activity_description);
            newActivityInstance.setMachineId(responseDjango1.getResponse().get(String.valueOf(MachinePosition)).getPk());
            newActivityInstance.setComponentId(responseComponent.getResponse().get(String.valueOf(ComponentPosition)).getPk());
            newActivityInstance.setScheduleId(responseSchedule.getResponse().get(String.valueOf(SchedulePosition)).getPk());
            newActivityInstance.setSome_name(some_name);
            Call<Integer> res=apiService.sendActivityInstance(newActivityInstance);
            res.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call1, Response<Integer> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "new Activity created", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call1, Throwable t) {

                }
            });
        });
        selectMachine.setAdapter(stringArrayAdapter);
        selectComponent.setAdapter(stringArrayAdapter1);
        selectSchedule.setAdapter(stringArrayAdapter2);
        stringArrayAdapter.notifyDataSetChanged();
        stringArrayAdapter1.notifyDataSetChanged();
        stringArrayAdapter2.notifyDataSetChanged();
        return view;

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
                            responseDjango1.getResponse().forEach((key,value)->{
                                if(responseDjango1.getResponse().get(key).getFields()!=null) {
                                    Log.d(TAG, "onResponse: "+responseDjango1.getResponse().get(key).getFields().getMachine_name());
                                    arrayList.add(responseDjango1.getResponse().get(key).getFields().getMachine_name().toString());
                                }
                            });
                            stringArrayAdapter.notifyDataSetChanged();
                            viewModel.setResponseDjangoMutableLiveData(arrayList);
                            Log.d(TAG, "onResponse: arraylist"+arrayList.size());
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
                            viewModel.setResponseComponentMutableLiveData(arrayList1);
                            stringArrayAdapter1.notifyDataSetChanged();
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
                        viewModel.setResponseScheduleMutableLiveData(arrayList2);
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
    @Override
    public void onResume() {
        super.onResume();

    }
    public void dropDown(){
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
}