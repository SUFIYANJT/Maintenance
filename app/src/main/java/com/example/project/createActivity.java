package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class createActivity extends Fragment {
    private EditText activityName,description,name;
    private Spinner selectMachine,selectComponent,selectSchedule;
    private Button submit;
    //some change
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activityName=getActivity().findViewById(R.id.nameactivity);
        description=getActivity().findViewById(R.id.description);
        name=getActivity().findViewById(R.id.nameofcreater);
        //some
        selectMachine=getActivity().findViewById(R.id.comboBox);
        selectComponent=getActivity().findViewById(R.id.comboBox1);
        selectSchedule=getActivity().findViewById(R.id.comboBox2);
        submit=getActivity().findViewById(R.id.submit);

        return inflater.inflate(R.layout.fragment_create_activity, container, false);

    }
}