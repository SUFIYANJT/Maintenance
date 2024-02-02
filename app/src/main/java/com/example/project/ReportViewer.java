package com.example.project;

import static com.example.project.Loginpage.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project.HelperClass.Uploads.NewActivityInstance;
import com.example.project.HelperClass.Uploads.SubmitData;

import java.util.HashMap;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportViewer extends AppCompatActivity {

    SubmitData submitData;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_viewer);
        TextView textView=findViewById(R.id.textView_report);
        ApiService apiService=RetrofitClient.getApiService();
        imageView=findViewById(R.id.reportedImage);
        int activity_id=getIntent().getIntExtra("activity_id",0);
        Call<SubmitData> report=apiService.getReport(activity_id);
        report.enqueue(new Callback<SubmitData>() {
            @Override
            public void onResponse(Call<SubmitData> call, Response<SubmitData> response) {
                Log.d(TAG, "onResponse: report");
                if(response.isSuccessful()){
                    Toast.makeText(ReportViewer.this, "response", Toast.LENGTH_SHORT).show();
                    submitData=response.body();

                    assert submitData != null;
                    if(submitData.getBase64()!=null){
                        Log.d(TAG, "onResponse: submitted"+submitData.getSubmitText());
                        byte[] decodedBytes = Base64.decode(submitData.getBase64(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        Glide.with(ReportViewer.this).load(bitmap).into(imageView);
                    }else{
                        Log.d(TAG, "onResponse: base64 is empty");
                    }
                    if(submitData.getSubmitText()!=null){
                        textView.setText(submitData.getSubmitText());
                    }
                }else{
                    Log.d(TAG, "onResponse: response is not successful");
                }
            }

            @Override
            public void onFailure(Call<SubmitData> call, Throwable t) {
                Log.e(TAG, "onFailure: failed report ",t );
            }
        });
        Button approve=findViewById(R.id.buttonReport);
        approve.setOnClickListener(v -> {
            Call<Integer> updateActivity=apiService.updateActivity(activity_id);
            updateActivity.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(ReportViewer.this, "report approved", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        });
    }
}