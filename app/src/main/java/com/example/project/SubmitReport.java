package com.example.project;

import static com.example.project.Loginpage.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.HelperClass.Downloads.LoginRes;
import com.example.project.HelperClass.Uploads.SubmitData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitReport extends AppCompatActivity {

    EditText editText;
    int userid;
    int activityid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        editText=findViewById(R.id.editTextsumbit);
        Button button=findViewById(R.id.submitReport);
        Intent intent=getIntent();
        if(intent!=null){
            userid=getIntent().getIntExtra("userid",0);
            activityid=getIntent().getIntExtra("activityid",0);
            Log.d(TAG, "onCreate: "+userid);
        }
        button.setOnClickListener(v -> {
            ApiService apiService=RetrofitClient.getApiService();
            SubmitData submitData=new SubmitData();
            submitData.setSubmitText(editText.getText().toString());
            submitData.setActivity_id(activityid);
            submitData.setUser_id(userid);
            Log.d(TAG, "onCreate: "+submitData.getUser_id());
            Call<Integer> loginDataCall=apiService.submit(submitData);
            loginDataCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){
                        int i=response.body();
                        if(i==100){
                            editText.setVisibility(View.INVISIBLE);
                            Toast.makeText(SubmitReport.this, "REPOSRT SUMBITTED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e(TAG, "onFailure: ",t );
                }
            });
        });
    }
}