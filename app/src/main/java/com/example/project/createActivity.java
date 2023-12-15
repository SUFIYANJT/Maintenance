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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class createActivity extends Fragment {
    //some changes
    public static String TAG="ContentView";
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_activity, container, false);
        EditText activityName = view.findViewById(R.id.editText);
        EditText description = view.findViewById(R.id.editText3);
        EditText name = view.findViewById(R.id.editText4);
        Context context=view.getContext();
        //some
        Spinner selectMachine = view.findViewById(R.id.comboBox);
        Spinner selectComponent = view.findViewById(R.id.comboBox1);
        Spinner selectSchedule = view.findViewById(R.id.comboBox2);
        Button submit = view.findViewById(R.id.submit);
        ArrayList<String> arrayList=new ArrayList<>();
        ArrayList<String> arrayList1=new ArrayList<>();
        ArrayList<String> arrayList2=new ArrayList<>();
        arrayList.add("Machine1");
        arrayList.add("Machine2");
        arrayList1.add("component1");
        arrayList1.add("component2");
        arrayList2.add("weekly");
        arrayList2.add("monthly");
        ApiService apiService = RetrofitClient.getApiService();

        Call<User> call = apiService.getUser("");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Toast.makeText(view.getContext(), user.name, Toast.LENGTH_SHORT).show();
                    // Handle the user data here
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure: network errors, parsing errors, etc.
                Toast.makeText(view.getContext(), "failed to connect", Toast.LENGTH_SHORT).show();
            }
        });

        selectMachine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectComponent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,arrayList);
        ArrayAdapter<String> stringArrayAdapter1=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,arrayList1);
        ArrayAdapter<String> stringArrayAdapter2=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,arrayList2);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        stringArrayAdapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        selectMachine.setAdapter(stringArrayAdapter);
        selectComponent.setAdapter(stringArrayAdapter1);
        selectSchedule.setAdapter(stringArrayAdapter2);
        return view;

    }

}