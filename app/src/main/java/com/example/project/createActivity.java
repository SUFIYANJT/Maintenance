package com.example.project;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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

        Communication communication=new Communication(context);
        communication.start();
        return view;

    }
    private class Communication extends Thread{
        Context context;
        public Communication(Context context){
            this.context=context;
        }
        @Override
        public void run(){
            //String url = "http://192.168.1.10:8000/home";
            String url = "https://api.example.com/data";

            // Create a URL object
            URL apiUrl = null;
            try {
                apiUrl = new URL(url);
            } catch (MalformedURLException e) {
                Log.d(TAG, "some exceptiom0");
            }

            // Open a connection to the URL
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) apiUrl.openConnection();
            } catch (IOException e) {
                Log.d(TAG, "some exceptiom1");
            }

            // Set the request method to GET
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
                Log.d(TAG, "some exceptiom2");
            }

            // Get the response code
            int responseCode = 0;
            try {
                responseCode = connection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, String.valueOf(responseCode));
            }
         //   Log.d(TAG, String.valueOf(responseCode));
            if(responseCode>0) {
                Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show();
                // Read the response from the input stream
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    System.out.println("Response Content:\n" + response.toString());
                } catch (IOException e) {
                    Log.d(TAG, "some exceptiommmmm");
                }
            }

            // Close the connection
            connection.disconnect();
        }
    }
}