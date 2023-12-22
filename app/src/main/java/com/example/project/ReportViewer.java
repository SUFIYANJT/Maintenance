package com.example.project;

import static com.example.project.Loginpage.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.HelperClass.Uploads.NewActivityInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportViewer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_viewer);
        TextView textView=findViewById(R.id.textView_report);
        ApiService apiService=RetrofitClient.getApiService();
        int activity_id=getIntent().getIntExtra("activity_id",0);
        Call<ReportObj> report=apiService.getReport(activity_id);
        report.enqueue(new Callback<ReportObj>() {
            @Override
            public void onResponse(Call<ReportObj> call, Response<ReportObj> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: is successful "+response.body());
                    ReportObj reportObj= response.body();
                    textView.setText(reportObj.getReport());
                }
            }

            @Override
            public void onFailure(Call<ReportObj> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
        Button approve=findViewById(R.id.buttonReport);
        approve.setOnClickListener(v -> {
            Call<Integer> updateActivity=apiService.updateActivity(activity_id);
            updateActivity.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){

                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        });
    }
}
class ReportObj {
    private String report;

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}