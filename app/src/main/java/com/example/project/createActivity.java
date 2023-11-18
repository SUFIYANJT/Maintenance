package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class createActivity extends Fragment {
    //some changes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_activity, container, false);
        EditText activityName = view.findViewById(R.id.editText);
        EditText description = view.findViewById(R.id.editText3);
        EditText name = view.findViewById(R.id.editText4);
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